package com.health.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberRecordMapper {
	String getGoalByMember(@Param("memberNo") int memberNo);
}
