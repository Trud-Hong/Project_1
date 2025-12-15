package com.health.dto;

import lombok.Data;

@Data
public class HabitDTO {
	
	private int record_id;
	private int member_no;
	private String record_date;
	private String breakfast_time;
	private String lunch_time;
	private String dinner_time;
	
	
}
