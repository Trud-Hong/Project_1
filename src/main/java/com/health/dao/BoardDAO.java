package com.health.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;

import com.health.dto.BoardDTO;

public class BoardDAO {
	

	private SqlSessionTemplate sessionTemplate;
	
	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}
	

	public int getMaxNum() {
		
		int maxNum = 0;
		
		maxNum = sessionTemplate.selectOne("com.health.board.maxNum");
		
		return maxNum;
	}
	
	public void insertData(BoardDTO dto) {
		
		sessionTemplate.insert("com.health.board.insertData",dto);
	}
	
	public int getDataCount(String category, String searchKey, String searchValue) {
	    Map<String, Object> params = new HashMap<>();
	    params.put("category", category);
	    params.put("searchKey", searchKey);
	    params.put("searchValue", searchValue);
	    return sessionTemplate.selectOne("com.health.board.getDataCount", params);
	}

	public List<BoardDTO> getLists(int start, int end, String category, String searchKey, String searchValue) {
	    Map<String, Object> params = new HashMap<>();
	    params.put("start", start);
	    params.put("end", end);
	    params.put("category", category);
	    params.put("searchKey", searchKey);
	    params.put("searchValue", searchValue);
	    return sessionTemplate.selectList("com.health.board.getLists", params);
	}
	
	public void updateHitCount(int board_id) {
		
		sessionTemplate.update("com.health.board.updateHitCount",board_id);

	}


	public BoardDTO getReadData(int board_id) {

		BoardDTO dto = sessionTemplate.selectOne("com.health.board.getReadData",board_id);

		
		return dto;
	}
	


	public void updateData(BoardDTO dto) {
		
		sessionTemplate.update("com.health.board.updateData", dto);
		
	}
    

	public void deleteData(int board_id) {
		
		sessionTemplate.delete("com.health.board.deleteData",board_id);
		
	}
	

}
