package com.health.dto;

public class EatingHabitDTO {
    private int userId;          
    private String breakfast;     
    private String lunch;        
    private String dinner;        
    private String breakfastTime; 
    private String lunchTime;     
    private String dinnerTime;    
    

    public EatingHabitDTO() {}
    
  
    public EatingHabitDTO(int userId, String breakfast, String lunch, String dinner,
                         String breakfastTime, String lunchTime, String dinnerTime) {
        this.userId = userId;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
        this.breakfastTime = breakfastTime;
        this.lunchTime = lunchTime;
        this.dinnerTime = dinnerTime;
    }
    
  
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    
    public String getBreakfast() { return breakfast; }
    public void setBreakfast(String breakfast) { this.breakfast = breakfast; }
    
    public String getLunch() { return lunch; }
    public void setLunch(String lunch) { this.lunch = lunch; }
    
    public String getDinner() { return dinner; }
    public void setDinner(String dinner) { this.dinner = dinner; }
    
    public String getBreakfastTime() { return breakfastTime; }
    public void setBreakfastTime(String breakfastTime) { this.breakfastTime = breakfastTime; }
    
    public String getLunchTime() { return lunchTime; }
    public void setLunchTime(String lunchTime) { this.lunchTime = lunchTime; }
    
    public String getDinnerTime() { return dinnerTime; }
    public void setDinnerTime(String dinnerTime) { this.dinnerTime = dinnerTime; }
}