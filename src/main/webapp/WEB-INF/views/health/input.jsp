<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.health.dto.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>건강 관리 시스템</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .container { max-width: 800px; margin: 0 auto; }
        .header { text-align: center; margin-bottom: 30px; }
        .dashboard { margin-bottom: 20px; }
        .card { 
            border: 1px solid #ccc; 
            padding: 15px; 
            margin-bottom: 15px; 
        }
        .card-title { font-weight: bold; margin-bottom: 10px; }
        button { 
            padding: 8px 15px; 
            margin: 5px; 
            cursor: pointer; 
        }
        .stats-table { 
            width: 100%; 
            border-collapse: collapse; 
            margin-bottom: 20px; 
        }
        .stats-table td, .stats-table th { 
            border: 1px solid #ccc; 
            padding: 8px; 
            text-align: center; 
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- 헤더 -->
        <div class="header">
            <h1>건강 관리 시스템</h1>
        </div>
        
        <!-- 빠른 통계 -->

        <%
        	BloodPressureDTO bp = (BloodPressureDTO) request.getAttribute("bp");
                    SleepRecordDTO sleep = (SleepRecordDTO) request.getAttribute("sleep");
                    EatingHabitDTO habit = (EatingHabitDTO) request.getAttribute("habit");
                    GoalDTO goal = (GoalDTO) request.getAttribute("goal");

        %>
        
        <h3>건강 현황 요약</h3>
        <table class="stats-table">
            <tr>
                <th>혈압</th>
                <th>수면시간</th>
                <th>정규식사</th>
                <th>목표</th>
            </tr>
            <tr>
                <td><%= bp != null ? bp.getBpHigh() + "/" + bp.getBpLow() + " mmHg" : "미설정" %></td>
                <td><%= sleep != null ? sleep.getSleepHours() + "시간" : "미설정" %></td>
                <td>
                    <% 
                        int mealCount = 0;
                        if (habit != null) {
                            if ("Y".equals(habit.getBreakfast())) mealCount++;
                            if ("Y".equals(habit.getLunch())) mealCount++;
                            if ("Y".equals(habit.getDinner())) mealCount++;
                        }
                    %>
                    <%= mealCount %>/3회
                </td>
                <td>
                    <% if (goal != null) { %>
                        <% if ("diet".equals(goal.getGoalType())) { %>
                            다이어트
                        <% } else if ("bulkup".equals(goal.getGoalType())) { %>
                            체중증가
                        <% } else { %>
                            건강유지
                        <% } %>
                    <% } else { %>
                        미설정
                    <% } %>
                </td>
            </tr>
        </table>
        
        <!-- 메인 메뉴 -->
        <div class="dashboard">
            <!-- 혈압 관리 -->
            <div class="card">
                <div class="card-title">혈압 관리</div>
                <p>
                    <% if (bp != null) { %>
                        현재 혈압: <%= bp.getBpHigh() %>/<%= bp.getBpLow() %> mmHg (상태: <%=  %>)
                    <% } else { %>
                        혈압 정보를 입력하여 건강 상태를 확인하세요.
                    <% } %>
                </p>
                <button onclick="location.href='bloodPressure.do'">혈압 정보 관리</button>
            </div>
            
            <!-- 식습관 관리 -->
            <div class="card">
                <div class="card-title">식습관 관리</div>
                <p>
                    <% if (habit != null) { %>
                        <% 
                            int totalMeals = 0;
                            if ("Y".equals(habit.getBreakfast())) totalMeals++;
                            if ("Y".equals(habit.getLunch())) totalMeals++;
                            if ("Y".equals(habit.getDinner())) totalMeals++;
                        %>
                        규칙적인 식사: <%= totalMeals %>/3회
                    <% } else { %>
                        식습관을 설정하여 건강한 식단을 계획하세요.
                    <% } %>
                </p>
                <button onclick="location.href='eatingHabit.do'">식습관 설정</button>
            </div>
            
            <!-- 관리목표 -->
            <div class="card">
                <div class="card-title">관리목표</div>
                <p>
                    <% if (goal != null) { %>
                        목표: 
                        <% if ("diet".equals(goal.getGoalType())) { %>
                            다이어트
                        <% } else if ("bulkup".equals(goal.getGoalType())) { %>
                            체중 증가
                        <% } else { %>
                            건강 유지
                        <% } %>
                    <% } else { %>
                        건강 관리 목표를 설정하세요.
                    <% } %>
                </p>
                <button onclick="location.href='goal.do'">목표 설정</button>
            </div>
            
            <!-- 수면패턴 -->
            <div class="card">
                <div class="card-title">수면패턴</div>
                <p>
                    <% if (sleep != null) { %>
                        수면시간: <%= sleep.getSleepHours() %>시간, 취침시간: <%= sleep.getBedtime() %>
                    <% } else { %>
                        수면 패턴을 기록하여 수면의 질을 개선하세요.
                    <% } %>
                </p>
                <button onclick="location.href='inputsleep.do'">수면패턴 설정</button>
            </div>
            
            <button onclick="location.href='main.do'">저장하기</button>
            
        </div>
    </div>
</body>
</html>