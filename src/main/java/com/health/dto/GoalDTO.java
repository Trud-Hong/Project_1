package com.health.dto;


public class GoalDTO {
    private int userId;        
    private String goalType;   
    private String description; 
    

    public GoalDTO() {}
    

    public GoalDTO(int userId, String goalType, String description) {
        this.userId = userId;
        this.goalType = goalType;
        this.description = description;
    }
    
 
    public int getUserId() { 
        return userId; 
    }
    
    public void setUserId(int userId) { 
        this.userId = userId; 
    }
    
    public String getGoalType() { 
        return goalType; 
    }
    
    public void setGoalType(String goalType) { 
        this.goalType = goalType; 
    }
    
    public String getDescription() { 
        return description; 
    }
    
    public void setDescription(String description) { 
        this.description = description; 
    }
}