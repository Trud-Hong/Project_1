package com.health.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.health.dto.CustomInfo;
import com.health.service.MemberService;

@Controller
public class FindPwdController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(value="/findPwd", method=RequestMethod.GET)
    public String findPwdForm() {
    	
    	
        return "/health/findPwd"; // findId.jsp 경로
    }

    
}