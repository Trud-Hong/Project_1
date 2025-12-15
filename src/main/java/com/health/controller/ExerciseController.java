package com.health.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.health.dao.ExerciseDAO;
import com.health.dto.CustomInfo;
import com.health.dto.ExerciseDTO;

@Controller
public class ExerciseController {
	
	@Autowired
	private ExerciseDAO exerciseDAO;
	
	@RequestMapping(value = "/exercise", method = RequestMethod.GET)
	public String exercise(HttpServletRequest req) {
	    try {
	        HttpSession session = req.getSession();
	        CustomInfo info = (CustomInfo) session.getAttribute("customInfo");

	        if (info != null) {
	            int member_no = info.getMember_no();
	            System.out.println("운동 기록 페이지 접근 - 회원번호: " + member_no);
	            
	        } else {
	            System.out.println("세션 정보가 없습니다. 로그인이 필요합니다.");
	            return "redirect:/login.do";
	        }
	    } catch (Exception e) {
	        System.out.println("운동 페이지 접근 중 오류: " + e.getMessage());
	        e.printStackTrace();
	        return "redirect:/login.do";
	    }

	    return "/health/exercise";
	}
	
	@RequestMapping(value = "/exerciseRecord", method = RequestMethod.POST)
	public String exerciseRecord(HttpServletRequest req) {
	    try {
	        HttpSession session = req.getSession();
	        CustomInfo info = (CustomInfo) session.getAttribute("customInfo");

	        if (info == null) {
	            return "redirect:/login.do";
	        }

	        int member_no = info.getMember_no();
	        
	        // 운동 데이터 받기
	        String category = req.getParameter("category");
	        String exerciseType = req.getParameter("exerciseType");
	        String exerciseDate = req.getParameter("exerciseDate");
	        String hours = req.getParameter("hours");
	        String minutes = req.getParameter("minutes");
	        String intensity = req.getParameter("intensity");
	        String calories = req.getParameter("calories");
	        String memo = req.getParameter("memo");
	        
	        System.out.println("=== 운동 기록 저장 ===");
	        System.out.println("회원번호: " + member_no);
	        System.out.println("카테고리: " + category);
	        System.out.println("운동종류: " + exerciseType);
	        System.out.println("날짜: " + exerciseDate);
	        System.out.println("시간: " + hours + "시간 " + minutes + "분");
	        System.out.println("강도: " + intensity);
	        System.out.println("칼로리: " + calories);
	        System.out.println("메모: " + memo);
	        
	        // ExerciseDTO 객체 생성 및 데이터 설정 (기본 필드만)
	        ExerciseDTO dto = new ExerciseDTO();
	        dto.setMember_no(member_no);
	        dto.setExercise_type_id(Integer.parseInt(exerciseType));
	        dto.setExercise_date(exerciseDate);
	        
	        // 시간을 분으로 변환
	        int totalMinutes = Integer.parseInt(hours) * 60 + Integer.parseInt(minutes);
	        dto.setDuration_minutes(totalMinutes);
	        
	        // 추가 정보는 DTO에는 설정하지만 DB에는 저장되지 않음 (현재 테이블 구조상)
	        // 나중에 테이블 컬럼 추가시 아래 주석 해제
	        // dto.setCategory(category);
	        // dto.setIntensity(Integer.parseInt(intensity));
	        // dto.setCalories(Integer.parseInt(calories));
	        // dto.setMemo(memo);
	        
	        // 데이터베이스에 저장
	        exerciseDAO.insertData(dto);
	        
	        System.out.println("운동 데이터 저장 완료");
	        req.setAttribute("message", "운동 기록이 성공적으로 저장되었습니다.");
	        
	    } catch (Exception e) {
	        System.out.println("운동 데이터 저장 중 오류: " + e.getMessage());
	        e.printStackTrace();
	        req.setAttribute("message", "운동 기록 저장 중 오류가 발생했습니다.");
	    }

	    return "redirect:/main.do";
	}
}