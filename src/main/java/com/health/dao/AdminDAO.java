package com.health.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.health.dto.AdminDTO;

public class AdminDAO {

	@Autowired
	private SqlSessionTemplate sessionTemplate;
	
	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}
	
	public void insertData(AdminDTO dto) {
		
		sessionTemplate.insert("com.health.admin.insertData",dto);
		
	}
	
	public AdminDTO getReadData(String admin_id) {
		
		AdminDTO dto = sessionTemplate.selectOne("com.health.admin.getReadData", admin_id);
		
		return dto;
	}
	
	
	public void updateData(AdminDTO dto) {
		
		sessionTemplate.update("com.health.admin.updateData",dto);
		
	}
	
	
	public void deleteData(String admin_id) {
		
		sessionTemplate.delete("com.health.admin.deleteData",admin_id);
		
	}

}
