package com.health.dto;

import lombok.Data;

@Data
public class ConditionScoreDTO {
	
	private int cond_id;
	private int member_no;
	private String record_date;
	private int score;

}
