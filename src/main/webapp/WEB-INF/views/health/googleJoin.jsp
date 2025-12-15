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
<link rel="stylesheet" type="text/css" href="<%=cp%>/resources/css/findId.css"/>
<script src="<%=cp %>/resources/js/login.js"></script>
</head>
<body>

<form action="" method="post" name="myForm">
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
			
			축하합니다 구글 회원가입이 완료되어 구글계정으로 로그인 할 수 있습니다.<br/><br/>
			<div align="center">
				<button type="button" class="btn small" onclick="location.href='/heal/login'">로그인하기</button>
			</div>
		</div>
	</div>
</form>
</body>
</html>


