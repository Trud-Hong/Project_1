package com.health.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class WeightDTO {
	
	private int member_no;
	private int weight_id;
	private double weight;
	private double bmi;
	private String record_date;

}
