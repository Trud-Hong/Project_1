package com.health.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.health.dto.WeightDTO;

@Repository
public class MemberRecordDAO {

    @Autowired
    private SqlSessionTemplate sessionTemplate;

    private final String namespace = "com.health.mapper.MemberRecordMapper";

    // 체중/BMI 기록 조회
    public List<WeightDTO> getWeightLogs(int memberNo) {
        return sessionTemplate.selectList(namespace + ".getWeightLogs", memberNo);
    }
}
