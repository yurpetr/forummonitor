package com.yurpetr.philkamonitor.service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.yurpetr.philkamonitor.config.StaticPropertyHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageSender {
	public static int sendMessage(String message) {
		HttpResponse<String> response = null;

		HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5))
				.version(HttpClient.Version.HTTP_2).build();

		UriBuilder builder = UriComponentsBuilder.fromUriString("https://api.telegram.org").path("/{token}/sendMessage")
				.queryParam("chat_id", StaticPropertyHolder.getChatId()).queryParam("text", message)
				.queryParam("parse_mode", "html");

		HttpRequest request = HttpRequest.newBuilder().GET()
				.uri(builder.build("bot" + StaticPropertyHolder.getBotToken())).timeout(Duration.ofSeconds(5)).build();
		try {
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException ioe) {
			log.warn("Error sending message to Telegram Bot");
		} catch (InterruptedException ie) {
			log.error("InterruptedException: ", ie);
			Thread.currentThread().interrupt();
		}

		return (response != null) ? response.statusCode() : -1;
	}
}
