package com.yurpetr.philkamonitor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yurpetr.philkamonitor.model.Post;

import jakarta.annotation.PostConstruct;

@Component
public class PhilkaDatabaseSaver {

	private static PostService service;

	@Autowired
	private PostService tmpService;

	@PostConstruct
	public void init() {
		service = tmpService;
	}

	private PhilkaDatabaseSaver() {
	}

	public static void savePostToDatabase(Post post) {
		service.savePostIfNotExists(post);
	}

}
