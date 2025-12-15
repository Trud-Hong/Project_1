package com.health.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.health.dto.MemberDTO;
import com.health.util.PasswordUtil;

public class MemberDAO { 
	
	private SqlSessionTemplate sessionTemplate;
	
	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}

	public void insertData(MemberDTO dto) {
		
		double bmi = dto.getWeight() / (dto.getHeight() * dto.getHeight()) * 10000;
		dto.setBmi(Math.round(bmi * 10) / 10.0);

		String encodedPwd = PasswordUtil.encode(dto.getPassword());
		dto.setPassword(encodedPwd);
		
		sessionTemplate.insert("com.health.member.insertData",dto);
	}
	

	public MemberDTO getReadData(@Param("member_id") String member_id) {

		MemberDTO dto = sessionTemplate.selectOne("com.health.member.getReadData",member_id);
		
		return dto;
	}
	
	public MemberDTO getGoal(@Param("member_no") int member_no) {
		
		MemberDTO dto = sessionTemplate.selectOne("com.health.member.getGoal",member_no);
		
		return dto;
	}
	

	public void updateData(MemberDTO dto) {
		

		double bmi = dto.getWeight() / (dto.getHeight() * dto.getHeight()) * 10000;
		dto.setBmi(Math.round(bmi * 10) / 10.0);
		
		sessionTemplate.update("com.health.member.updateData", dto);
		
	}
	
	public void updateProfile(MemberDTO dto) {
		

		double bmi = dto.getWeight() / (dto.getHeight() * dto.getHeight()) * 10000;
		dto.setBmi(Math.round(bmi * 10) / 10.0);
		
		sessionTemplate.update("com.health.member.updateProfile", dto);
		
	}
    

	public void deleteHabitData(int num) {
		sessionTemplate.delete("com.health.member.deleteHabitData",num);
	}
	public void deleteExerciseData(int num) {
		sessionTemplate.delete("com.health.member.deleteExerciseData",num);
	}
	public void deleteBGData(int num) {
		sessionTemplate.delete("com.health.member.deleteBGData",num);
	}
	public void deleteBPData(int num) {
		sessionTemplate.delete("com.health.member.deleteBPData",num);
	}
	public void deleteSleepData(int num) {
		sessionTemplate.delete("com.health.member.deleteSleepData",num);
	}
	public void deleteCoummunity(int num) {
		sessionTemplate.delete("com.health.member.deleteCoummunity",num);
	}
	public void deleteReview(int num) {
		sessionTemplate.delete("com.health.member.deleteReview",num);
	}
	public void deleteData(int num) {
		sessionTemplate.delete("com.health.member.deleteData",num);
	}


	public void updateGoal(MemberDTO dto) {
		
		sessionTemplate.update("com.health.member.updateGoal",dto);
		
	}

	public void updateWeight(MemberDTO dto) {
	    System.out.println("updateWeight 시작: member_no=" + dto.getMember_no() + ", weight=" + dto.getWeight());
	    double bmi = dto.getWeight() / (dto.getHeight() * dto.getHeight()) * 10000;
	    dto.setBmi(Math.round(bmi * 10) / 10.0);

	    int cnt = sessionTemplate.selectOne("com.health.member.countRecord", dto.getMember_no());
	    System.out.println("countRecord 결과: " + cnt);

	    if (cnt == 0) {
	        sessionTemplate.insert("com.health.member.insertMemberRecord", dto);
	        System.out.println("insertMemberRecord 실행");
	    } else {
	        int updatedRows = sessionTemplate.update("com.health.member.updateWeightMemberRecord", dto);
	        System.out.println("updateWeightMemberRecord 영향받은 행 수: " + updatedRows);
	    }
	    System.out.println("updateWeight 종료");
	}

	public void updateWeightMemberRecord(MemberDTO dto) {
		
		double bmi = dto.getWeight() / (dto.getHeight() * dto.getHeight()) * 10000;
		dto.setBmi(Math.round(bmi * 10) / 10.0);
		 
		int updatedRows = sessionTemplate.update("com.health.member.updateWeightMemberRecord",dto);
		
		System.out.println("updateWeightMemberRecord 영향받은 행 수: " + updatedRows);
		
	}
	
	//이메일 존재 확인
	public boolean isEmailExist(String email) {
	    Integer count = sessionTemplate.selectOne("com.health.member.isEmailExist", email);
	    return count != null && count > 0;
	}
	
	// 인증 코드 저장 (재전송 시 기존 무효화)
	public void saveAuthCode(String email, String code, Date expire) {
	    Map<String, Object> params = new HashMap<String, Object>();
	    params.put("email", email);
	    params.put("code", code);
	    params.put("expire", expire);

	    // MERGE 구문으로 기존 코드를 덮어쓰거나 새로 삽입
	    sessionTemplate.insert("com.health.member.saveAuthCode", params);

	    // 발송 로그 저장 
	    insertEmailAuthLog(email, code); 
	}
	
	//아이디 이메일 일치여부
	public boolean isIdEmailEqual(String id, String email) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("email", email);
		
		int count = sessionTemplate.selectOne("com.health.member.isIdEmailEqual", params);
		
		return count > 0 ? true : false;
		
		
	}
	
	//저장된 인증 코드 조회
	public String getAuthCode(String email) {
	    return sessionTemplate.selectOne("com.health.member.getAuthCode", email);
	}
	
	//이메일로 회원 ID조회
	public String getMemberIdByEmail(String email) {
	    return sessionTemplate.selectOne("com.health.member.getMemberIdByEmail", email);
	}
	
	public void insertEmailAuthLog(String email, String authCode) {
	    Map<String, Object> params = new HashMap<String, Object>();
	    params.put("email", email);
	    params.put("authCode", authCode);
	    sessionTemplate.insert("com.health.member.insertEmailAuthLog", params);
	}
	
	//아이디 존재확인
	public boolean isIdExist(String id) {
	    Integer count = sessionTemplate.selectOne("com.health.member.isIdExist", id);
	    return count != null && count > 0;
	}
	
	//비밀번호 수정
	public void updatePassword(String memberId, String newPassword) {
	    Map<String, Object> params = new HashMap<>();
	    params.put("member_id", memberId);
	    params.put("password", PasswordUtil.encode(newPassword));
	    
	    System.out.println("Mapper 호출 전 params: " + params);
	    int cnt = sessionTemplate.update("com.health.member.updatePassword", params);
	    System.out.println("Mapper update 결과 행 수: " + cnt);
	}

}
