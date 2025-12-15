package com.health.controller;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.health.dto.MemberDTO;
import com.health.util.MyUtil;
import com.health.util.PasswordUtil;

@Controller
public class myPageController {
	//Test
	@Autowired
	@Qualifier("MemberDAO")
	MemberDAO member_dao;
	
	@Autowired
	MyUtil myUtil;
	
	@Autowired
	@Qualifier("bqDAO")
	BqDAO bq_dao;

	
	@RequestMapping(value = "/myPage", method = RequestMethod.GET)
	public String myPage(HttpServletRequest req) {
		
		HttpSession session = req.getSession();
		CustomInfo info = (CustomInfo) session.getAttribute("customInfo");
		
		if (info == null) {
			return "redirect:/login.do";
		}
		
		return "/health/myPage";
	}
	
	@RequestMapping(value = "/myPageEdit", method = RequestMethod.GET)
	public String myPageEdit(HttpServletRequest req) {
		
		HttpSession session = req.getSession();
		CustomInfo info = (CustomInfo) session.getAttribute("customInfo");
		
		if (info == null || (info.getPassword() == null || info.getPassword().equals(""))) {
			return "redirect:/myPageEditSocial";
		}
		
		if ("GOOGLE".equals(info.getLoginType())) {
			return "/health/myPageGoogleEdit"; // 구글 로그인 사용자
		} else {
			return "/health/myPageEdit"; // 일반 로그인 사용자
		}
	}
	
	@RequestMapping(value = "/myPageEditSocial", method = RequestMethod.GET)
	public String myPageEditSocial(HttpServletRequest req) {
		
		HttpSession session = req.getSession();
		CustomInfo info = (CustomInfo) session.getAttribute("customInfo");
		
		if (info == null || (info.getPassword() == null || info.getPassword().equals(""))) {
			return "/health/myPageEditSocial";
		}
		
		if ("GOOGLE".equals(info.getLoginType())) {
			return "/health/myPageGoogleEdit"; // 구글 로그인 사용자
		} else {
			return "/health/myPageEditSocial"; // 일반 로그인 사용자
		}
	}
	
	
	@RequestMapping(value = "/myPageEdit_ok", method = RequestMethod.POST)
	public String myPageEdit_ok(MemberDTO dto, HttpServletRequest req) {
	    
	    try {
	        
	        // 세션의 정보도 업데이트
	        HttpSession session = req.getSession();
	        CustomInfo info = (CustomInfo) session.getAttribute("customInfo");
	        System.out.println("세션 member_no: " + info.getMember_no());
	        
//			if (info == null) {
//				return "redirect:/login.do";
//			}
//			if(dto.getGoal() == null || dto.getGoal().equals("")) {
//				
//				member_dao.insertData(dto);
//			}else {
//				// 업데이트 실행
//		        member_dao.updateProfile(dto);
//			}
	        
		    
			
			System.out.println(dto);
	        if (info != null) {
	        	
	        	dto.setMember_no(info.getMember_no());
	            // BMI 재계산
	            double bmi = dto.getWeight() / (dto.getHeight() * dto.getHeight()) * 10000;
	            dto.setBmi(Math.round(bmi * 10) / 10.0);
	            
	            info.setName(dto.getName());
	            info.setHeight(dto.getHeight());
	            info.setWeight(dto.getWeight());
	            info.setBmi(dto.getBmi());
	            info.setBirth(dto.getBirth());
	            info.setGoal(dto.getGoal());
	            
	            // 업데이트 실행
		        member_dao.updateProfile(dto);
	            
	            session.setAttribute("customInfo", info);
	        }
	        
	        
	    } catch (Exception e) {
	    	e.printStackTrace();
	        req.setAttribute("message", "업데이트 중 오류가 발생했습니다.");
	        return "/health/myPageGoogleEdit";
	    }
	    
	    
	    // 리다이렉트로 변경 (POST-Redirect-GET 패턴)
	    return "redirect:/myPage.do";
		
		/*
		 * System.out.println("Controller 시작"); System.out.println("DAO 호출 전: " +
		 * dto.getMember_id()); member_dao.updateData(dto);
		 * 
		 * System.out.println("DAO 호출 후"); System.out.println("Controller 끝"); return
		 * "health/myPage";
		 */
		
	}
	
