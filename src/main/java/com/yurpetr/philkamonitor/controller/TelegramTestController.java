package com.yurpetr.philkamonitor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yurpetr.philkamonitor.model.TextMessage;
import com.yurpetr.philkamonitor.service.MessageSender;

@Controller
public class TelegramTestController {

	@GetMapping("/tg-test")
	public ModelAndView tgTest() {
		ModelAndView mav = new ModelAndView("tg_test");
		mav.addObject("textmessage", new TextMessage());
		return mav;
	}

	@PostMapping("/tg-send")
	public ModelAndView home(@ModelAttribute TextMessage message) {
		MessageSender.sendMessage(message.getMessage());
		return new ModelAndView("redirect:/tg-test");
	}

}
