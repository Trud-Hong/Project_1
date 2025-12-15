package com.health.dao;

import java.util.List;
import java.util.Map;
import org.mybatis.spring.SqlSessionTemplate;
import com.health.dto.ExerciseDTO;

public class ExerciseDAO {
	
	// 세션 템플릿
	private SqlSessionTemplate sessionTemplate;
	
	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}
	
	// 운동 데이터 추가
	public void insertData(ExerciseDTO dto) {
		sessionTemplate.insert("com.health.exercise.insertData", dto);
	}
	
	// 최대 번호 조회
	public int maxNum() {
		Integer result = sessionTemplate.selectOne("com.health.exercise.maxNum");
		return result != null ? result : 0;
	}
	
	// 특정 운동 기록 조회 (ex_id로 조회)
	public ExerciseDTO getReadData(int ex_id) {
		ExerciseDTO dto = sessionTemplate.selectOne("com.health.exercise.getReadData", ex_id);
		return dto;
	}
	
	// 특정 회원의 운동 데이터 조회 (최신 1건)
	public ExerciseDTO getLatestExercise(int member_no) {
		return sessionTemplate.selectOne("com.health.exercise.getLatestExercise", member_no);
	}
	
	// 특정 회원의 모든 운동 데이터 조회
	public List<ExerciseDTO> getExerciseList(int member_no) {
		return sessionTemplate.selectList("com.health.exercise.getExerciseList", member_no);
	}
	
	// 특정 회원의 특정 날짜 운동 데이터 조회
	public List<ExerciseDTO> getExerciseByDate(int member_no, String exercise_date) {
		ExerciseDTO searchDto = new ExerciseDTO();
		searchDto.setMember_no(member_no);
		searchDto.setExercise_date(exercise_date);
		return sessionTemplate.selectList("com.health.exercise.getExerciseByDate", searchDto);
	}
	
	// 특정 회원의 특정 날짜 운동 데이터 조회 (메인페이지용)
	public List<ExerciseDTO> getTodayExercise(Map<String, Object> params) {
		return sessionTemplate.selectList("com.health.exercise.getTodayExercise", params);
	}
	
	// 운동 데이터 수정
	public void updateData(ExerciseDTO dto) {
		sessionTemplate.update("com.health.exercise.updateData", dto);
	}
	
	// 운동 데이터 삭제
	public void deleteData(int ex_id) {
		sessionTemplate.delete("com.health.exercise.deleteData", ex_id);
	}
	
	// 특정 회원의 운동 기록 총 개수 조회
	public int getExerciseCount(int member_no) {
		Integer result = sessionTemplate.selectOne("com.health.exercise.getExerciseCount", member_no);
		return result != null ? result : 0;
	}
	
	// 특정 회원의 총 소모 칼로리 조회
	public int getTotalCalories(int member_no) {
		Integer result = sessionTemplate.selectOne("com.health.exercise.getTotalCalories", member_no);
		return result != null ? result : 0;
	}
	
	// 운동 추천 메시지 (칼로리 기반)
	public String getRecommendation(int calories) {
		if (calories < 200) {
			return "좋은 시작입니다! 조금씩 운동량을 늘려보세요.";
		} else if (calories < 400) {
			return "꾸준히 잘 하고 계시네요! 이대로 유지하세요.";
		} else if (calories < 600) {
			return "훌륭합니다! 활발한 운동을 하고 계시네요.";
		} else {
			return "매우 활동적이시네요! 충분한 휴식도 챙기세요.";
		}
	}
}