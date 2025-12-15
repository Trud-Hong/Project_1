package com.health.dao;

import org.mybatis.spring.SqlSessionTemplate;

import com.health.dto.ScoreDTO;

public class ScoreDAO { 
	

	private SqlSessionTemplate sessionTemplate;
	
	public void setSessionTemplate(SqlSessionTemplate sessionTemplate) {
		this.sessionTemplate = sessionTemplate;
	}


	public ScoreDTO getBPData(int member_no) {

		ScoreDTO dto = sessionTemplate.selectOne("com.health.score.getBPData",member_no);
		
		return dto;
	}
	
	public ScoreDTO getSleepHoursData(int member_no) {
		
		ScoreDTO dto = sessionTemplate.selectOne("com.health.score.getSleepHoursData",member_no);
		
		return dto;
	}
	
	public ScoreDTO getBedtimeData(int member_no) {
		
		ScoreDTO dto = sessionTemplate.selectOne("com.health.score.getBedtimeData",member_no);
		
		return dto;
	}
	
	public ScoreDTO getExerciseData(int member_no) {
		
		ScoreDTO dto = sessionTemplate.selectOne("com.health.score.getExerciseData",member_no);
		
		return dto;
	}
	
	public ScoreDTO gethabitData(int member_no) {
		
		ScoreDTO dto = sessionTemplate.selectOne("com.health.score.gethabitData",member_no);
		
		return dto;
	}
	
	
	public int conditionTotalScore(int member_no) {
		
		int scoreBP = 0, scoreSleep = 0, scoreBedtime = 0, scoreExercise = 0, scoreHabit = 0;
		
		ScoreDTO bp = getBPData(member_no);
		if (bp != null && bp.getBpLevel() != null) {
			switch (bp.getBpLevel()) {
			case "정상":
				scoreBP = 20;
				break;
			case "주의":
				scoreBP = 16;
				break;
			case "고혈압 전단계":
				scoreBP = 12;
				break;
			case "고혈압":
				scoreBP = 8;
				break;
			case "고혈압 위기":
				scoreBP = 4;
				break;
			}
		}
		
		
		ScoreDTO sleep = getSleepHoursData(member_no);
		if (sleep != null) {
			double hours = sleep.getSleepHours();
			if (hours >= 9 || hours <= 6) {
				scoreSleep = 10;
			}else {
				scoreSleep = 20;
			}
		}
		
		ScoreDTO bedtime = getBedtimeData(member_no);
		if (bedtime != null && bedtime.getBedtime() != null) {
			int hour = bedtime.getBedtime().toLocalDateTime().getHour();
			if (hour > 20 && hour <=23) {
				scoreBedtime = 20;
			}else if (hour >= 0 && hour < 6) {
				scoreBedtime = 10;
			}
		}
		
		ScoreDTO exer = getExerciseData(member_no);
		if (exer != null) {
			int exercise = exer.getDuration();
			if (exercise >= 120) {
				scoreExercise = 20;
			}else if (exercise >= 90) {
				scoreExercise = 15;
			}else if (exercise >= 60) {
				scoreExercise = 10;
			}else if (exercise >= 30) {
				scoreExercise = 5;
			}
		}
		
		ScoreDTO hb = gethabitData(member_no);
		int count = 0;
		if (hb != null) {
			if (hb.getBreakfast_time() != null) {
				count++;
			}
			if (hb.getLunch_time() != null) {
				count++;
			}
			if (hb.getDinner_time() != null) {
				count++;
			}
		}
		
		switch (count) {
		case 3:
			scoreHabit = 20;
			break;
		case 2:
			scoreHabit = 14;
			break;
		case 1:
			scoreHabit = 7;
			break;
		}
		
		
		System.out.println("scoreBp:" + scoreBP);
		System.out.println("scoreSleep:" + scoreSleep);
		System.out.println("scoreBedtime:" + scoreBedtime);
		System.out.println("scoreExercise:" + scoreExercise);
		System.out.println("scoreHabit:" + scoreHabit);
		int totalScore = scoreBP + scoreSleep + scoreBedtime + scoreExercise + scoreHabit;
		
		return totalScore;
	}
	



}
