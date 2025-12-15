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
<title>개인정보 수집 이용 동의</title>
<link rel="stylesheet" type="text/css" href="<%=cp%>/resources/css/personalInformation.css"/>
<script src="<%=cp %>/resources/js/join.js"></script>

</head>
<body>




	<div class="container">

		<div class="card">
			<h3>✔ 개인정보 수집 이용 동의</h3>
			
			<div class="info-row">
				<div class="personal-info-text">
<strong>MyCondition 개인정보 처리방침</strong>
정보통신망 이용촉진 등에 관한 법률 등 관련 법률에 의한 개인정보 보호규정 및 정보통신부가 제정한 개인정보지침을 준수하고 있습니다.

<strong>1. 개인정보의 수집 항목</strong>
원활한 서비스 이용과 고객과의 소통을 위해 수집합니다.
• [공통] 필수항목: 아이디, 비밀번호, 이름, 생년월일, 이메일, 성별, 신체정보 

<strong>2. 개인정보의 수집 방법</strong>
회원 가입 시에 필수항목 기재를 요청합니다.

<strong>3. 개인정보의 수집 이용 목적</strong>
• 아이디, 비밀번호, 이메일: 회원 가입시에 사용자확인, 중복가입 방지, 아이디 및 비밀번호 찾기 시 사용자확인, 부정 이용 방지를 위한 목적으로 사용합니다.
• 이름, 생년월일, 성별: 회원 가입시에 사용자 확인 목적으로 사용합니다.
• 신체정보: 신체정보를 통해 현재 건강 정보를 사용자에게 제공합니다.

<strong>4. 개인정보의 보유 및 이용기간</strong>
- 가입 회원정보는 탈퇴할 시 즉시 파기됩니다.
- 게시물의 내용은 사이트 폐쇄 시까지 보관합니다.

개인정보 수집 및 이용에 대한 동의를 거부할 권리가 있습니다. 다만, 동의를 거부할 경우 회원가입, 회원 서비스, 로그인 서비스가 제한될 수 있습니다.
</div>
			</div>

			<div style="text-align: center;">
				<input type="button" value=" 확 인 " class="btn" onclick="closePopup();"><br>
				
			</div>
		</div>

	</div>
</body>
</html>


