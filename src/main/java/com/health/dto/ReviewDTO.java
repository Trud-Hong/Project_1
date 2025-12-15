package com.health.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class ReviewDTO {
	
	private int review_id;
	private int member_no;
	private String member_name;
	private String title;
	private String content;
	private Date reg_date;
	private int hit_count;

}
