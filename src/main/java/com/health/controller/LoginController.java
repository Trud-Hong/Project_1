package com.health.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.health.config.GoogleOAuthConfig;
import com.health.dao.AdminDAO;
import com.health.dao.BqDAO;
import com.health.dao.MemberDAO;
import com.health.dto.AdminDTO;
import com.health.dto.CustomInfo;
import com.health.dto.MemberDTO;
import com.health.service.UserService;
import com.health.util.MyUtil;
import com.health.util.PasswordUtil;

@Controller
public class LoginController {
	
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
    UserService userService;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "/health/login";
	}
	
	@RequestMapping(value = "/login_ok.do", method = {RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> login_ok(HttpServletRequest req) {
		
		 String member_id = req.getParameter("member_id");
		    String password = req.getParameter("password");

		    MemberDTO dto = member_dao.getReadData(member_id);

		    Map<String, Object> result = new HashMap<>();

		    if (dto == null || (!PasswordUtil.match(password, dto.getPassword()))) {
		        // 로그인 실패
		        result.put("success", false);
		        result.put("message", "아이디 또는 패스워드를 정확히 입력하세요.");
		        return result;
		    }

		    // 로그인 성공
		    HttpSession session = req.getSession();

		    CustomInfo info = new CustomInfo();

		    String gender = dto.getGender().equalsIgnoreCase("M") ? "남자" : "여자";

		    info.setMember_no(dto.getMember_no());
		    info.setMember_id(dto.getMember_id());
		    info.setName(dto.getName());
		    info.setPassword(dto.getPassword());
		    info.setHeight(dto.getHeight());
		    info.setWeight(dto.getWeight());
		    info.setBmi(dto.getBmi());
		    info.setBirth(dto.getBirth());
		    info.setGoal(dto.getGoal());
		    info.setGender(gender);
		    info.setLoginType("NORMAL");

		    session.setAttribute("customInfo", info);

		    result.put("success", true);
		    result.put("redirectUrl", "/heal/main.do"); // 로그인 성공 시 이동할 URL

		    return result;
	}
	
	@RequestMapping(value = "/loginAdmin", method = RequestMethod.GET)
	public String loginAdmin() {
		
		return "/health/loginAdmin";
	}
	
	@RequestMapping(value = "/loginAdmin_ok.do", method = {RequestMethod.POST})
	public String loginAdmin_ok(HttpServletRequest req) {
		
		String admin_id = req.getParameter("admin_id");
		String password = req.getParameter("password");
		
		AdminDTO dto = admin_dao.getReadData(admin_id);
		
		if(dto==null || (!dto.getPassword().equals(password))) {
			req.setAttribute("message", "아이디 또는 패스워드를 정확히 입력하세요.");
			return "redirect:/loginAdmin.do";
		}
		
		HttpSession session = req.getSession();
		
		CustomInfo info = new CustomInfo();
		
		info.setAdmin_id(dto.getAdmin_id());
		info.setName(dto.getName());
		info.setPassword(dto.getPassword());
		
		session.setAttribute("customInfo", info);
		
		return "redirect:/main";
	}
	
	@RequestMapping(value = "/logout.do", method = {RequestMethod.GET})
	public String logout(HttpServletRequest req) {
		
		HttpSession session = req.getSession();
		session.removeAttribute("customInfo");
		session.invalidate();
		
		return "redirect:/main";
	}
	
	@RequestMapping(value = "/googleLogin", method = {RequestMethod.GET,RequestMethod.POST})
    public String googleLogin() {
        String authUrl = GoogleOAuthConfig.AUTH_URL
                + "?response_type=code"
                + "&client_id=" + GoogleOAuthConfig.CLIENT_ID
                + "&redirect_uri=" + GoogleOAuthConfig.REDIRECT_URI
                + "&scope=" + GoogleOAuthConfig.SCOPE.replace(" ", "%20");
        return "redirect:" + authUrl;
    }
	

	// 회원가입 완료 페이지 매핑
    @RequestMapping(value = "/googleJoin", method = RequestMethod.GET)
    public String memberJoinComplete() {
        
        return "/health/googleJoin";
    }
	
    // 회원가입 비밀번호 설정 페이지 매핑
    @RequestMapping(value = "/googlePw", method = {RequestMethod.GET, RequestMethod.POST})
    public String googlePw() {
        
    	
    	
        return "/health/googlePw";
    }

}
