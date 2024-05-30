package com.yurpetr.philkamonitor.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.jsoup.Connection;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yurpetr.philkamonitor.model.Post;
import com.yurpetr.philkamonitor.utils.FileSaver;
import com.yurpetr.philkamonitor.utils.PhilkaConstants;
import com.yurpetr.philkamonitor.utils.PhilkaCookies;
import com.yurpetr.philkamonitor.utils.PostParser;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PhilkaChecker {

	private PhilkaChecker() {
	}

	public static String getLastPostWithKeys() {

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
			StringBuilder fileContent = new StringBuilder();
			List<Post> postList = new ArrayList<>();

			for (Element postBlock : postBlocks) {
				log.info("Post ID: {}", postBlock.id());
				Optional<Post> postOptional = PostParser.parsePostBlock(postBlock);
				postOptional.ifPresent(post -> {
					fileContent.append(post.toString());
					postList.add(post);
				});
			}
			FileSaver.saveTextToFile(fileContent.toString(), "keys.txt");

			postList.stream().forEach(PhilkaDatabaseSaver::savePostToDatabase);

			return postList.getLast().toString();
		} catch (IOException e) {
			log.error(e.getMessage());
		}

		return "";
	}

}
