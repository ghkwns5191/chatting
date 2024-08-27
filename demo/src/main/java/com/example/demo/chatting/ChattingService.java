package com.example.demo.chatting;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ChattingService {
	
	@Autowired
	SqlSession sql;
	
	private static final String NAMESPACE = "chatting.";
	
	public Map<String, Object> getDialogs() {
		return sql.selectOne(NAMESPACE + "getDialogs");
	}
}
