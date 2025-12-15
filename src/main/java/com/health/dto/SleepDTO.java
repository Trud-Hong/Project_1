package com.health.dto;

import java.sql.Date;

public class SleepDTO {

    private int sleepId;
    private int memberNo;
    private double sleepHours;     // 수면시간 (시간 단위)
    private Date bedtime;          // 취침시간 (DATE 타입)
    private String bedtimeString;  // JSP에서 받을 문자열 (datetime-local 값)

    // getter/setter
    public int getSleepId() {
        return sleepId;
    }
    public void setSleepId(int sleepId) {
        this.sleepId = sleepId;
    }

    public int getMemberNo() {
        return memberNo;
    }
    public void setMemberNo(int memberNo) {
        this.memberNo = memberNo;
    }

    public double getSleepHours() {
        return sleepHours;
    }
    public void setSleepHours(double sleepHours) {
        System.out.println("SleepDTO - setSleepHours 호출됨: " + sleepHours);
        this.sleepHours = sleepHours;
    }

    public Date getBedtime() {
        return bedtime;
    }
    public void setBedtime(Date bedtime) {
        this.bedtime = bedtime;
    }
    
    // datetime-local 값을 받기 위한 String 필드
    public String getBedtimeString() {
        return bedtimeString;
    }
    public void setBedtimeString(String bedtimeString) {
        System.out.println("SleepDTO - setBedtimeString 호출됨: " + bedtimeString);
        this.bedtimeString = bedtimeString;
        
        // String을 Date로 변환
        if (bedtimeString != null && !bedtimeString.isEmpty()) {
            try {
                // "2025-09-22T23:42" -> "2025-09-22 23:42:00"
                String dateTimeStr = bedtimeString.replace("T", " ") + ":00";
                this.bedtime = java.sql.Date.valueOf(dateTimeStr.substring(0, 10)); // 날짜 부분만
                System.out.println("SleepDTO - 날짜 변환 성공: " + this.bedtime);
                
                // 또는 Timestamp로 변환하려면:
                // this.bedtime = java.sql.Timestamp.valueOf(dateTimeStr);
            } catch (Exception e) {
                System.out.println("SleepDTO - 날짜 변환 오류: " + e.getMessage());
                this.bedtime = new java.sql.Date(System.currentTimeMillis());
            }
        }
    }
    
    @Override
    public String toString() {
        return "SleepDTO{" +
                "sleepId=" + sleepId +
                ", memberNo=" + memberNo +
                ", sleepHours=" + sleepHours +
                ", bedtime=" + bedtime +
                ", bedtimeString='" + bedtimeString + '\'' +
                '}';
    }
}