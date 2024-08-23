package com.example.demo.chatting;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class ChattingController {

	@RequestMapping("/")
	public String main(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();
		String sessionVal = session.getId();
		model.addAttribute("sessionVal", sessionVal);
		
		return "/ui/index";
	}
}
