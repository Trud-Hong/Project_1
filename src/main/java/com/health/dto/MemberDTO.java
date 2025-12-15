package com.health.dto;

import java.sql.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class MemberDTO {
	
	private int member_no;
	private String member_id;
	private String password;
	private String name;
	private Date birth;
	private String gender;
	private double height;
	private double weight;
	private double bmi;
	private String goal;
	private String join_date;
	private String email;
	//09.28수정
	//private String status;
	

}
