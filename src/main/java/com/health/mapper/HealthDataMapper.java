package com.health.mapper;

import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface HealthDataMapper {
	
	
    Map<String, Object> getDayData(
        @Param("year") int year, 
        @Param("month") int month, 
        @Param("day") int day,
        @Param("memberId") String memberId
    );

}


