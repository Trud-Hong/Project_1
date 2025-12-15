package com.health.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.health.dao.ScoreDAO;
import com.health.dto.ConditionScoreDTO;
import com.health.service.HealthDataService;

@Controller
@RequestMapping("/api")
public class HealthDataController {

	@Autowired
	@Qualifier("scoreDAO")
	ScoreDAO score_dao;
	
	@Autowired
    private HealthDataService service;


    @RequestMapping(value = "/daydata", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Map<String, Object> getDayData(
        @RequestParam int year,
        @RequestParam int month,
        @RequestParam int day,
        @RequestParam String memberId,
        ConditionScoreDTO dto
    ) {
        Map<String, Object> data = service.getDayData(year, month, day, memberId);
        
        if (data == null) {
            Map<String, Object> emptyData = new HashMap<String, Object>();
            
            emptyData.put("bp", "");
            emptyData.put("bp_level", "");
            emptyData.put("sleep", "");
            emptyData.put("bedtime", "");
            emptyData.put("exercise", "");
            emptyData.put("GOAL", "");
            emptyData.put("condition", "");
            
            // diet용 데이터 추가
            emptyData.put("CATEGORY", "-");
            emptyData.put("MORNING", "-");
            emptyData.put("LUNCH", "-");
            emptyData.put("DINNER", "-");
            emptyData.put("TOTALKCAL", "-");
            emptyData.put("totalScore", 0); // totalScore 추가
            return emptyData;
        }
        
        int totalScore = score_dao.conditionTotalScore(dto.getMember_no());
        
//     // totalScore 계산 (예시: 단순 합산)
//        int totalScore = 0;
//        
//        // 혈압 레벨에 따른 점수
//        String bpLevel = (String) data.get("bp_level");
//        if ("정상".equals(bpLevel)) totalScore += 10;
//        else if ("주의".equals(bpLevel)) totalScore += 5;
//        else if ("고혈압 전단계".equals(bpLevel)) totalScore += 3;
//        
//        // 수면 시간 점수 예시
//        String sleepStr = (String) data.get("sleep");
//        if (sleepStr != null && !sleepStr.isEmpty()) {
//            try {
//                double sleepHours = Double.parseDouble(sleepStr);
//                if (sleepHours >= 7 && sleepHours <= 9) totalScore += 10;
//                else totalScore += 5;
//            } catch(NumberFormatException e) {
//                // 파싱 실패 시 점수 0
//            }
//        }
//        
//        // 운동 여부
//        String exercise = (String) data.get("exercise");
//        if ("Y".equalsIgnoreCase(exercise)) totalScore += 10;
//
//        // 식단 점수 (예시: TOTALKCAL 기준)
//        String totalKcal = data.get("TOTALKCAL").toString();
//        if (totalKcal != null && !"-".equals(totalKcal)) {
//            try {
//                int kcal = Integer.parseInt(totalKcal);
//                if (kcal >= 1500 && kcal <= 2500) totalScore += 10;
//            } catch(NumberFormatException e) {}
//        }
//
        	System.out.println("Calculated Total Score: " + totalScore);
        	System.out.println("Returned Data: " + data);

			// 계산된 totalScore를 data에 추가
        	data.put("totalScore", totalScore);
        return data;
    }

    // 주간 식단 조회 (특정 날짜 기준 일주일)
    @RequestMapping(value = "/weeklyDiet", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Map<String, Object>> getWeeklyDiet(@RequestParam String memberId) {
        int memberNo;
        try {
            memberNo = Integer.parseInt(memberId); // 문자열 → 숫자
        } catch(NumberFormatException e) {
            throw new RuntimeException("memberId가 숫자가 아님: " + memberId);
        }
        
        return service.getWeeklyDietByMember(memberNo);
    }
    
    
    
}
