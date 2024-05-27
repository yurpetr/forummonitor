package com.yurpetr.philkamonitor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class StaticPropertyHolder {
	private static String BOT_TOKEN;
	private static String CHAT_ID;

	@Value("${telegram.bot.token}")
	public void setBotToken(String token) {
		BOT_TOKEN = token;
	}

	@Value("${telegram.bot.chat_id}")
	public void setChatId(String chatId) {
		CHAT_ID = chatId;
	}

	public static String getBotToken() {
		return BOT_TOKEN;
	}

	public static String getChatId() {
		return CHAT_ID;
	}
}
