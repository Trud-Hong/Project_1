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
<title>Admin 로 그 인</title>
<link rel="stylesheet" type="text/css" href="<%=cp%>/resources/css/loginAdmin.css"/>
<script src="<%=cp %>/resources/js/loginAdmin.js"></script>
</head>
<body>

<form action="" method="post" name="myForm">
	<header>
		<div class="left">
			<a href="<%=cp%>/main.do">MyCondition</a>
		</div>
		<div style="font-size: 20px;">
        	Admin 로 그 인
        </div>
        <div style="position: absolute; left: 95%; transform: translateX(-50%); 
        	display: flex; gap: 15px;" align="right">
        	<a href="<%=cp%>/login.do">
        		Member
        	</a>
        </div>
	</header>

	<div class="container">

		<div class="card">
			<h3>🧾 로&nbsp;그&nbsp;인</h3>
			
			<div class="info-row">
				<label>아&nbsp;이&nbsp;디:</label> <input type="text" name="admin_id">
			</div>

			<div class="info-row">
				<label>비밀번호:</label> <input type="password" name="password">
			</div>
			
			<div>
				<div class="fault">
					<b>${message }</b>
				</div>
			</div>

			<div style="text-align: center;">
				<input type="button" value=" 로 그 인 " class="btn" 
				onclick="loginAdmin();"><br>
				
			</div>
		</div>
		<div style="text-align: center;">
		<!-- <input type="button" value=" 비밀번호 찾기 " class="link-btn" 
				onclick="loginAdmin();">|
				<input type="button" value=" 아이디 찾기 " class="link-btn"  
				onclick="loginAdmin();">| -->
				<input type="button" value=" 회 원 가 입 "  class="link-btn"
				onclick="location='<%=cp%>/joinAdmin.do'">
		</div>
	</div>
</form>
</body>
</html>


