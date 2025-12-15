<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	
	String goal = "다이어트";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원가입</title>
<link rel="stylesheet" type="text/css" href="<%=cp%>/resources/css/join.css"/>


</head>
<body>
<form action="join_ok.do" method="post" name="myForm">
	<header>
		<div class="left">
			<a href="<%=cp%>/main.do">MyCondition</a>
		</div>
		<div style="font-size: 20px;">
        	회 원 가 입
        </div>
        <div></div>
	</header>

	<div class="container">

		<!-- 내 정보 수정 -->
		<div class="card">
			<h3>🧾 회원 가입</h3>
			<div class="email-input-group"><label>아이디:</label></div>
			<div class="email-input-group">
				<input type="text" id="userId" name="member_id">
				<input type="button" class="btnId" value="중복확인" onclick="checkIdDuplicate()"/>
			</div>

			<div class="info-row">
				<label>비밀번호:</label> <input type="password" name="password">
			</div>

			<div class="info-row">
				<label>이름:</label> <input type="text" name="name">
			</div>

			<div class="info-row">
				<label>생년월일:</label> 
				<input type="date" name="birth" min=1900-01-01
	       		max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
			</div>

			<label class="label">이 메 일:</label> 
			<div class="email-input-group">
				    <input type="text" id="emailUser" placeholder="이메일 입력">
				    <span>@</span>
				    <div class="email-domain-wrapper">
					    <input type="text" id="emailDomain" placeholder="직접입력 또는 선택" autocomplete="off">
					    <ul id="domainList" class="domain-list">
					        <li data-value="naver.com">naver.com</li>
					        <li data-value="gmail.com">gmail.com</li>
					        <li data-value="nate.com">nate.com</li>
					        <li data-value="">직접입력</li>
					    </ul>
					</div>
					<input type="hidden" id="email" name="email">
				    <button type="button" class="btn" onclick="checkEmailDuplicate()">중복확인</button>
				</div><br>

			<div class="info-row">
				<label>성별:</label>
				<div style="display: flex; align-content: center; gap: 5px;">
					<input type="radio" id="male" name="gender" value="M">
					<label for="male">남자</label>
					<input type="radio" id="female" name="gender" value="F">
					<label for="female">여자</label>
				</div>
			</div>

			<div class="info-row">
				<label>체중:</label>
				<div style="display: flex; align-items: center; gap: 5px;">
					<input type="number" min="3" step="0.1" 
						id="weightInput" name="weight"
						style="flex: 1;"> <span>kg</span>
				</div>
			</div>

			<div class="info-row">
				<label>키:</label>
				<div style="display: flex; align-items: center; gap: 5px;">
					<input type="number" min="50" step="0.1" 
						id="heightInput" name="height""
						style="flex: 1;"> <span>cm</span>
				</div>
			</div>


			<div class="info-row">
				<label>내 관리목표:</label> <select name="goal">
					<option value="다이어트" <%=goal.equals("다이어트") ? "selected" : ""%>>다이어트</option>
					<option value="벌크업" <%=goal.equals("벌크업") ? "selected" : ""%>>벌크업</option>
					<option value="건강유지" <%=goal.equals("건강유지") ? "selected" : ""%>>건강유지</option>
				</select>
			</div>
			
			<div class="info-row agree-container">
				<div class="right">
					<input type="checkbox" id="personalInformationChk" name="personalInformation" disabled="disabled" required>
					<a onclick="openPersonalInformationPopup()">개인정보 수집 이용 동의</a>
				</div>
			</div>
			
			<div class="info-row">
				<input type="submit" class="btn" onclick="return validateForm();" value="회원가입"/>
			</div>
		</div>
		
	</div>
</form>

<c:if test="${not empty message}">
    <script>
        alert('${message}');
    </script>
</c:if>

<script src="<%=cp %>/resources/js/join.js"></script>
</body>
</html>
