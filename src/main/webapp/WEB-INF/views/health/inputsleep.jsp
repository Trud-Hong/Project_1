<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.health.dto.SleepRecordDTO" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>수면패턴 설정</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .container { max-width: 600px; margin: 0 auto; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; font-weight: bold; }
        input[type="number"], input[type="time"] { 
            padding: 8px; border: 1px solid #ddd; border-radius: 4px; 
            font-size: 14px; width: 200px; 
        }
        
    </style>
</head>
<body>
    <div class="container">
        <h1>수면패턴 설정</h1>
        
        <!-- 메시지 표시 -->

        <%
        	String message = (String) request.getAttribute("message");
        %>
        <%
        	if (message != null) {
        %>
            <div class="message success"><%=message%></div>
        <%
        	}
        %>
        
        <!-- 현재 수면패턴 표시 -->
        <%
        	SleepRecordDTO sleep = (SleepRecordDTO) request.getAttribute("sleep");
        %>

        <% if (sleep != null) { %>
            <div class="current-sleep">
                <h3>현재 수면패턴</h3>
                <p><strong>수면시간:</strong> <%= sleep.getSleepHours() %>시간</p>
                <p><strong>취침시간:</strong> <%= sleep.getBedtime() %></p>
                
                <!-- 수면시간 추천 -->
                <% String recommendation = (String) request.getAttribute("recommendation"); %>
                <% if (recommendation != null) { %>
                    <div class="recommendation">
                        <strong>추천:</strong> <%= recommendation %>
                    </div>
                <% } %>
            </div>
        <% } %>
        
        <!-- 수면패턴 설정 폼 -->
        <form method="post" action="sleep">
            <div class="form-group">
                <label for="sleepHours">평균 수면시간 (시간):</label>
                <input type="number" id="sleepHours" name="sleepHours" 
                       min="1" max="12" 
                       value="<%= sleep != null ? sleep.getSleepHours() : 8 %>" required>
                
            </div>
            
            <div class="form-group">
                <label for="bedTime">보통 취침시간:</label>
                <input type="time" id="bedTime" name="bedTime" 
                       value="<%= sleep != null ? sleep.getBedtime() : "22:00" %>" required>
              
            </div>
            
            <button type="submit">저장하기</button>
            <button type="button" class="btn-secondary" onclick="location.href='main.jsp'">뒤로가기</button>
        </form>
        
        
    </div>
</body>
</html>