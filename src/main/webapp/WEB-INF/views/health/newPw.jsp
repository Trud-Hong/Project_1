<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%
    String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 재설정</title>
<link rel="stylesheet" type="text/css" href="<%=cp%>/resources/css/findPwd.css"/>
<script src="<%=cp%>/resources/js/newPwd.js"></script>

</head>
<body>

<form action="" method="post" name="newPwdForm">
	<header>
		<div class="left">
			<a href="<%=cp%>/main.do">MyCondition</a>
		</div>
		<div style="font-size: 20px;">
        	비밀번호 재설정
        </div>
        <div style="position: absolute; left: 95%; transform: translateX(-50%); 
        	display: flex; gap: 15px;" align="right">
        </div>
	</header>

	<div class="container">

		<div class="card">
			<h3>🧾 비밀번호 재설정</h3>
			
			<div class="info-row">
			    <label>새 비밀번호 :</label>
			</div>
			<div>
			    <div class="auth-input-group">
			        <input type="text" id="newPwd" placeholder="새 비밀번호 입력" required>
			        <input type="text" id="newPwdConfirm" placeholder="새 비밀번호 확인" required>
			        <button type="button" id="verifyBtn" class="btn" onclick="pwdCheck()">확인</button>
			    </div>
			</div><br><br>
			
			<div id="foundPwdSection" style="display:none;">
			    <div class="fault"><b>${message}</b></div>
				<div class="button-row" style="display:none; gap:10px; justify-content:center;">
					<button type="button" class="btn small" onclick="location.href='/heal/login'">확인</button>
					<button type="button" class="btn small danger" onclick="location.href='/heal/main'">첫 화면으로</button>
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