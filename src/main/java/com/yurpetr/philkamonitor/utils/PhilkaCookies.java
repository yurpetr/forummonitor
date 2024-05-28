package com.yurpetr.philkamonitor.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PhilkaCookies extends PhilkaConstants {
	private static Connection session;

	private static Map<String, String> headers;
	private static Map<String, String> formData;

	static {
		headers = new HashMap<>();
		headers.put("accept-encoding", "gzip, deflate");
		headers.put("Content-Type", "application/x-www-form-urlencoded");
		headers.put("Origin", URL_ORIGIN);
		headers.put("DNT", "1");
		headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8");
		formData = new HashMap<>();
		formData.put("ips_username", getIpsUserName());
		formData.put("ips_password", getIpsPassword());
		formData.put("rememberMe", "1");
		formData.put("referer", URL_FORUM);
	}

	public static Map<String, String> getCookies() {
		Map<String, String> cookies = loadCookies();
		try {
			if (!cookies.containsKey(MEMBER_ID) || checkExpiredCookies(cookies)) {
				cookies = loginAndGetCookie(cookies);
			}
		} catch (IOException e) {
			log.error("Something with cookies load");
		}
		return cookies;
	}

	private static boolean checkExpiredCookies(Map<String, String> cookies) {
		try {
			Connection connection = getProxyfiedSession().newRequest(URL_USER_CP).userAgent(USER_AGENT).cookies(cookies)
					.headers(headers);
			Response response = connection.execute();
			if (response.statusCode() == HttpURLConnection.HTTP_FORBIDDEN) {
				return true;
			}
		} catch (HttpStatusException e) {
			return true;
		} catch (IOException e) {
			log.error("Something wrong while connecting to server");
		}
		return false;
	}

	public static Connection getProxyfiedSession() {

		if (session == null) {
			session = Jsoup.newSession().proxy(getProxyAddress(), getProxyPort()).auth(auth -> {
				if (auth.isProxy()) {
					return auth.credentials(getProxyUserName(), getProxyPassword());
				}
				return auth.credentials(getIpsUserName(), getIpsPassword());
			});
		}
		return session;
	}

	private static Map<String, String> loginAndGetCookie(Map<String, String> cookies) throws IOException {
		Map<String, String> mapCookies = new HashMap<>(cookies);

		Connection.Response loginForm = getProxyfiedSession().newRequest(URL_LOGIN).userAgent(USER_AGENT)
				.headers(headers).execute();
		mapCookies.putAll(loginForm.cookies());
		storeCookies(mapCookies);

		Document html = loginForm.parse();
		String authToken = html.select("input[name=auth_key]").first().attr("value");
		System.out.println("Found authToken: " + authToken);

		formData.put("auth_key", authToken);

		System.out.println("cookies before login:");
		System.out.println(mapCookies);
		System.out.println(" Logged in cookie present? "
				+ (mapCookies.containsKey(MEMBER_ID) && mapCookies.get(MEMBER_ID) != "0"));

		Connection request = getProxyfiedSession().newRequest(URL_LOGIN_DO).cookies(mapCookies).headers(headers)
				.userAgent(USER_AGENT).data(formData).method(Connection.Method.POST).referrer(URL_FORUM);
		Connection.Response afterLoginPage = request.execute();

		System.out.println("cookies after login:");
		System.out.println(mapCookies);
		System.out.println(" Logged in cookie present? " + mapCookies.containsKey(MEMBER_ID));

		mapCookies.putAll(afterLoginPage.cookies());
		storeCookies(mapCookies);
		return mapCookies;
	}

	private static Map<String, String> loadCookies() {
		Map<Object, Object> propertiesMap = new HashMap<>();
		Map<String, String> cookies = new HashMap<>();
		Properties cookiesProp = new Properties();
		if (new File(COOKIES_FILE).exists()) {
			try (FileInputStream fis = new FileInputStream(COOKIES_FILE)) {
				cookiesProp.load(fis);
				propertiesMap = new HashMap<>(cookiesProp);
				for (Map.Entry<Object, Object> entry : propertiesMap.entrySet()) {
					cookies.put(entry.getKey().toString(), entry.getValue().toString());
				}
			} catch (IOException e) {
				log.error("Error while cookies file loading");
			}
		}
		return cookies;
	}

	private static void storeCookies(Map<String, String> cookies) {
		Properties cookiesProp = new Properties();
		cookiesProp.putAll(cookies);
		try (FileOutputStream fos = new FileOutputStream(COOKIES_FILE)) {
			cookiesProp.store(fos, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
