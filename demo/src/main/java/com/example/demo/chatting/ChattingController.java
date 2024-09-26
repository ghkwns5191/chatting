package com.example.demo.chatting;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class ChattingController {
	
	private static final Logger log = LoggerFactory.getLogger(ChattingController.class);
	
	
	@Autowired
	private ChattingService chattingService;

	@RequestMapping("/")
	public String main(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();
		String sessionVal = session.getId();
		model.addAttribute("sessionVal", sessionVal);
		
		Map<String, Object> datas = new HashMap<>();
		datas = this.chattingService.getDialogs();
		
		return "ui/login";
	}
	
	
	@RequestMapping("/chatting")
	public String chatting(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();
		String sessionVal = session.getId();
		model.addAttribute("sessionVal", sessionVal);
		
		Map<String, Object> datas = new HashMap<>();
//		datas = this.chattingService.getDialogs();
		
		return "ui/login";
	}
	
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();
		String sessionVal = session.getId();
		model.addAttribute("sessionVal", sessionVal);
		
//		this.chattingService.login(request, response);
//		
//		Map<String, Object> datas = new HashMap<>();
//		datas = this.chattingService.getDialogs();
		
		return "ui/login";
	}
	
	
	@RequestMapping("/room")
	public String room(HttpServletRequest request, HttpServletResponse response, Model model) {
		HttpSession session = request.getSession();
		
		String sessionVal = session.getId();
		model.addAttribute("sessionVal", sessionVal);
		
		log.info(sessionVal);
		Map<String, Object> datas = new HashMap<>();
//		datas = this.chattingService.getDialogs();
		
		return "ui/index";
	}
	
	
	@RequestMapping(value = "/login", method=RequestMethod.POST)
	public ResponseEntity<String> login(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> loginInfo) {
		boolean result = false;
		log.info(loginInfo.toString());
		Map<String, Object> loginResult = this.chattingService.login(request, response, loginInfo);
		log.info(loginResult.toString());
		if ((boolean) loginResult.get("loginFlag")) {
			result = true;
			
			
			HttpSession newSession = (HttpSession) loginResult.get("session");
			newSession.setAttribute("username", Util.mapToString(loginResult, "username"));
			log.info(newSession.toString());
			Cookie cookie = new Cookie("username", Util.mapToString(loginResult, "username"));
			response.addCookie(cookie);  // 쿠키를 클라이언트에 추가
			log.info(cookie.toString());
		} else {
			result = false;
		}
		String res = "";
		
		if (result) {
			res = "success";
		} else {
			res = "fail";
		}
		
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/join", method=RequestMethod.POST)
	public ResponseEntity<String> join(HttpServletRequest request, HttpServletResponse response, @RequestBody Map<String, Object> joinInfo) {
		boolean result = false;
		
		Map<String, Object> joinResult = this.chattingService.join(request, response, joinInfo);
		if ((boolean) joinResult.get("joinFlag")) {
			result = true;
		} else {
			result = false;
		}
		String res = "";
		if (result) {
			res = "success";
		} else {
			res = "fail";
		}
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
