<%@page import="com.health.dto.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
request.setCharacterEncoding("UTF-8");
String cp = request.getContextPath();

MemberDTO goal = (MemberDTO) request.getAttribute("goal");
String message = (String) request.getAttribute("message");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리목표 설정</title>

<link rel="stylesheet" type="text/css" href="<%=cp%>/resources/css/goal.css"/>

</head>
<body>


	<header>
	    <div class="left">
			<a href="<%=cp%>/main.do">MyCondition</a>
		</div>
		<div style="font-size: 20px;">
        	관 리 목 표 설 정
        </div>
        <div></div>
	</header>


<div class="container">

    <!-- 메시지 표시 -->

    <c:if test="${not empty message }">
        <div class="card message">${message}</div>
    </c:if>

    <!-- 현재 설정된 목표 -->
    <c:if test="${not empty dto.goal }">

    <div class="card">
        <h3>📌 현재 설정된 목표</h3>
        <div class="info-row">
            <strong>목표:</strong>

            <c:if test="${dto.goal == '다이어트' }"> 다이어트
			</c:if>
            <c:if test="${dto.goal == '벌크업' }"> 벌크업
			</c:if>
            <c:if test="${dto.goal == '건강유지' }"> 건강 유지
			</c:if>
        </div>
        <div class="info-row"><%-- <strong>설명:</strong> <%= goal.getDescription() %> --%></div>
    </div>
    </c:if>


    <!-- 목표 설정 -->
    <div class="card">
        <h3>🎯 목표 선택</h3>
        <form method="post" action="goalUpdate.do">
            <div class="radio-group">

                <input type="radio" id="diet" name="goal" value="다이어트" 
					<c:if test="${dto.goal == '다이어트'}">checked="checked"</c:if>>
                <label for="diet">다이어트 - 체중 감량</label>
            </div>
            <div class="radio-group">
                <input type="radio" id="bulkup" name="goal" value="벌크업"
					<c:if test="${dto.goal == '벌크업'}">checked="checked"</c:if>>
                <label for="bulkup">벌크업 - 체중 증가</label>
            </div>
            <div class="radio-group">
                <input type="radio" id="health" name="goal" value="건강유지"
					<c:if test="${dto.goal == '건강유지'}">checked="checked"</c:if>>
                <label for="health">건강 유지 - 현재 상태 유지</label>
            </div>
            
            <input type="hidden" name="member_no" value="${customInfo.member_no }">
            

			<div class="button-wrap">
	            <button type="submit" class="btn">저장하기</button>
	            <button type="button" class="btn danger" onclick="location.href='/heal/main'">뒤로가기</button>
            </div>
        </form>
    </div>

</div>

</body>

</html>

