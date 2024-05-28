package com.yurpetr.philkamonitor.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PhilkaConstants {
	public static final String COOKIES_FILE = "assets/philka.cookies";
	public static final String URL_ORIGIN = "https://philka.ru/";
	public static final String URL_FORUM = "https://philka.ru/forum/";
	public static final String URL_TOPIC = "https://philka.ru/forum/topic/";
	public static final String URL_NEW_POSTS = "?view=getnewpost";
	public static final String URL_USER_CP = URL_FORUM + "index.php?app=core&module=usercp";
	public static final String URL_LOGIN = URL_FORUM + "index.php?app=core&module=global&section=login";
	public static final String URL_LOGIN_DO = URL_LOGIN + "&do=process";
	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:126.0) Gecko/20100101 Firefox/126.0";
	public static final String MEMBER_ID = "member_id";

	private static String PROXY_ADDRESS;
	private static int PROXY_PORT;
	private static String PROXY_USERNAME;
	private static String PROXY_PASSWORD;

	private static String IPS_USERNAME;
	private static String IPS_PASSWORD;
	private static int TOPIC_NUMBER;

	public static String getProxyAddress() {
		return PROXY_ADDRESS;
	}

	public static int getProxyPort() {
		return PROXY_PORT;
	}

	public static String getProxyUserName() {
		return PROXY_USERNAME;
	}

	public static String getProxyPassword() {
		return PROXY_PASSWORD;
	}

	public static String getIpsUserName() {
		return IPS_USERNAME;
	}

	public static String getIpsPassword() {
		return IPS_PASSWORD;
	}

	public static int getTopicNumber() {
		return TOPIC_NUMBER;
	}

	@Value("${proxy.address}")
	public void setProxyAddress(String proxyAddress) {
		PROXY_ADDRESS = proxyAddress;
	}

	@Value("${proxy.port}")
	public void setProxyPort(int proxyPort) {
		PROXY_PORT = proxyPort;
	}

	@Value("${proxy.username}")
	public void setProxyUserName(String proxyUserName) {
		PROXY_USERNAME = proxyUserName;
	}

	@Value("${proxy.password}")
	public void setProxyPassword(String proxyPassword) {
		PROXY_PASSWORD = proxyPassword;
	}

	@Value("${forum.philka.username}")
	public void setIpsUserName(String ipsUserName) {
		IPS_USERNAME = ipsUserName;
	}

	@Value("${forum.philka.password}")
	public void setIpsPassword(String ipsPassword) {
		IPS_PASSWORD = ipsPassword;
	}

	@Value("${forum.philka.topic}")
	public void setTopicNumber(int topicNumber) {
		TOPIC_NUMBER = topicNumber;
	}

}