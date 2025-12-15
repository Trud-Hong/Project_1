package com.health.controller;

import java.util.List;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.text.SimpleDateFormat;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.health.dao.AdminDAO;
import com.health.dao.BloodPressureDAO;
import com.health.dao.BqDAO;
import com.health.dao.ConditionScoreDAO;
import com.health.dao.HabitDAO;
import com.health.dao.MemberDAO;
import com.health.dao.ScoreDAO;
import com.health.dao.ExerciseDAO;
import com.health.dto.AdminDTO;
import com.health.dao.SleepRecordDAO;
import com.health.dto.BloodPressureDTO;
import com.health.dto.BqDTO;
import com.health.dto.CustomInfo;
import com.health.dto.HabitDTO;
import com.health.dto.MemberDTO;

import com.health.dto.ReviewDTO;
import com.health.dto.ExerciseDTO;
import com.health.util.MyUtil;
import com.health.dto.SleepRecordDTO;
import com.health.dto.WeightDTO;


@Controller
public class HomeController {

	@Autowired
	@Qualifier("MemberDAO")
	MemberDAO member_dao;
	
	@Autowired
	@Qualifier("adminDAO")
	AdminDAO admin_dao;
	

	@Autowired
	MyUtil myUtil;
	
	@Autowired
	@Qualifier("bqDAO")
	BqDAO bq_dao;
	
	@Autowired
	@Qualifier("sleepDAO")
	SleepRecordDAO sleep_dao;

	
	@Autowired
	@Qualifier("bloodPressureDAO")
	BloodPressureDAO bloodPressure_dao;
	
	@Autowired
	@Qualifier("habitDAO")
	HabitDAO habitDAO;
	
	@Autowired
	@Qualifier("scoreDAO")
	ScoreDAO score_dao;


