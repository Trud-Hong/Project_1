package com.health.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.health.dao.SleepRecordDAO;
import com.health.dto.CustomInfo;
import com.health.dto.SleepRecordDTO;

@Controller
public class SleepRecordController {
	
	@Autowired
	@Qualifier("sleepDAO")
	SleepRecordDAO sleep_dao;
	
	@RequestMapping(value = "/sleep", method = RequestMethod.GET)
	public String sleep(HttpServletRequest req) {
	    try {
	        HttpSession session = req.getSession();
	        CustomInfo info = (CustomInfo) session.getAttribute("customInfo");

	        if (info != null) {
	            int member_no = info.getMember_no();
	            System.out.println("수면 페이지 접근 - 회원번호: " + member_no);

	            try {
	                SleepRecordDTO sleep_result = sleep_dao.getReadData(member_no);
	                System.out.println("수면 데이터 조회 결과: " + (sleep_result != null ? "데이터 있음" : "데이터 없음"));
	                if (sleep_result != null) {
	                    System.out.println("기존 수면 데이터 - 시간: " + sleep_result.getSleepHours() + ", 취침: " + sleep_result.getBedtime());
	                }
	                req.setAttribute("sleep_dto", sleep_result);
	                
	            } catch (Exception e) {
	                System.out.println("수면 데이터 조회 중 오류: " + e.getMessage());
	                e.printStackTrace();
	                req.setAttribute("sleep_dto", null);
	            }
	        } else {
	            System.out.println("세션 정보가 없습니다. 로그인이 필요합니다.");
	            return "redirect:/login.do";
	        }
	    } catch (Exception e) {
	        System.out.println("수면 페이지 접근 중 오류: " + e.getMessage());
	        e.printStackTrace();
	    }

	    return "/health/sleepRecord";
	}

	@RequestMapping(value = "/sleep_ok", method = RequestMethod.POST)
	public String sleep_ok(SleepRecordDTO dto, HttpServletRequest req) {
	    try {
	        HttpSession session = req.getSession();
	        CustomInfo info = (CustomInfo) session.getAttribute("customInfo");

	        if (info == null) {
	            return "redirect:/login.do";
	        }

	        int member_no = info.getMember_no();
	        dto.setMemberNo(member_no);  // 올바른 코드

	        System.out.println("=== 수면 저장 디버깅 ===");
	        System.out.println("저장할 회원번호: " + member_no);
	        System.out.println("받은 sleepHours 원본 값: " + dto.getSleepHours());
	        System.out.println("받은 bedtimeString 원본 값: " + dto.getBedtimeString());

	        // DTO에서 이미 setBedtimeString 호출 시 자동 변환됨
	        System.out.println("DTO 자동 변환 후 bedtime: " + dto.getBedtime());

	        // INSERT 직전 DTO 상태 확인
	        System.out.println("=== INSERT 직전 DTO 상태 ===");
	        System.out.println("member_no: " + dto.getMemberNo()); 
	        System.out.println("sleepHours: " + dto.getSleepHours());
	        System.out.println("bedtime: " + dto.getBedtime());
	        
	        try {
	            // 항상 새로운 데이터를 INSERT (매번 새 기록 추가)
	            System.out.println("새 수면 기록 삽입 시작");
	            
	            sleep_dao.insertData(dto);
	            System.out.println("INSERT 실행 완료");

	            System.out.println("수면 데이터 저장 성공!");
	            
	            // 저장 후 바로 조회해서 확인
	            SleepRecordDTO saved = sleep_dao.getReadData(member_no);
	            System.out.println("저장 후 조회 결과: " + (saved != null ? "성공" : "실패"));
	            if (saved != null) {
	                System.out.println("저장된 데이터 확인 - 시간: " + saved.getSleepHours() + ", 날짜: " + saved.getBedtime());
	            }
	            
	            // 성공 메시지 설정
	            req.setAttribute("message", "새로운 수면 기록이 성공적으로 저장되었습니다.");
	            
	        } catch (Exception dbException) {
	            System.out.println("INSERT 실행 중 오류: " + dbException.getMessage());
	            dbException.printStackTrace();
	            req.setAttribute("message", "데이터 저장 중 오류가 발생했습니다: " + dbException.getMessage());
	        }
	        
	        System.out.println("=== 저장 디버깅 종료 ===");

	    } catch (Exception e) {
	        System.out.println("수면 데이터 저장 중 전체 오류: " + e.getMessage());
	        e.printStackTrace();
	        req.setAttribute("message", "시스템 오류가 발생했습니다.");
	    }

	    return "redirect:/main.do";
	}

}