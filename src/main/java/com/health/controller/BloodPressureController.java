package com.health.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.health.dao.BloodPressureDAO;
import com.health.dao.MemberDAO;
import com.health.dto.BloodPressureDTO;
import com.health.dto.BqDTO;
import com.health.dto.CustomInfo;
import com.health.util.MyUtil;

@Controller
public class BloodPressureController {
	
	@Autowired
	@Qualifier("MemberDAO")
	MemberDAO member_dao;
	
	@Autowired
	MyUtil myUtil;
	
	@Autowired
	@Qualifier("bloodPressureDAO")
	BloodPressureDAO bloodPressure_dao;
	
	
	@RequestMapping(value = "/blood", method = RequestMethod.GET)
	public String blood(HttpServletRequest req) {
	    
		HttpSession session = req.getSession();
	    CustomInfo info = (CustomInfo) session.getAttribute("customInfo");

	    if (info == null) {
			return "redirect:/login";
		}
	    
	    if (info != null) {
	        int member_no = info.getMember_no();

		        List<BloodPressureDTO> lists = bloodPressure_dao.getReadData(member_no);
		        
		        if (lists != null && !lists.isEmpty()) {
		            req.setAttribute("dtoBp", lists.get(0));
		        } else {
		            req.setAttribute("dtoBp", null);
		        }
		    }

	    return "/health/blood";

	}


	@RequestMapping(value = "/blood_ok", method = RequestMethod.POST)
	public String blood_ok(BloodPressureDTO dtoBp, HttpServletRequest req) {
	   
	        HttpSession session = req.getSession();
	        
	        CustomInfo info = (CustomInfo) session.getAttribute("customInfo");

	        if (info == null) {
	        	System.out.println("세션에 customInfo가 없음");
	            return "redirect:/login.do";
	        }

	        int member_no = info.getMember_no();
	        String logDateStr = req.getParameter("log_Date");
			 System.out.println("Received log_date: " + logDateStr);  // 로그 추가
			
			if (logDateStr == null || logDateStr.isEmpty()) {
		        throw new IllegalArgumentException("날짜를 입력하지 않았습니다.");
		    }
			
			java.sql.Date log_date = java.sql.Date.valueOf(logDateStr);
	        
			dtoBp.setLog_Date(log_date);
			
			 System.out.println("log_date set in BqDTO: " + dtoBp.getLog_Date());
			
			 dtoBp.setMemberNo(member_no);

	        // 혈압 레벨 계산
	        String bp_level = calculateBloodPressureLevel(dtoBp.getBpHigh(), dtoBp.getBpLow());
	        dtoBp.setBpLevel(bp_level);

	        // 기존 데이터 확인
	        List<BloodPressureDTO> lists = bloodPressure_dao.getReadData(member_no);

			boolean updated = false; // 업데이트 여부 체크

			if (lists != null && !lists.isEmpty()) {
			    for (BloodPressureDTO existing : lists) {
			        if (existing.getLog_Date().equals(dtoBp.getLog_Date())) {
			            // 같은 날짜 있으면 업데이트
			        	dtoBp.setBpId(existing.getBpId());
			            bloodPressure_dao.updateData(dtoBp);
			            System.out.println("업데이트: " + dtoBp);
			            updated = true;
			            break; // 업데이트 후 반복 종료
			        }
			    }
			}
			
				// 업데이트가 안된 경우 새 데이터 삽입
				if (!updated) {
				    bloodPressure_dao.insertData(dtoBp);
				    System.out.println("저장: " + dtoBp);
				}
		        
		    req.setAttribute("dtoBp", dtoBp);
		    return "redirect:/main";
	    
	    }	

	private String calculateBloodPressureLevel(int systolic, int diastolic) {

	    if (systolic >= 160 || diastolic >= 100) {
	        return "고혈압 위기";
	    } else if (systolic >= 140 || diastolic >= 90) {
	        return "고혈압";
	    } else if (systolic >= 130 || diastolic >= 80) {
	        return "고혈압 전단계";
	    } else if (systolic >= 120) {
	        return "주의";
	    } else {
	        return "정상";
	    }
	}

}
