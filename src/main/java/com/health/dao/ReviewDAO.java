package com.health.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import com.health.dto.BoardDTO;
import com.health.dto.MemberDTO;
import com.health.dto.ReviewDTO;

public class ReviewDAO { 
	

	private SqlSessionTemplate sessionTemplate;
	
	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}

	public int getMaxNum() {
		
		int maxNum = 0;
		
		maxNum = sessionTemplate.selectOne("com.health.review.maxNum");
		
		return maxNum;
	}
	
	public void insertData(ReviewDTO dto) {
		
		sessionTemplate.insert("com.health.review.insertData",dto);
	}
	
//	public int getDataCount() {
//		
//		Map<String, Object> params = new HashMap<String, Object>();
//		
//		int totalCount = sessionTemplate.selectOne("com.health.review.getDataCount",params);
//		
//		return totalCount;
//		
//	}	
	public int getDataCount(String searchKey, String searchValue) {
	    Map<String, Object> params = new HashMap<>();
	    params.put("searchKey", searchKey);
	    params.put("searchValue", searchValue);
	    return sessionTemplate.selectOne("com.health.review.getDataCount", params);
	}
	
//	public List<ReviewDTO> getLists(int start, int end){
//		
//		Map<String, Object> params = new HashMap<String, Object>();
//		
//		params.put("start", start);
//		params.put("end", end);
//		
//		List<ReviewDTO> lists = sessionTemplate.selectList("com.health.review.getLists",params);
//		
//		return lists;
//	}
	
	public List<ReviewDTO> getLists(int start, int end,String searchKey, String searchValue) {
	    Map<String, Object> params = new HashMap<>();
	    params.put("start", start);
	    params.put("end", end);
	    params.put("searchKey", searchKey);
	    params.put("searchValue", searchValue);
	    return sessionTemplate.selectList("com.health.review.getLists", params);
	}
	
	public void updateHitCount(int review_id) {
		
		sessionTemplate.update("com.health.review.updateHitCount",review_id);

	}


	public ReviewDTO getReadData(int review_id) {

		ReviewDTO
		dto = sessionTemplate.selectOne("com.health.review.getReadData",review_id);
		
		return dto;
	}
	

	public void updateData(ReviewDTO dto) {
		
		sessionTemplate.update("com.health.review.updateData", dto);
		
	}
    
	public void deleteData(int review_id) {
		
		sessionTemplate.delete("com.health.review.deleteData",review_id);
		
	}


}
