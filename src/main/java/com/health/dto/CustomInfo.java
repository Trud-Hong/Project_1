package com.health.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class CustomInfo {
	
	private String admin_id;
	private int member_no;
	private String member_id;
	private String password;
	private String name;
	private Date birth;
	private double height;
	private double weight;
	private String goal;
	private double bmi;
	private String gender;
	private BqDTO bloodGlucose;
	private int record_id;
	private String habit_record_date;
	private String breakfast_time;
	private String lunch_time;
	private String dinner_time;
	private Date record_date;
	private String email;
	
	private String loginType;

}
