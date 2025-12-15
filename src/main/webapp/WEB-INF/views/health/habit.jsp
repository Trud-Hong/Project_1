<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>식습관 설정</title>

<link rel="stylesheet" type="text/css" href="<%=cp %>/resources/css/habit.css"/>
<script src="<%=cp %>/resources/js/habit.js"></script>
</head>
<body>


	<header>
	    <div class="left">
			<a href="<%=cp%>/main.do">MyCondition</a>
		</div>
		<div style="font-size: 20px;">
        	식 습 관 설 정
        </div>
        <div></div>
	</header>


<form method="post" action="habitUpdate.do">
	<div class="container">

	<input type="hidden" name="record_id" value="${customInfo.record_id }">
	<input type="hidden" name="member_no" value="${customInfo.member_no }">
	
	    <!-- 아침 -->
	    <div class="card">
	        <h3>🌅 아침식사</h3>
	        <div class="checkbox-group">
	        	<c:choose>
	        		<c:when test="${empty breakfastTimeStr }">
						<input type="checkbox" id="breakfast" onchange="toggleTimeInput('breakfast')">
	        		</c:when>
	        		<c:otherwise>
						<input type="checkbox" id="breakfast" checked="checked" onchange="toggleTimeInput('breakfast')">
	        		</c:otherwise>
	        	</c:choose>
	            <label for="breakfast">아침을 먹습니다 </label>
	            <input type="time" name="breakfast_time" id="breakfastTime" min="05:00" max="11:00" value="${empty breakfastTimeStr ? '' : breakfastTimeStr}" disabled>
	        </div>
	    </div>
	
	    <!-- 점심 -->
	    <div class="card">
	        <h3>🥗 점심식사</h3>
	        <div class="checkbox-group">
	        	<c:choose>
	        		<c:when test="${empty lunchTimeStr }">
			            <input type="checkbox" id="lunch" onchange="toggleTimeInput('lunch')">
	        		</c:when>
	        		<c:otherwise>
			            <input type="checkbox" id="lunch" checked="checked" onchange="toggleTimeInput('lunch')">
	        		</c:otherwise>
	        	</c:choose>
	            <label for="lunch">점심을 먹습니다 </label>
	            <input type="time" name="lunch_time" id="lunchTime" min="11:00" max="17:00" value="${empty lunchTimeStr ? '' : lunchTimeStr}" disabled>
	        </div>
	    </div>
	
	    <!-- 저녁 -->
	    <div class="card">
	        <h3>🌙 저녁식사</h3>
	        <div class="checkbox-group">
	        	<c:choose>
	        		<c:when test="${empty dinnerTimeStr }">
			            <input type="checkbox" id="dinner" onchange="toggleTimeInput('dinner')">
	        		</c:when>
	        		<c:otherwise>
			            <input type="checkbox" id="dinner" checked="checked" onchange="toggleTimeInput('dinner')">
	        		</c:otherwise>
	        	</c:choose>
	            <label for="dinner">저녁을 먹습니다 </label>
	            <input type="time" name="dinner_time" id="dinnerTime" min="17:00" max="23:59" value="${empty dinnerTimeStr ? '' : dinnerTimeStr}" disabled>
	        </div>
	    </div>
	
	    <!-- 버튼 -->
	    <div class="card" style="text-align:center;">
	        <button type="submit" class="btn">저장하기</button>
			<button type="button" class="btn danger" onclick="location.href='/heal/main.do'">뒤로가기</button>
	    </div>
	
	</div>
</form>


</body>
</html>
