package com.health.dto;


import lombok.Data;

@Data
public class BoardDTO {
	
	private int board_id;
	private String category;

	private String member_name;

	private String title;
	private String content;
	private int member_no;
	private String reg_date;

}
