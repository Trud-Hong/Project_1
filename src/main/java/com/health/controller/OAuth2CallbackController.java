package com.health.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.health.dao.MemberDAO;
import com.health.dto.CustomInfo;
import com.health.mapper.UserMapper;
import com.health.service.UserService;
import com.health.util.OAuth2Utils;
import com.health.util.PasswordUtil;

@Controller
public class OAuth2CallbackController {
	
	@Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login/oauth2/code/google", method = RequestMethod.GET)
    public String googleCallback(@RequestParam(value="code", required=false) String code,
            @RequestParam(value="error", required=false) String error, HttpServletRequest request) {
    	
    	HttpSession session = request.getSession();
    	
    	 try {
	        // 1. 구글 OAuth로부터 사용자 정보 가져오기
	        String tokenResponse = OAuth2Utils.getAccessToken(code);
	        String accessToken = OAuth2Utils.parseAccessToken(tokenResponse);
	        String userInfoJson = OAuth2Utils.getUserInfo(accessToken);

	        ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode userInfo = objectMapper.readTree(userInfoJson);

	        String member_id = userInfo.get("sub").asText();  // 고유 ID
	        String name = userInfo.get("name").asText();
	        String email = userInfo.get("email").asText();
	        
		     // 1. 이메일로 기존 유저 있는지 확인
	        CustomInfo emailUser = userService.findByEmail(email);
	        if (emailUser != null) {
	            if (emailUser.getLoginType() == null || !"GOOGLE".equals(emailUser.getLoginType())) {
	                // ⚠ 일반 로그인 사용자
	                request.setAttribute("message", "이미 가입된 이메일입니다.");
	                return "redirect:/login?message=alreadyExists"; // 또는 알림용 페이지
	            }
	        }
	        
	        
	        // 2. DB에서 해당 member_id 존재 여부 확인
	        CustomInfo existingUser = userService.findByMemberId(member_id);

	        if (existingUser != null) {
	            // ✅ 기존 유저

	            session.setAttribute("customInfo", existingUser);

	            if (existingUser.getPassword() == null || existingUser.getPassword().isEmpty()) {
	                // 👉 소셜 로그인 유저 (비밀번호 없음)
	                return "redirect:/myPageEditSocial";
	            }

	            return "redirect:/main"; // 일반 로그인 유저
	        } else {
	        	
	        	
	            // ✅ 신규 유저 → 자동 회원가입 + 소셜 유저 처리
	            CustomInfo customInfo = new CustomInfo();
	            customInfo.setMember_id(member_id);
	            customInfo.setName(name);
	            customInfo.setEmail(email);
	            customInfo.setPassword(null); // 비밀번호 없이 등록 (소셜 유저임을 표시)
	            customInfo.setLoginType("GOOGLE");
	            customInfo.setGender("M");
	            customInfo.setBirth(java.sql.Date.valueOf("2000-01-01"));
	            customInfo.setHeight(0.0);
	            customInfo.setWeight(0.0);
	            customInfo.setBmi(0.0);
	            customInfo.setGoal("");

	            CustomInfo savedUser = userService.saveUser(customInfo);

	            session.setAttribute("customInfo", savedUser);

	            return "redirect:/myPageEditSocial"; // 비밀번호 설정 없이 곧바로 정보 수정 페이지로
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	        return "redirect:/error";
	    }
    }
    
    @RequestMapping("/error")
    public String errorPage(HttpServletRequest request) {
        request.setAttribute("message", "Google 인증 도중 오류가 발생했습니다.");
        return "/health/login";
    }
            
}

