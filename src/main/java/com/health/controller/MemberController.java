package com.health.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.health.service.MemberService;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    // 아이디 찾기 페이지
    @RequestMapping(value="/findId", method=RequestMethod.GET)
    public String findIdForm() {
        return "/health/findId"; // findId.jsp 경로
    }

    // 인증 코드 발송 (AJAX)
    @RequestMapping(value="/sendAuthCode", method=RequestMethod.POST)
    @ResponseBody
    public String sendAuthCode(@RequestParam String email, @RequestParam String id) {
        if(memberService.isEmailExist(email)) {
        	return memberService.sendAuthCode(id,email);
        }else {
        	return "notExist";
        }
        
    }

    // 인증 코드 확인 (AJAX)
    @RequestMapping(value="/verifyAuthCode", method=RequestMethod.POST)
    @ResponseBody
    public String verifyAuthCode(@RequestParam String email,
                                 @RequestParam String code,
                                 HttpSession session) {  
        String memberId = memberService.verifyCodeAndGetMemberId(email, code);
        if(memberId != null) {
            session.setAttribute("resetMemberId", memberId);
            return "success:" + memberId;
        } else {
            return "fail";
        }
    }
    
    @RequestMapping(value = "/idCheck", method = RequestMethod.POST)
    @ResponseBody
    public String idCheck(@RequestParam("id") String id) {
        boolean exist = memberService.checkId(id);
        return exist ? "success" : "fail";
    }
    
    //비밀번호 변경
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public String changePassword(@RequestParam("member_id") String member_id,
                                 @RequestParam String newPassword,
                                 HttpSession session) {  
    	System.out.println("changePassword 호출: memberId=" + member_id + ", newPassword=" + newPassword);
        boolean result = memberService.changePassword(member_id, newPassword);
        
        if(result) {
            session.removeAttribute("resetMemberId");
            return "success";
        } else {
            return "fail";
        }
    }
    
    @RequestMapping(value="/pwdChange.do", method=RequestMethod.GET)
    public String pwdChangeForm(HttpSession session, Model model) {
        String memberId = (String) session.getAttribute("resetMemberId");
        
        if(memberId == null) {
            // 세션 없으면 접근 금지
            return "redirect:/findPwd.do";
        }
        
        model.addAttribute("memberId", memberId);
        return "/health/pwdChange";
    }
    
    // 이메일 중복 확인
    @RequestMapping(value = "/checkEmail", method = RequestMethod.GET)
    @ResponseBody
    public String checkEmail(@RequestParam String email) {
        boolean exists = memberService.isEmailExist(email);
        return exists ? "exist" : "ok";
    }
    
}