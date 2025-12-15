<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%
    String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
<link rel="stylesheet" type="text/css" href="<%=cp%>/resources/css/findPwd.css"/>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

</head>
<body>

<form action="" method="post" name="findIdForm">
	<header>
		<div class="left">
			<a href="<%=cp%>/main.do">MyCondition</a>
		</div>
		<div style="font-size: 20px;">
        	비밀번호 찾기
        </div>
        <div style="position: absolute; left: 95%; transform: translateX(-50%); 
        	display: flex; gap: 15px;" align="right">
        </div>
	</header>

	<div class="container">

		<div class="card">
			<h3>🧾 비밀번호 찾기</h3>
			
			<div class="info-row">
			    <label>아 이 디 :</label>
			</div>
			<div>
			    <div class="auth-input-group">
			        <input type="text" id="userId" placeholder="아이디 입력" required>
			        <button type="button" id="verifyBtn" class="btn" onclick="idCheck()">확인</button>
			    </div>
			</div><br>
			
			<div class="info-row">
				<label>이 메 일 :</label>
			</div>
			<!-- 이메일 입력 -->
		    <div class="info-row email-row">
			    <div class="email-input-group">
			        <input type="text" id="emailUser" placeholder="이메일 입력" required>
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
			        <button type="button" id="sendBtn" class="btn" onclick="sendAuthCode()" disabled="disabled">전 송</button>
			    </div>
			</div>
			
			<div id="foundPwdSection" style="display:none;">
			    <div class="info-row">
			        <label>인증번호:<span id="timer"></span></label>
			    </div>
			    <div>
			        <div class="auth-input-group">
			            <input type="text" id="authCode" placeholder="인증번호 입력" required>
			            <button type="button" id="verifyAuthBtn" class="btn" onclick="verifyAuthCode()">확인</button>
			        </div>
			    </div>
			    <div class="fault"><b>${message}</b></div>
			    <div class="button-row" style="display:none; gap:10px; justify-content:center;">
			        <button type="button" class="btn small" onclick="openChangePasswordPopup()">비밀번호 변경하기</button>
			    </div>
			</div>

		    
		</div>
	</div>
</form>


<script>
    const cp = "<%=cp%>";
</script>
<script src="<%=cp%>/resources/js/findPwd.js"></script>


</body>
</html>