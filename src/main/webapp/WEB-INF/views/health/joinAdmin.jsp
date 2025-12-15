<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	
	String userId = "hong123";
	String password = "******";
	String name = "홍길동";
	String weight = "70kg";
	String height = "175cm";
	String birth = "1990-01-01";
	String goal = "다이어트";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin 회원가입</title>
<link rel="stylesheet" type="text/css" href="<%=cp%>/resources/css/joinAdmin.css"/>
<script src="<%=cp %>/resources/js/joinAdmin.js"></script>


</head>
<body>
<form action="" method="post" name="myForm">
	<header>
		<div class="left">
			<a href="<%=cp%>/main.do">MyCondition</a>
		</div>
		<div style="font-size: 20px;">
        	Admin 회 원 가 입
        </div>
        <div></div>
	</header>

	<div class="container">

		<!-- 내 정보 수정 -->
		<div class="card">
			<h3>🧾 회원 가입</h3>

			<div class="info-row">
				<label>아이디:</label> <input type="text" name="admin_id">
			</div>

			<div class="info-row">
				<label>비밀번호:</label> <input type="password" name="password">
			</div>
			
			<div class="info-row">
				<label>이름:</label> <input type="text" name="name">
			</div>

			<div class="info-row">
				<input type="button" class="btn" onclick="sendIt();" value="회원가입"/>
			</div>
		</div>
		
	</div>
</form>
</body>
</html>
