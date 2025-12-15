package com.health.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import com.health.dto.BloodPressureDTO;
import com.health.dto.BqDTO;

public class BqDAO {

private SqlSessionTemplate sessionTemplate;
	
	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}
	
	public void insertData(BqDTO dto) {
		
		sessionTemplate.insert("com.health.bq.insertData",dto);
	}
	
	public List<BqDTO> getReadData(int member_no) {
	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put("member_no", member_no);

	    return sessionTemplate.selectList("com.health.bq.getReadData", paramMap);
	}
	
	public void updateData(BqDTO dto) {
		
		sessionTemplate.update("com.health.bq.updateData", dto);
	}
	
	


}
