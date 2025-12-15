package com.health.service;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.health.mapper.HealthDataMapper;
import com.health.mapper.MemberRecordMapper;
import com.health.mapper.WeeklyDietMapper;


@Service
public class HealthDataService {


//    @Autowired
//    private HealthDataMapper mapper; 
//
////    @Autowired
////    private WeeklyDietMapper weeklyDietMapper;  // 주간식단 Mapper 추가
//
//    // 하루 데이터 조회
//    public Map<String, Object> getDayData(int year, int month, int day, String memberId) {
//        return mapper.getDayData(year, month, day, memberId);
//    }
////
//// // 주간 식단 조회 (특정 날짜 기준 주간)
////    public List<Map<String, Object>> getWeeklyDiet(int year, int month, int day, String memberId) {
////        return weeklyDietMapper.getWeeklyDiet(year, month, day, memberId);
////    }
//	
//	@Autowired
//	private WeeklyDietMapper weeklyDietMapper;
//
//	@Autowired
//	private MemberRecordMapper memberRecordMapper; // member_record에서 goal 조회용
//
//	// 회원별 goal에 따른 주간 식단 조회
//	public List<Map<String, Object>> getWeeklyDietByMember(int memberNo) {
//	    // member_record에서 goal 조회
//	    String goal = memberRecordMapper.getGoalByMember(memberNo);
//	    return weeklyDietMapper.getWeeklyDietByGoal(goal);
//	}
//
//}
	
	@Autowired
    private HealthDataMapper mapper; 

    @Autowired
    private WeeklyDietMapper weeklyDietMapper;

    @Autowired
    private MemberRecordMapper memberRecordMapper; // member_record에서 goal 조회용

    
    
    // 하루 데이터 조회
    public Map<String, Object> getDayData(int year, int month, int day, String memberId) {
    	
    	return mapper.getDayData(year, month, day, memberId);
    }

    // 회원별 goal에 따른 주간 식단 조회
    public List<Map<String, Object>> getWeeklyDietByMember(int memberNo) {
        // 1. member_record에서 goal 조회
        String goal = memberRecordMapper.getGoalByMember(memberNo);
        System.out.println("[DEBUG] memberNo=" + memberNo + ", goal=" + goal);

        if (goal == null || goal.isEmpty()) {
            return Collections.emptyList(); // 안전하게 빈 리스트 반환
        }

        // 2. goal 기준으로 weekly_diet_recommendation 조회
        List<Map<String, Object>> weeklyDietList = weeklyDietMapper.getWeeklyDietByGoal(goal);
        System.out.println("[DEBUG] 조회된 주간 식단 수=" + weeklyDietList.size());

        if (weeklyDietList.isEmpty()) {
            System.out.println("[WARN] goal 값 '" + goal + "'에 맞는 주간 식단 데이터 없음");
        }

        return weeklyDietList;
    }
    
}
	