package com.health.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import com.health.dto.AdminDTO;
import com.health.dto.BqDTO;
import com.health.dto.ExerciseDTO;
import com.health.dto.HabitDTO;
import com.health.dto.ReviewDTO;

public class HabitDAO {

	private SqlSessionTemplate sessionTemplate;
	
	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}
	
	public int getMaxNum() {
		
		Integer result = sessionTemplate.selectOne("com.health.habit.maxNum");

		return result;
	}
	
	public void insertData(HabitDTO dto) {
		
		sessionTemplate.insert("com.health.habit.insertData",dto);
		
	}
	
	public int getDataCount(int member_no) {
		
//		int totalCount = sessionTemplate.selectOne("com.health.habit.getDataCount", member_no);
//		
//		return totalCount;
		
		Integer totalCountObj = sessionTemplate.selectOne("com.health.habit.getDataCount", member_no);
		int totalCount = (totalCountObj != null) ? totalCountObj : 0;
		return totalCount;
		
	}
	
//	public HabitDTO getReadData(int habit_id) {
//		
//		HabitDTO dto = sessionTemplate.selectOne("com.health.habit.getReadData", habit_id);
//		
//		return dto;
//	}
	
	public List<HabitDTO> getReadData(int member_no) {
	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put("member_no", member_no);

	    return sessionTemplate.selectList("com.health.habit.getReadData", paramMap);
	}
	
	
	public void updateData(HabitDTO dto) {
		
		sessionTemplate.update("com.health.habit.updateData",dto);
		
	}
	
	
	public void deleteData(int habit_id) {
		
		sessionTemplate.delete("com.health.habit.deleteData",habit_id);
		
	}
	
	
	public List<HabitDTO> getLists(int member_no){
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("member_no", member_no);
		
		List<HabitDTO> lists = sessionTemplate.selectList("com.health.habit.getLists",params);
		
		return lists;
	}
	
}
