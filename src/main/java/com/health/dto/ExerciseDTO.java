package com.health.dto;

import java.sql.Timestamp;

public class ExerciseDTO {
    
    // 운동 기록 테이블 필드들
    private int exercise_record_id;     // 운동 기록 ID (Primary Key)
    private int member_no;              // 회원 번호
    private int exercise_type_id;       // 운동 종류 ID (1-30)
    private String category;            // 운동 카테고리 (cardio, strength, daily)
    private String exercise_date;       // 운동 날짜 (YYYY-MM-DD)
    private int duration_minutes;       // 총 운동시간 (분)
    private int intensity;              // 운동 강도 (1-5)
    private int calories;               // 소모 칼로리
    private String memo;                // 메모
    private Timestamp reg_date;         // 등록일시
    
    // 운동 종류 정보 (조인용)
    private int ex_id;                  // 운동 종류 테이블 ID
    private String name;                // 운동 이름
    private int kcal_burn;              // 기본 칼로리 소모량
    
    // JSP에서 받을 시간 데이터
    private int hours;                  // 시간
    private int minutes;                // 분
    
    // 기본 생성자
    public ExerciseDTO() {}
    
    // Getters and Setters
    public int getExercise_record_id() {
        return exercise_record_id;
    }
    public void setExercise_record_id(int exercise_record_id) {
        this.exercise_record_id = exercise_record_id;
    }
    
    public int getMember_no() {
        return member_no;
    }
    public void setMember_no(int member_no) {
        this.member_no = member_no;
    }
    
    public int getExercise_type_id() {
        return exercise_type_id;
    }
    public void setExercise_type_id(int exercise_type_id) {
        this.exercise_type_id = exercise_type_id;
    }
    
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getExercise_date() {
        return exercise_date;
    }
    public void setExercise_date(String exercise_date) {
        this.exercise_date = exercise_date;
    }
    
    public int getDuration_minutes() {
        return duration_minutes;
    }
    public void setDuration_minutes(int duration_minutes) {
        this.duration_minutes = duration_minutes;
    }
    
    public int getIntensity() {
        return intensity;
    }
    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }
    
    public int getCalories() {
        return calories;
    }
    public void setCalories(int calories) {
        this.calories = calories;
    }
    
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
    
    public Timestamp getReg_date() {
        return reg_date;
    }
    public void setReg_date(Timestamp reg_date) {
        this.reg_date = reg_date;
    }
    
    // 기존 운동 종류 관련 필드들
    public int getEx_id() {
        return ex_id;
    }
    public void setEx_id(int ex_id) {
        this.ex_id = ex_id;
    }
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public int getKcal_burn() {
        return kcal_burn;
    }
    public void setKcal_burn(int kcal_burn) {
        this.kcal_burn = kcal_burn;
    }
    
    // 시간 관련 필드들
    public int getHours() {
        return hours;
    }
    public void setHours(int hours) {
        this.hours = hours;
        updateDurationMinutes();
    }
    
    public int getMinutes() {
        return minutes;
    }
    public void setMinutes(int minutes) {
        this.minutes = minutes;
        updateDurationMinutes();
    }
    
    // 시간과 분을 총 분으로 변환하는 헬퍼 메서드
    private void updateDurationMinutes() {
        this.duration_minutes = (this.hours * 60) + this.minutes;
    }
    
    // 총 분을 시간과 분으로 분리하는 헬퍼 메서드
    public void setDurationFromMinutes() {
        if (this.duration_minutes > 0) {
            this.hours = this.duration_minutes / 60;
            this.minutes = this.duration_minutes % 60;
        }
    }
    
    @Override
    public String toString() {
        return "ExerciseDTO{" +
                "exercise_record_id=" + exercise_record_id +
                ", member_no=" + member_no +
                ", exercise_type_id=" + exercise_type_id +
                ", category='" + category + '\'' +
                ", exercise_date='" + exercise_date + '\'' +
                ", duration_minutes=" + duration_minutes +
                ", intensity=" + intensity +
                ", calories=" + calories +
                ", memo='" + memo + '\'' +
                ", reg_date=" + reg_date +
                ", name='" + name + '\'' +
                '}';
    }
}