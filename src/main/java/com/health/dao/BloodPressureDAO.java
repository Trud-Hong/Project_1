package com.health.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import com.health.dto.BloodPressureDTO;
import com.health.dto.BqDTO;

public class BloodPressureDAO {
	
	private SqlSessionTemplate sessionTemplate;
	
	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}
	
	public void insertData(BloodPressureDTO dtoBp) {
		sessionTemplate.insert("com.health.bloodPressure.insertData",dtoBp);
	}
	
	public List<BloodPressureDTO> getReadData(int memberNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberNo", memberNo);
		
		return sessionTemplate.selectList("com.health.bloodPressure.getReadData",paramMap);
	}
	
	public void updateData(BloodPressureDTO dtoBp) {
		sessionTemplate.update("com.health.bloodPressure.updateData", dtoBp);
	}
	
}