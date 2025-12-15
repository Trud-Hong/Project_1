package com.health.controller;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.health.dao.AdminDAO;
import com.health.dao.MemberDAO;
import com.health.dto.AdminDTO;
import com.health.dto.CustomInfo;
import com.health.dto.MemberDTO;
import com.health.service.UserService;

@Controller
public class JoinController {
	
	@Autowired
	@Qualifier("MemberDAO")
	MemberDAO member_dao;
	
	@Autowired
	@Qualifier("adminDAO")
	AdminDAO admin_dao;
	
	@Autowired
    UserService userService;
	
	// birth 필드 바인딩용 @InitBinder
		// ---------------------------
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    sdf.setLenient(false);
	    binder.registerCustomEditor(java.sql.Date.class, new PropertyEditorSupport() {
	        @Override
	        public void setAsText(String text) throws IllegalArgumentException {
	            if(text == null || text.trim().isEmpty()) {
	                setValue(null);
	            } else {
	                setValue(java.sql.Date.valueOf(text));
	            }
	        }
	    });
	}
	
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join() {
		return "/health/join";
	}

	@RequestMapping(value = "/joinAdmin", method = RequestMethod.GET)
	public String joinAdmin() {
		
		
		return "/health/joinAdmin";
	}
	
	@RequestMapping(value = "/join_ok.do", method = RequestMethod.POST)
	public String join_ok(MemberDTO dto, Model model) {
	    if(dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
	        model.addAttribute("message", "이메일을 입력하세요.");
	        return "/health/join";
	    }

	    if(member_dao.isEmailExist(dto.getEmail())) {
	        model.addAttribute("message", "이미 등록된 이메일입니다.");
	        return "/health/join";
	    }

	    if(member_dao.isIdExist(dto.getMember_id())) {
	        model.addAttribute("message", "이미 존재하는 아이디입니다.");
	        return "/health/join";
	    }
	    
	    member_dao.insertData(dto);

	    // 성공 메시지를 모델에 담고 JSP에서 alert + 로그인 이동
	    model.addAttribute("successMsg", "회원가입 성공!! 로그인 화면으로 이동합니다.");
	    return "/health/joinSuccess";  
	}
	
	// 아이디 중복 체크
	@RequestMapping(value = "/checkId", method = RequestMethod.GET)
	@ResponseBody
	public String checkId(@RequestParam String member_id) {
	    boolean exists = member_dao.isIdExist(member_id);
	    return exists ? "exist" : "ok";
	}

	@RequestMapping(value = "/joinAdmin_ok.do", method = {RequestMethod.POST})
	public String joinAdmin_ok(AdminDTO dto,HttpServletRequest req) {
		
		admin_dao.insertData(dto);
		
		return "redirect:/loginAdmin.do";
		
	}
	
	@RequestMapping(value = "/googleJoin_ok.do", method = {RequestMethod.POST, RequestMethod.GET})
	public String googleJoin_ok(HttpServletRequest request, CustomInfo customInfo) {
	    System.out.println("googleJoin_ok");
	    
	    return "redirect:/googleJoin";
	}
	@RequestMapping(value = "/personalInformation", method = {RequestMethod.GET})
	public String personalInformation() {
		return "/health/personalInformation";
	}
	
}
