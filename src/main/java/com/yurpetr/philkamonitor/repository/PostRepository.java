package com.yurpetr.philkamonitor.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.yurpetr.philkamonitor.model.Post;

public interface PostRepository extends CrudRepository<Post, Long> {

	List<Post> findByPostId(int postId);

}
