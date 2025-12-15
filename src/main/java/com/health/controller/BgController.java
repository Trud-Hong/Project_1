package com.health.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.health.dao.BqDAO;
import com.health.dao.MemberDAO;
import com.health.dto.BqDTO;
import com.health.dto.CustomInfo;
import com.health.util.MyUtil;

@Controller
public class BgController {
	
	@Autowired
	@Qualifier("MemberDAO")
	MemberDAO member_dao;
	
	@Autowired
	MyUtil myUtil;
	
	@Autowired
	@Qualifier("bqDAO")
	BqDAO bq_dao;
	
	@RequestMapping(value = "/bg", method = RequestMethod.GET)
	public String bg(HttpServletRequest req) {
	    HttpSession session = req.getSession();
	    CustomInfo info = (CustomInfo) session.getAttribute("customInfo");
	    	
		if (info == null) {
			return "redirect:/login.do";
		}

	    if (info != null) {
	        int member_no = info.getMember_no();

	        List<BqDTO> lists = bq_dao.getReadData(member_no);

	        if (lists != null && !lists.isEmpty()) {
	            req.setAttribute("dto", lists.get(0));
	        } else {
	            req.setAttribute("dto", null);
	        }
	        
	        req.setAttribute("lists", lists);
	    }
	    
	    return "/health/bg";
	}

	


	@RequestMapping(value = "/bg_ok", method = {RequestMethod.POST,RequestMethod.GET})
	public String bg_ok(BqDTO dto,HttpServletRequest req) {
		
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
		
		dto.setLog_Date(log_date);
		
		 System.out.println("log_date set in BqDTO: " + dto.getLog_Date());
		
		dto.setMember_no(member_no);
		
	    // 기존 데이터 확인 후 update/insert
		List<BqDTO> lists = bq_dao.getReadData(member_no);

		boolean updated = false; // 업데이트 여부 체크

		if (lists != null && !lists.isEmpty()) {
		    for (BqDTO existing : lists) {
		        if (existing.getLog_Date().equals(dto.getLog_Date())) {
		            // 같은 날짜 있으면 업데이트
		            dto.setBq_id(existing.getBq_id());
		            bq_dao.updateData(dto);
		            System.out.println("업데이트: " + dto);
		            updated = true;
		            break; // 업데이트 후 반복 종료
		        }
		    }
		}

		// 업데이트가 안된 경우 새 데이터 삽입
		if (!updated) {
		    bq_dao.insertData(dto);
		    System.out.println("저장: " + dto);
		}

		req.setAttribute("dto", dto);
		return "redirect:/bg.do";

	}

}