	@Autowired
	private ExerciseDAO exerciseDAO;

	
	//날짜 + 시간 값 시와 분만 출력되도록 하는 함수
	private static String toHm(String ts) {
		if (ts == null || ts.length() < 5) return null;
		int space = ts.indexOf(' ');
		if (space >= 0 && ts.length() >= space + 6) return ts.substring(space + 1, space + 6); 
		//"20250912 12:30:00" → "12:30"
		//"12:30:00" → "12:30", 이미 "HH:mm"이면 그대로 앞 5자
		return ts.substring(0, 5);
	}
	

	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main(HttpServletRequest req, BqDTO dto, BloodPressureDTO dtoBp,
			HabitDTO dtoHb) {

		int totalScore = score_dao.conditionTotalScore(dto.getMember_no());
		req.setAttribute("totalScore", totalScore);
		
	    HttpSession session = req.getSession();
	    CustomInfo info = (CustomInfo) session.getAttribute("customInfo");
	    
	    if(info != null) {
	        
	        int member_no = info.getMember_no();
	        
	        List<BqDTO> listsBq = bq_dao.getReadData(member_no);
	        
	        if (listsBq != null && !listsBq.isEmpty()) {
	            BqDTO existing = listsBq.get(0);
	            req.setAttribute("dto", existing);
	        } else {
	            req.setAttribute("dto", null);
	        }
	        
	        int memberNo = info.getMember_no();
	        
	        List<BloodPressureDTO> listsBp = bloodPressure_dao.getReadData(memberNo);
	        
	        if (listsBp != null && !listsBp.isEmpty()) {
	            BloodPressureDTO existing = listsBp.get(0);
	            req.setAttribute("dtoBp", existing);
	        } else {
	            req.setAttribute("dtoBp", null);
	        }
	        System.out.println("dtoBp : " + dtoBp);
	        

	        // 수면 데이터 처리 - 취침시간만 HH:mm 형태로 표시
	        List<HabitDTO> listsHb = habitDAO.getReadData(member_no);
	        int habitNum = habitDAO.getDataCount(member_no);
	        
	        req.setAttribute("habitNum", habitNum);
	        
	        if (listsHb != null && !listsHb.isEmpty()) {
	            HabitDTO existing = listsHb.get(0);
	            req.setAttribute("dtoHb", existing);
	            
	            //시간 HH:MI으로 출력하기 위해 String으로 잘라내기
	            if (existing != null) {
	            	String breakfastTimeStr = toHm(existing.getBreakfast_time());
	            	String lunchTimeStr = toHm(existing.getLunch_time());
	            	String dinnerTimeStr = toHm(existing.getDinner_time());

	            	String recordDate = existing.getRecord_date().substring(0, 10);
	            	
	            	System.out.println(recordDate);
	            	
	            	req.setAttribute("recordDate", recordDate);
	            	req.setAttribute("breakfastTimeStr", breakfastTimeStr);
	            	req.setAttribute("lunchTimeStr", lunchTimeStr);
	            	req.setAttribute("dinnerTimeStr", dinnerTimeStr);
	            }
	            
	        } else {
	            req.setAttribute("dtoHb", null);
	        }

	       System.out.println("dtoHb : " + dtoHb);

	       
	       
	       
	        
	        try {

	            System.out.println("메인페이지 - 수면 데이터 조회 시작, 회원번호: " + member_no);
	            
	            // 최신 수면 기록 조회 (가장 최근에 입력한 데이터)
	            SleepRecordDTO sleep_result = sleep_dao.getReadData(member_no);
	            
	            if (sleep_result != null) {
	                System.out.println("메인페이지 - 최신 수면 데이터 발견: " 
	                    + sleep_result.getSleepHours() + "시간, 날짜: " + sleep_result.getBedtime());
	                
	                // 취침시간을 HH:mm 형태로 변환
	                if (sleep_result.getBedtime() != null) {
	                    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
	                    String bedtimeDisplay = timeFormat.format(sleep_result.getBedtime());
	                    req.setAttribute("bedtimeDisplay", bedtimeDisplay);
	                    System.out.println("취침시간 표시용으로 변환: " + bedtimeDisplay);
	                } else {
	                    req.setAttribute("bedtimeDisplay", "미입력");
	                }
	                
	                req.setAttribute("sleep_dto", sleep_result);
	                
	                // 수면 권장사항도 함께 전달
	                String recommendation = sleep_dao.getRecommendation((int)sleep_result.getSleepHours());
	                req.setAttribute("sleep_recommendation", recommendation);
	                
	            } else {
	                System.out.println("메인페이지 - 수면 데이터 없음");
	                req.setAttribute("sleep_dto", null);
	                req.setAttribute("bedtimeDisplay", "미입력");
	                req.setAttribute("sleep_recommendation", "아직 수면 기록이 없습니다. 기록을 시작해보세요!");
	            }
	            
	        } catch (Exception e) {
	            System.out.println("메인페이지 - 수면 데이터 조회 중 오류: " + e.getMessage());
	            e.printStackTrace();
	            req.setAttribute("sleep_dto", null);
	            req.setAttribute("bedtimeDisplay", "오류");
	            req.setAttribute("sleep_recommendation", "데이터 로드 중 오류가 발생했습니다.");
	        }
	        
	     // === 운동 데이터 조회 추가 ===
	        try {
	            System.out.println("메인페이지 - 운동 데이터 조회 시작, 회원번호: " + member_no);
	            
	            // 오늘 날짜 구하기
	            String today = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
	            System.out.println("메인페이지 - 오늘 날짜: " + today);
	            
	            Map<String, Object> exerciseParams = new HashMap<String, Object>();
	            exerciseParams.put("member_no", member_no);
	            exerciseParams.put("exercise_date", today);
	            
	            System.out.println("메인페이지 - 파라미터 member_no: " + exerciseParams.get("member_no"));
	            System.out.println("메인페이지 - 파라미터 exercise_date: " + exerciseParams.get("exercise_date"));
	            
	            List<ExerciseDTO> todayExercises = exerciseDAO.getTodayExercise(exerciseParams);
	            
	            System.out.println("메인페이지 - 조회 결과 null 여부: " + (todayExercises == null));
	            if (todayExercises != null) {
	                System.out.println("메인페이지 - 조회 결과 크기: " + todayExercises.size());
	                for (int i = 0; i < todayExercises.size(); i++) {
	                    ExerciseDTO ex = todayExercises.get(i);
	                    System.out.println("운동 " + (i+1) + ": " + ex.getName() + ", " + ex.getDuration_minutes() + "분, " + ex.getCalories() + "kcal");
	                }
	            }
	            
	            req.setAttribute("todayExercises", todayExercises);
	            
	            // === 디버깅 코드 추가 ===
	            System.out.println("=== JSP 전달 데이터 확인 ===");
	            System.out.println("todayExercises null 여부: " + (todayExercises == null));
	            if (todayExercises != null) {
	                System.out.println("todayExercises 크기: " + todayExercises.size());
	                System.out.println("첫 번째 운동명: " + (todayExercises.size() > 0 ? todayExercises.get(0).getName() : "없음"));
	            }
	            // 한 번 더 설정 (혹시 모를 문제 해결용)
	            req.setAttribute("exerciseList", todayExercises);
	            System.out.println("JSP로 데이터 전달 완료");
	            
	            System.out.println("메인페이지 - 오늘 운동 기록 조회 완료, 건수: " + 
	                (todayExercises != null ? todayExercises.size() : 0));
	            
	        } catch (Exception e) {
	            System.out.println("메인페이지 - 운동 데이터 조회 중 오류: " + e.getMessage());
	            e.printStackTrace();
	            // Java 1.6 호환을 위해 Diamond Operator 제거
	            List<ExerciseDTO> emptyList = new ArrayList<ExerciseDTO>();
	            req.setAttribute("todayExercises", emptyList);

	        }
	        
	    }
	    

	    System.out.println("JSP 경로로 이동: /health/main");
	    return "/health/main";
	
	}

	@RequestMapping(value = "/diet", method = RequestMethod.GET)
	public String diet(HttpServletRequest req) {
		
		HttpSession session = req.getSession();
		CustomInfo info = (CustomInfo) session.getAttribute("customInfo");
		
		if (info == null) {
			return "redirect:/login.do";
		}
		
		return "/health/diet";
	}
	

	
//	@RequestMapping(value = "/profile", method = RequestMethod.GET)
//	public String profile() {
//		return "/health/profile";
//	}
	
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/goal", method = RequestMethod.GET)
	public String goal(MemberDTO dto ,HttpServletRequest req) {
		
		HttpSession session = req.getSession();
		CustomInfo info = (CustomInfo) session.getAttribute("customInfo");
		
		if (info == null) {
			return "redirect:/login.do";
		}
		
	    System.out.println("Member_no: " + info.getMember_no());
	    System.out.println("Goal: " + (dto != null ? dto.getGoal() : "null"));
		
		dto = member_dao.getGoal(info.getMember_no());
		req.setAttribute("dto", dto);

		return "/health/goal";
	}
	

	

	

	
}

