package com.health.dao;

import org.mybatis.spring.SqlSessionTemplate;

import com.health.dto.AdminDTO;

public class DietDAO {
	

	//의존성 주입
	private SqlSessionTemplate sessionTemplate;
	
	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}
	
	//삽입
	public void insertData(AdminDTO dto) {
		
		sessionTemplate.insert("com.health.admin.insertData",dto);
		
	}
	
	//num으로 조회한 한 개의 데이터
	public AdminDTO getReadData(String admin_id) {
		
		AdminDTO dto = sessionTemplate.selectOne("com.health.admin.getReadData", admin_id);
		
		return dto;
	}
	
	
	//수정
	public void updateData(AdminDTO dto) {
		
		sessionTemplate.update("com.health.admin.updateData",dto);
		
	}
	
	
	//삭제
	public void deleteData(String admin_id) {
		
		sessionTemplate.delete("com.health.admin.deleteData",admin_id);
		
	}
	

}
