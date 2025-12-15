<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.health.dto.EatingHabitDTO" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>식습관 설정</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .container { max-width: 600px; margin: 0 auto; }
        .form-group { margin-bottom: 20px; padding: 15px; border: 1px solid #e0e0e0; border-radius: 8px; }
        .meal-title { font-weight: bold; font-size: 16px; margin-bottom: 10px; color: #333; }
        .checkbox-group { display: flex; align-items: center; margin-bottom: 10px; }
        .checkbox-group input[type="checkbox"] { margin-right: 10px; transform: scale(1.2); }
        .time-input { margin-left: 20px; }
        input[type="time"] { 
            padding: 5px 10px; border: 1px solid #ddd; border-radius: 4px; 
            font-size: 14px; 
        }
       
    </style>
</head>
<body>
    <div class="container">
        <h1>식습관 설정</h1>
        
        <!-- 메시지 표시 -->
        <% String message = (String) request.getAttribute("message"); %>
        <% if (message != null) { %>
            <div class="message success"><%= message %></div>
        <% } %>
        
        <!-- 현재 식습관 표시 -->
        <% EatingHabitDTO habit = (EatingHabitDTO) request.getAttribute("habit"); %>
        <% if (habit != null) { %>
            <div class="current-habit">
                <h3>현재 식습관</h3>
                <div class="meal-summary">
                    <span class="meal-item <%= "Y".equals(habit.getBreakfast()) ? "meal-yes" : "meal-no" %>">
                        아침: <%= "Y".equals(habit.getBreakfast()) ? habit.getBreakfastTime() : "안 먹음" %>
                    </span>
                    <span class="meal-item <%= "Y".equals(habit.getLunch()) ? "meal-yes" : "meal-no" %>">
                        점심: <%= "Y".equals(habit.getLunch()) ? habit.getLunchTime() : "안 먹음" %>
                    </span>
                    <span class="meal-item <%= "Y".equals(habit.getDinner()) ? "meal-yes" : "meal-no" %>">
                        저녁: <%= "Y".equals(habit.getDinner()) ? habit.getDinnerTime() : "안 먹음" %>
                    </span>
                </div>
                
                <!-- 식습관 조언 -->
                <% String advice = (String) request.getAttribute("advice"); %>
                <% if (advice != null) { %>
                    <div class="advice">
                        <strong>💡 조언:</strong> <%= advice %>
                    </div>
                <% } %>
            </div>
        <% } %>
        
        <!-- 식습관 설정 폼 -->
        <form method="post" action="eatingHabit">
            <!-- 아침 -->
            <div class="form-group">
                <div class="meal-title">아침식사</div>
                <div class="checkbox-group">
                    <input type="checkbox" id="breakfast" name="breakfast" value="Y"
                           <%= habit != null && "Y".equals(habit.getBreakfast()) ? "checked" : "" %>
                           onchange="toggleTimeInput('breakfast')">
                    <label for="breakfast">아침을 먹습니다</label>
                </div>
                <div class="time-input">
                    <label for="breakfastTime">아침 식사 시간:</label>
                    <input type="time" id="breakfastTime" name="breakfastTime" 
                           value="<%= habit != null ? habit.getBreakfastTime() : "08:00" %>"
                           <%= habit == null || "N".equals(habit.getBreakfast()) ? "disabled" : "" %>>
                </div>
            </div>
            
            <!-- 점심 -->
            <div class="form-group">
                <div class="meal-title">점심식사</div>
                <div class="checkbox-group">
                    <input type="checkbox" id="lunch" name="lunch" value="Y"
                           <%= habit != null && "Y".equals(habit.getLunch()) ? "checked" : "" %>
                           onchange="toggleTimeInput('lunch')">
                    <label for="lunch">점심을 먹습니다</label>
                </div>
                <div class="time-input">
                    <label for="lunchTime">점심 식사 시간:</label>
                    <input type="time" id="lunchTime" name="lunchTime" 
                           value="<%= habit != null ? habit.getLunchTime() : "12:00" %>"
                           <%= habit == null || "N".equals(habit.getLunch()) ? "disabled" : "" %>>
                </div>
            </div>
            
            <!-- 저녁 -->
            <div class="form-group">
                <div class="meal-title">저녁식사</div>
                <div class="checkbox-group">
                    <input type="checkbox" id="dinner" name="dinner" value="Y"
                           <%= habit != null && "Y".equals(habit.getDinner()) ? "checked" : "" %>
                           onchange="toggleTimeInput('dinner')">
                    <label for="dinner">저녁을 먹습니다</label>
                </div>
                <div class="time-input">
                    <label for="dinnerTime">저녁 식사 시간:</label>
                    <input type="time" id="dinnerTime" name="dinnerTime" 
                           value="<%= habit != null ? habit.getDinnerTime() : "18:00" %>"
                           <%= habit == null || "N".equals(habit.getDinner()) ? "disabled" : "" %>>
                </div>
            </div>
            
            <button type="submit">저장하기</button>
            <button type="button" class="btn-secondary" onclick="location.href='main.jsp'">뒤로가기</button>
        </form>
        
       
    </div>
    
    <script>
       
        function toggleTimeInput(mealType) {
            const checkbox = document.getElementById(mealType);
            const timeInput = document.getElementById(mealType + 'Time');
            
            if (checkbox.checked) {
                timeInput.disabled = false;
                timeInput.style.opacity = '1';
            } else {
                timeInput.disabled = true;
                timeInput.style.opacity = '0.5';
            }
        }
        
      
        window.onload = function() {
            toggleTimeInput('breakfast');
            toggleTimeInput('lunch');
            toggleTimeInput('dinner');
        };
    </script>
</body>
</html>