package com.yurpetr.philkamonitor.utils;

import java.time.format.DateTimeFormatter;

import com.yurpetr.philkamonitor.model.Post;

public class PostTransformer {

	private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("HH:mm:ss dd/MM/yyyy (OOOO)");

	private PostTransformer() {
	}

	public static String toString(Post post) {
		return "Post ID: " + post.getPostId() + "\n<a href=\"" + post.getLink() + "\">-=Link=-</a>" + "\nSent at: "
				+ post.getPublishTime().format(FMT) + "\n\n" + post.getKeys() + "\n\n\n";
	}

}
