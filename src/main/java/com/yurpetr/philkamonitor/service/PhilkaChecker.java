package com.yurpetr.philkamonitor.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.yurpetr.philkamonitor.utils.FileSaver;
import com.yurpetr.philkamonitor.utils.PhilkaConstants;
import com.yurpetr.philkamonitor.utils.PhilkaCookies;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PhilkaChecker {

	public static int checkPhilkaConnection() {

		Map<String, String> cookies = PhilkaCookies.getCookies();

		Map<String, String> headers = new HashMap<>();
		headers.put("accept-encoding", "gzip, deflate");
		Connection proxyfiedSession = PhilkaCookies.getProxyfiedSession();

		try {
			String url = PhilkaConstants.URL_TOPIC + PhilkaConstants.getTopicNumber() + "/"
					+ PhilkaConstants.URL_NEW_POSTS;
			Response response = proxyfiedSession.newRequest(url).cookies(cookies).method(Connection.Method.GET)
					.userAgent(PhilkaConstants.USER_AGENT).referrer(PhilkaConstants.URL_FORUM).followRedirects(true)
					.referrer(PhilkaConstants.URL_LOGIN).headers(headers).execute();
			Document newPosts = response.parse();
//			FileSaver.saveHtmlToFile(newPosts.html());
			log.info("\n{}", newPosts.head().text());

			Elements postBlocks = newPosts.getElementsByClass("post_block");
			StringBuilder stringBuilder = new StringBuilder();

			for (Element postBlock : postBlocks) {
				log.info("Post ID: {}", postBlock.id());
				stringBuilder.append("Post ID: ");
				stringBuilder.append(postBlock.id());
				stringBuilder.append("\n");
				stringBuilder.append("Sent at: ");
				stringBuilder.append(postBlock.getElementsByClass("published").first().text());
				stringBuilder.append("\n\n");
				for (var element : postBlock.getElementsByClass("prettyprint")) {
					stringBuilder.append(element.wholeText());
					stringBuilder.append("\n\n");
				}
				stringBuilder.append("\n\n\n");
			}
			FileSaver.saveHtmlToFile(stringBuilder.toString(), "keys.txt");

			return response.statusCode();
		} catch (IOException e) {
			log.error(e.getMessage());
		}

		return -1;
	}

}
