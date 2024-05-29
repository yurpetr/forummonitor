package com.yurpetr.philkamonitor.utils;

import java.time.OffsetDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.jsoup.nodes.Element;

import com.yurpetr.philkamonitor.model.Post;
import com.yurpetr.philkamonitor.model.Post.PostBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostParser {

	private PostParser() {
	}

	public static Optional<Post> parsePostBlock(Element postBlock) {
		PostBuilder postBuilder = Post.getPostBuilder();

		Element linkBlock = postBlock.getElementsByAttribute("data-entry-pid").first();
		postBuilder.setLink(linkBlock.attr("href"));

		try {
			int postId = Integer.parseInt(linkBlock.attr("data-entry-pid"));
			postBuilder.setPostId(postId);
			if (postId == 0) {
				throw new NoSuchElementException("Post id not found");
			}
		} catch (NumberFormatException e) {
			log.error("New post id format");
		}

		postBuilder
				.setPublishTime(OffsetDateTime.parse(postBlock.getElementsByClass("published").first().attr("title")));

		StringBuilder sb = new StringBuilder();
		for (var element : postBlock.getElementsByClass("bbc_spoiler")) {
			sb.append(element.getElementsByClass("prettyprint").first().wholeText());
			sb.append("\n\n");
		}
		String postKeys = sb.toString();
		postBuilder.setKeys(postKeys);

		if (postKeys.isBlank()) {
			return Optional.empty();
		}
		return Optional.of(postBuilder.build());
	}

}
