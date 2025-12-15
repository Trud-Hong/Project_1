package com.health.dao;

import org.mybatis.spring.SqlSessionTemplate;
import com.health.dto.SleepRecordDTO;

public class SleepRecordDAO {
    
    private SqlSessionTemplate sessionTemplate;
    
    public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
        this.sessionTemplate = sessionTemplate;
    }

    // 수면 데이터 추가 - namespace를 sleeprecord로 변경
    public void insertData(SleepRecordDTO dto) {
        sessionTemplate.insert("com.health.sleeprecord.insertData", dto);
    }
    
    // 최대 번호 조회
    public int maxNum() {
        Integer result = sessionTemplate.selectOne("com.health.sleeprecord.maxNum");
        return result != null ? result : 0;
    }
    
    // 특정 회원의 수면 데이터 조회 (최신 1건)
    public SleepRecordDTO getReadData(int member_no) {
        return sessionTemplate.selectOne("com.health.sleeprecord.getReadData", member_no);
    }
    
    // 기존 메서드 (호환성 유지)
    public SleepRecordDTO getSleep(int member_no) {
        return getReadData(member_no);
    }
    
    // 수면 데이터 수정
    public void updateData(SleepRecordDTO dto) {
        sessionTemplate.update("com.health.sleeprecord.updateData", dto);
    }
    
    // 수면 데이터 삭제
    public void deleteData(int member_no) {
        sessionTemplate.delete("com.health.sleeprecord.deleteData", member_no);
    }
    
    // 수면시간 권장사항 조회
    public String getRecommendation(int sleep_hours) {
        if (sleep_hours < 6) {
            return "수면시간이 너무 짧습니다. 7-8시간 수면을 권장합니다.";
        } else if (sleep_hours > 9) {
            return "수면시간이 너무 깁니다. 7-8시간 수면을 권장합니다.";
        } else {
            return "적절한 수면시간입니다! 잘 하고 계세요.";
        }
    }
}