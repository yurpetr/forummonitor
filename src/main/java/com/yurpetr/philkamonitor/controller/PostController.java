package com.yurpetr.philkamonitor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yurpetr.philkamonitor.model.Post;
import com.yurpetr.philkamonitor.service.PostService;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public Post createPost(@RequestBody Post post) {
        // Save the post using PostService
        return postService.savePost(post);
    }

    @GetMapping("/{postId}")
    public Post getPostById(@PathVariable Long postId) {
        // Get the post by ID using PostService
        return postService.getPostById(postId)
            .orElseThrow(() -> new RuntimeException("Post not found"));
    }
}
