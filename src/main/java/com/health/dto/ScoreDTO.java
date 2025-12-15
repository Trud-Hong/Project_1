package com.health.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ScoreDTO {
	
	private int bp_id;
	private int record_id;
	private int member_no;
	private String bpLevel;
    private double sleepHours;
    private Timestamp bedtime;
    private int duration;
	private String breakfast_time;
	private String lunch_time;
	private String dinner_time;
	
	
}
