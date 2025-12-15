package com.health.mapper;

import com.health.dto.CustomInfo;

public interface UserMapper {

    // 구글 고유 ID로 사용자 조회
    CustomInfo findByGoogleId(String googleId);

    // 새 사용자 저장
    void saveUser(CustomInfo customInfo);
    
    //이메일 조회
	CustomInfo findByEmail(String email);

    
}
