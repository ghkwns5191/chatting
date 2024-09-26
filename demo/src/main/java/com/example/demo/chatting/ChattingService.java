package com.example.demo.chatting;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Transactional
@Service
public class ChattingService {
	
	private static final Logger log = LoggerFactory.getLogger(ChattingService.class);
	
	@Autowired
	SqlSession sql;
	
	
	
	private static final String NAMESPACE = "chatting.";
	
	public Map<String, Object> getDialogs() {
		return sql.selectOne(NAMESPACE + "getDialogs");
	}
	
	
	public Map<String, Object> login(HttpServletRequest request, HttpServletResponse response, Map<String, Object> loginInfo) {
		Map<String, Object> result = new HashMap<>();
		boolean loginFlag = false;
		String username = "";
		HttpSession session = request.getSession();
		SHA256 sha = new SHA256();
		
		Map<String, Object> infoById = Util.mapValidate(sql.selectOne(NAMESPACE + "getInfoById", loginInfo));
		log.info((String)infoById.get("USERNAME"));
		log.info(infoById.toString());
		if ((String)infoById.get("USERNAME") == null) {
			loginFlag = false;

		} else {
			username = Util.mapToString(infoById, "USERNAME");
			String savedPassword = Util.mapToString(infoById, "PASSWORD");
			String inputPassword = Util.mapToString(loginInfo, "password");
			
			try {
				inputPassword = sha.encrypt(inputPassword);
				
				log.info(savedPassword);
				log.info(inputPassword);
				
				loginFlag = sha.validatePassword(savedPassword, inputPassword);
				
				if (loginFlag) {
					request.getSession().invalidate();  // 기존 세션 무효화
					session = request.getSession(true);
				} else {
					
				}
			} catch(Exception e) {
				
			}
			
			
		}
		log.info(username);
		log.info(String.valueOf(loginFlag));
		log.info(session.toString());
		result.put("username", username);
		result.put("loginFlag", loginFlag);
		result.put("session", session);
		
		
		return result;
	}
	
	
	
	public Map<String, Object> join(HttpServletRequest request, HttpServletResponse response, Map<String, Object> joinInfo) {
		
		Map<String, Object> result = new HashMap<>();
		boolean joinFlag = false;
		String password = Util.mapToString(joinInfo, "password");
		SHA256 sha = new SHA256();
		
		
		try {
			password = sha.encrypt(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		joinInfo.put("password", password);
		
		List<Map<String, Object>> usernameData = sql.selectList(NAMESPACE + "checkUsername", joinInfo);
		
		if (usernameData.size() > 0) {
			joinFlag = false;
		} else if (usernameData.size() == 0) {
			joinFlag = true;
			
			String id = UUID.randomUUID().toString();
			joinInfo.put("id", id);
			sql.insert(NAMESPACE + "insertNewUser", joinInfo);
		}
		
		result.put("username", Util.mapToString(joinInfo, "username"));
		result.put("joinFlag", joinFlag);
		
		
		return result;
	}
}