	@RequestMapping(value = "/myPageEditSocial_ok", method = RequestMethod.POST)
	public String myPageEditSocial_ok(MemberDTO dto, HttpServletRequest req) {
	    
	    try {
	        
	        // 세션의 정보도 업데이트
	        HttpSession session = req.getSession();
	        CustomInfo info = (CustomInfo) session.getAttribute("customInfo");
	        
			if (info == null) {
				return "redirect:/login.do";
			}
			
			 // 1. 기존 비밀번호 확인
		    MemberDTO dbMember = member_dao.getReadData(info.getMember_id());
		    if (!PasswordUtil.match(dto.getPassword(), dbMember.getPassword())) {
		    	
		        // 비밀번호 불일치
		        req.setAttribute("message", "비밀번호가 일치하지 않습니다.");
		        return "/health/myPageEdit"; // 다시 수정 페이지로
		    }
		    
		    // 업데이트 실행
	        member_dao.updateProfile(dto);
			
	        if (info != null) {
	            // BMI 재계산
	            double bmi = dto.getWeight() / (dto.getHeight() * dto.getHeight()) * 10000;
	            dto.setBmi(Math.round(bmi * 10) / 10.0);
	            
	            info.setName(dto.getName());
	            info.setPassword(dbMember.getPassword());
	            info.setHeight(dto.getHeight());
	            info.setWeight(dto.getWeight());
	            info.setBmi(dto.getBmi());
	            info.setBirth(dto.getBirth());
	            info.setGoal(dto.getGoal());
	            
	            session.setAttribute("customInfo", info);
	        }
	        
	        
	    } catch (Exception e) {
	    	e.printStackTrace();
	        req.setAttribute("message", "업데이트 중 오류가 발생했습니다.");
	        return "/health/myPageGoogleEdit";
	    }
	    
	    
	    // 리다이렉트로 변경 (POST-Redirect-GET 패턴)
	    return "redirect:/myPage.do";
		
		/*
		 * System.out.println("Controller 시작"); System.out.println("DAO 호출 전: " +
		 * dto.getMember_id()); member_dao.updateData(dto);
		 * 
		 * System.out.println("DAO 호출 후"); System.out.println("Controller 끝"); return
		 * "health/myPage";
		 */
		
	}

	@RequestMapping(value = "/delete_ok", method = {RequestMethod.POST})
	public String delete_ok(HttpServletRequest req, HttpServletResponse resp) {
		
		int member_no = Integer.parseInt(req.getParameter("member_no"));
		String member_id = req.getParameter("member_id");
		String password = req.getParameter("password");
		
		MemberDTO dto = member_dao.getReadData(member_id);
		
		if (password == "") {
			
			req.setAttribute("message", "비밀번호를 입력해주세요.");
			
			return "/health/myPage";
		
		}

		if(!PasswordUtil.match(password, dto.getPassword())) {
			
			req.setAttribute("message", "비밀번호를 정확히 입력해주세요");
			
			return "/health/myPage";
		}
		
		
		member_dao.deleteHabitData(member_no);
		member_dao.deleteExerciseData(member_no);
		member_dao.deleteBGData(member_no);
		member_dao.deleteBPData(member_no);
		member_dao.deleteSleepData(member_no);
		member_dao.deleteCoummunity(member_no);
		member_dao.deleteReview(member_no);
		member_dao.deleteData(member_no);
		
		
		HttpSession session = req.getSession();
		session.removeAttribute("customInfo");
		session.invalidate();
		
		return "redirect:/main.do";
		
	}
	

	@RequestMapping(value = "/goalUpdate", method = RequestMethod.POST)
	public String goalUpdate(MemberDTO dto, HttpServletRequest req) {
		
		try {
			// 업데이트 실행
			member_dao.updateGoal(dto);
			
			// 세션의 정보도 업데이트
			HttpSession session = req.getSession();
			CustomInfo info = (CustomInfo) session.getAttribute("customInfo");
			if (info != null) {
				
				info.setGoal(dto.getGoal());
				
				session.setAttribute("customInfo", info);
			}
			
			
		} catch (Exception e) {
			System.out.println("업데이트 중 오류 발생: " + e.getMessage());
			e.printStackTrace();
		}
		
		// 리다이렉트로 변경 (POST-Redirect-GET 패턴)
		return "redirect:/main.do";
				
	}

	@RequestMapping(value = "/profileUpdate.do", method = {RequestMethod.POST})
	public String profileUpdate(MemberDTO dto, HttpServletRequest req) {
		
		HttpSession session = req.getSession();
		CustomInfo info = (CustomInfo) session.getAttribute("customInfo");
		
		if (info == null) {
			return "redirect:/login.do";
		}
		
		try {
			
			dto.setHeight(info.getHeight());
			
			double bmi = dto.getWeight() / (dto.getHeight() * dto.getHeight()) * 10000;
			dto.setBmi(Math.round(bmi*10)/10.0);
			
			// 업데이트 실행
			member_dao.updateWeightMemberRecord(dto);
			System.out.println(dto);
			member_dao.updateWeight(dto);
			System.out.println("실행 성공");
			
			// 세션의 정보도 업데이트
			if (info != null) {
				
				info.setWeight(dto.getWeight());
				info.setBmi(dto.getBmi());
				
				session.setAttribute("customInfo", info);
			}
			System.out.println("세션 업데이트 성공" + info);
			
			
		} catch (Exception e) {
			System.out.println("업데이트 중 오류 발생: " + e.getMessage());
			e.printStackTrace();
		}
		
		// 리다이렉트로 변경 (POST-Redirect-GET 패턴)
		return "redirect:/profile.do";
		
	}
	
}
