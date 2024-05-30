package com.yurpetr.philkamonitor.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.yurpetr.philkamonitor.model.Post;
import com.yurpetr.philkamonitor.repository.PostRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PostService {

	private final PostRepository postRepository;

	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public Post savePost(Post post) {
		List<Post> postsByPostId = postRepository.findByPostId(post.getPostId());
		if (postsByPostId.isEmpty()) {
			log.debug("Saving new post to DB");
			return postRepository.save(post);
		}
		log.warn("Post already exist in DB");
		return postsByPostId.getFirst();
	}

	public Optional<Post> getPostById(Long id) {
		return postRepository.findById(id);
	}

	public List<Post> getPostByPostId(int postId) {
		return postRepository.findByPostId(postId);
	}

	// Add methods for loading and saving posts here
	// For example:
	// public Post savePost(Post post) { ... }
	// public Optional<Post> getPostById(Long id) { ... }
}
