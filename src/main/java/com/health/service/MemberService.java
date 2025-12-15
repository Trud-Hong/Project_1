package com.health.service;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.dao.MemberDAO;
import com.health.dto.MemberDTO;

@Service
public class MemberService {
    
    @Autowired
    private MemberDAO memberDAO;

    @Autowired
    private EmailService emailService; // MimeMessage 기반으로 변경

    // 6자리 랜덤 코드 생성
    private String generateRandomCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

//    public boolean sendAuthCode(String email) {
//        if(memberDAO.isEmailExist(email)) {
//            String code = generateRandomCode();
//            Date expire = new Date(System.currentTimeMillis() + 5*60*1000); // 5분
//            memberDAO.saveAuthCode(email, code, expire); // 기존 코드 무효화 + 로그 저장
//
//            // 한글 발신자 이름 적용
//            emailService.sendEmail(email, "MyCondition 인증번호", "인증번호: <b>" + code + "</b>");
//
//            return true;
//        }
//        return false;
//    }

    public String verifyCodeAndGetMemberId(String email, String code) {
        String savedCode = memberDAO.getAuthCode(email);
        if(savedCode != null && savedCode.equals(code)) {
            return memberDAO.getMemberIdByEmail(email);
        }
        return null;
    }
    
        
    // 아이디 존재 확인
    public boolean checkId(String id) {
        return memberDAO.isIdExist(id);
    }
    
    // 회원 비밀번호 변경
    public boolean changePassword(String memberId, String newPassword) {
    	System.out.println("changePassword 호출: memberId=" + memberId + ", newPassword=" + newPassword);
        try {
            MemberDTO member = memberDAO.getReadData(memberId);
            if(member == null) return false;

            memberDAO.updatePassword(memberId, newPassword); // DAO에서 구현
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean isEmailExist(String email) {
    	return memberDAO.isEmailExist(email);
    }
    
    public String sendAuthCode(String id, String email) {
    	if((id==null||id.equals("")) || memberDAO.isIdEmailEqual(id,email)) {
            String code = generateRandomCode();
            Date expire = new Date(System.currentTimeMillis() + 60*1000); // 5분
            try {
            	memberDAO.saveAuthCode(email, code, expire); // 기존 코드 무효화 + 로그 저장

                // 한글 발신자 이름 적용
                emailService.sendEmail(email, "MyCondition 인증번호", "인증번호: <b>" + code + "</b>");
                return "success";
            } catch (Exception e) {
				return "sendFail";
			}
            
        }
        return "notEqual";
    }
    
    
}
