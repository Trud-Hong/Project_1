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
<title>로 그 인</title>
<link rel="stylesheet" type="text/css" href="<%=cp%>/resources/css/login.css"/>
<script src="<%=cp %>/resources/js/login.js"></script>
</head>
<body>

<form action="<%=cp %>/login_ok.do" method="post" name="myForm">
	<header>
		<div class="left">
			<a href="<%=cp%>/main.do">MyCondition</a>
		</div>
		<div style="font-size: 20px;">
        	로 그 인
        </div>
        <div style="position: absolute; left: 95%; transform: translateX(-50%); 
        	display: flex; gap: 15px;" align="right">
        </div>
	</header>

	<div class="container">

		<div class="card">
			<h3>🧾 로&nbsp;그&nbsp;인</h3>
			
			<div class="info-row">
				<label>아&nbsp;이&nbsp;디:</label> <input type="text" name="member_id" onkeyup="enterkey(event);" />
			</div>

			<div class="info-row">
				<label>비밀번호:</label> <input type="password" name="password" onkeyup="enterkey(event);" />
			</div>
			
			<div style="text-align: center;">
				<input type="button" value=" 로 그 인 " class="btn" 
				onclick="login();"><br>
				
			</div>
		</div>
		<div style="text-align: center;">

		<input type="button" value=" 아이디 찾기 " class="link-btn" 
				onclick="location='<%=cp%>/findId.do'">|
				<input type="button" value=" 비밀번호 찾기 " class="link-btn"  
				onclick="location='<%=cp%>/findPwd.do'">|
				<input type="button" value=" 회 원 가 입 "  class="link-btn"
				onclick="location='<%=cp%>/join.do'">
		</div>
		
		<%-- <div style="text-align: center;">
		소셜 계정으로 로그인
		<br/><br/>
			<div>
				<img src="<c:url value='/resources/images/google.png'/>" alt="Google" width="50" height="50"
				style="cursor: pointer;"
				onclick="location='<%=cp%>/googleLogin'">

			
			</div>
		</div> --%>
		<!-- 알림 메시지 (경고창) - form 바깥 또는 container 안 가장 마지막에 위치 -->
		<c:if test="${param.message == 'alreadyExists'}">
		    <script>alert("이미 가입된 이메일입니다. 일반 로그인을 이용해주세요.");</script>
		</c:if>
		
		<div class="social-login-container">
		    <p>소셜 계정으로 로그인</p>
		    <a href="<%=cp%>/googleLogin" class="google-btn" aria-label="Sign in with Google">
		        <div class="google-btn-content">
		            <img src="<c:url value='/resources/images/google.png'/>" alt="Google logo">
		            <span>Google로 로그인</span>
		        </div>
		    </a>
		</div>
	</div>
</form>
</body>
</html>


