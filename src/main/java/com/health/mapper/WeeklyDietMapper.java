package com.health.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WeeklyDietMapper {

    // 회원 목표(goal) 기준 주간 식단 조회
    List<Map<String, Object>> getWeeklyDietByGoal(@Param("goal") String goal);

}
