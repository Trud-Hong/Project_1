package com.health.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.health.dto.CustomInfo;
import com.health.mapper.UserDAO;
import com.health.mapper.UserMapper;
import com.health.util.PasswordUtil;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private UserDAO userDAO;

//    @Transactional
//    public CustomInfo loginOrRegisterUser(CustomInfo customInfo) {
//        // 1. 구글 ID로 사용자 찾기
//        CustomInfo existingUser = userMapper.findByGoogleId(customInfo.getMember_id());
//
//        if (existingUser != null) {
//            // 기존 사용자일 경우 정보 반환
//            return existingUser;
//        } else {
//            // 새 사용자일 경우 DB에 저장
//            userMapper.saveUser(customInfo);
//            return customInfo;
//        }
//    }
    @Transactional
    public boolean changePassword(String memberId, String newPassword) {
        try {
            String encodedPwd = PasswordUtil.encode(newPassword);
            userDAO.updatePassword(memberId, encodedPwd);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // member_id로 기존 사용자 찾기
    public CustomInfo findByMemberId(String memberId) {
        return userMapper.findByGoogleId(memberId);  // 구글 ID로 조회
    }
    
    public CustomInfo findByEmail(String email) {
        return userMapper.findByEmail(email);  // 구글 ID로 조회
    }

    @Transactional
    public CustomInfo saveUser(CustomInfo customInfo) {
        // 1. 사용자 저장 (member_no는 DB 시퀀스 자동생성)
        userMapper.saveUser(customInfo);
        
        // 2. 저장된 사용자 DB에서 다시 조회 (member_id 기준)
        CustomInfo savedUser = userMapper.findByGoogleId(customInfo.getMember_id());
        
        // 3. 반환
        return savedUser;
    }
    
    
    
    // 비밀번호 평문으로 저장
    public void updatePassword(CustomInfo customInfo) {
    	userDAO.updatePassword(customInfo.getMember_id(), customInfo.getPassword());
    }
    
    
}

