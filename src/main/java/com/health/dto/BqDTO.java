package com.health.dto;

import java.sql.Date;
import java.sql.Timestamp;

import lombok.Data;

@Data
public class BqDTO {
	
	private int bq_id;
	private int member_no;
	private int fasting;
	private int afterMeal;
	private int beforeBed;
	private Date log_Date;
	private Timestamp created_At;
	
	
	
}
