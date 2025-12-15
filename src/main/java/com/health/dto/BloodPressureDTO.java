package com.health.dto;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Data;
import lombok.Getter;

@Data
public class BloodPressureDTO {

    private int bpId;
    private int memberNo;
    private String bpLevel;
    private int bpHigh;
    private int bpLow;
    private Date log_Date;
	private Timestamp created_At;

    
}
