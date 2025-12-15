package com.health.dto;

import java.sql.Timestamp;

public class SleepRecordDTO {

    private int recordId;          // sleepId에서 recordId로 변경 (테이블 구조에 맞춤)
    private int memberNo;
    private double sleepHours;     // 수면시간 (시간 단위)
    private Timestamp bedtime;     // 취침시간 (TIMESTAMP 타입으로 변경)
    private String bedtimeString;  // JSP에서 받을 문자열 (datetime-local 값)

    // getter/setter
    public int getRecordId() {
        return recordId;
    }
    public void setRecordId(int recordId) {
        this.recordId = recordId;
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

    public Timestamp getBedtime() {
        return bedtime;
    }
    public void setBedtime(Timestamp bedtime) {
        this.bedtime = bedtime;
    }
    
    // datetime-local 값을 받기 위한 String 필드
    public String getBedtimeString() {
        return bedtimeString;
    }
    public void setBedtimeString(String bedtimeString) {
        System.out.println("SleepDTO - setBedtimeString 호출됨: " + bedtimeString);
        this.bedtimeString = bedtimeString;
        
        // String을 Timestamp로 변환 - 시간 정보까지 포함
        if (bedtimeString != null && !bedtimeString.isEmpty()) {
            try {
                // "2025-09-22T23:42" -> "2025-09-22 23:42:00"
                String dateTimeStr = bedtimeString.replace("T", " ") + ":00";
                System.out.println("SleepDTO - 변환할 문자열: " + dateTimeStr);
                
                // Timestamp로 직접 설정
                this.bedtime = Timestamp.valueOf(dateTimeStr);
                System.out.println("SleepDTO - 최종 저장된 bedtime: " + this.bedtime);
                
            } catch (Exception e) {
                System.out.println("SleepDTO - 날짜 변환 오류 상세: " + e.getMessage());
                e.printStackTrace();
                this.bedtime = new Timestamp(System.currentTimeMillis());
            }
        }
    }
    
    @Override
    public String toString() {
        return "SleepDTO{" +
                "recordId=" + recordId +
                ", memberNo=" + memberNo +
                ", sleepHours=" + sleepHours +
                ", bedtime=" + bedtime +
                ", bedtimeString='" + bedtimeString + '\'' +
                '}';
    }
}