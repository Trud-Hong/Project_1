package com.health.mapper;

import com.health.dto.CustomInfo;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO {

    // 비밀번호 평문으로 업데이트
    @Update("UPDATE member SET password = #{password} WHERE member_id = #{member_id}")
    void updatePassword(String memberId, String password);

    // 사용자 정보 조회 (예시)
    @Select("SELECT * FROM member WHERE member_id = #{member_id}")
    CustomInfo findByMemberId(String memberId);
}
