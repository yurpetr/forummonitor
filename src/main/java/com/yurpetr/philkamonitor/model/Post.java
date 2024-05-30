package com.yurpetr.philkamonitor.model;

import java.time.OffsetDateTime;

import com.yurpetr.philkamonitor.utils.PostExporter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Data
@Entity
@Table(name = "posts", uniqueConstraints = @UniqueConstraint(columnNames = "postId"))
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(unique = true)
	private int postId;
	private OffsetDateTime publishTime;

	@Column(columnDefinition = "TEXT")
	private String keys;
	private String link;

	private Post() {
	}

	private Post(int postId, OffsetDateTime publishTime, String keys, String link) {
		this.postId = postId;
		this.publishTime = publishTime;
		this.keys = keys;
		this.link = link;
	}

	@Override
	public String toString() {
		return PostExporter.toString(this);
	}

	public static PostBuilder builder() {
		return new PostBuilder();
	}

	public static class PostBuilder {
		private int postId;
		private OffsetDateTime publishTime;
		private String keys;
		private String link;

		public PostBuilder setPostId(final int postId) {
			this.postId = postId;
			return this;
		}

		public PostBuilder setPublishTime(final OffsetDateTime publishTime) {
			this.publishTime = publishTime;
			return this;
		}

		public PostBuilder setKeys(final String keys) {
			this.keys = keys;
			return this;
		}

		public PostBuilder setLink(final String link) {
			this.link = link;
			return this;
		}

		public Post build() {
			return new Post(postId, publishTime, keys, link);
		}

	}

}
