package com.yurpetr.philkamonitor.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.yurpetr.philkamonitor.service.MessageSender;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
//@EnableScheduling
public class ScheduledSender {
	private int counter = 0;

	public void doSomething() {
		counter++;
		log.info("Do something: {}", counter);
//		MessageSender.sendMessage("Test " + counter);

	}

}
