package com.example.demo.chatting;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class ChattingController {
	
	@Autowired
	private ChattingService chattingService;

	@RequestMapping("/")
	public String main(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();
		String sessionVal = session.getId();
		model.addAttribute("sessionVal", sessionVal);
		
		Map<String, Object> datas = new HashMap<>();
//		datas = this.chattingService.getDialogs();
		
		return "ui/index";
	}
}
