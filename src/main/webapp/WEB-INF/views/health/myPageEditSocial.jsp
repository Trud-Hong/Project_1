<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내 정보 수정</title>
 
<link rel="stylesheet" type="text/css" href="<%=cp%>/resources/css/myPageEdit.css">
<script src="<%=cp %>/resources/js/myPageEdit.js"></script>

</head>
<body>

	<header>
		<div class="left">
			<a href="<%=cp%>/main.do">MyCondition</a>
		</div>
		<div style="font-size: 20px;">
        	내 정보
        </div>
        <div></div>
	</header>

	<div class="container">
		
		<!-- 서버 메시지 출력 -->
	    <c:if test="${not empty message}">
	        <script>alert('${message}');</script>
	    </c:if>
		
		<!-- 내 정보 수정 -->
		<form action="<%=cp%>/myPageEdit_ok.do" method="post" name="myForm" onsubmit="return update();">

		<input name="member_no" type="hidden" value="${customInfo.member_no}">
		<input name="gender" type="hidden" value="${customInfo.gender}">
		

		<div class="card">
			<h3>🧾 내 정보 수정</h3>

			<div class="info-row">

				<label>아이디:</label> <input name="member_id" type="text" value="${customInfo.member_id }"
					readonly style="background: #dbdbdb;">
			</div> 

			<div class="info-row">
				<label>이름:</label> <input name="name" type="text" value="${customInfo.name }"
				readonly style="background: #dbdbdb;">

			</div>

			<div class="info-row">
				<label>체중:</label>
				<div style="display: flex; align-items: center; gap: 5px;">

					<input name="weight" min=3 type="number" value="${customInfo.weight }" min="0" id="weightInput"

						style="flex: 1;"> <span>kg</span>
				</div>
			</div>

			<div class="info-row">
				<label>키:</label>
				<div style="display: flex; align-items: center; gap: 5px;">

					<input name="height" min=50 type="number" value="${customInfo.height }" min="0" id="heightInput"

						style="flex: 1;"> <span>cm</span>
				</div>
			</div>

			<div class="info-row">
				<label>생년월일:</label> 
				<c:set var="birthDate">
				    <fmt:formatDate value="${customInfo.birth}" pattern="yyyy-MM-dd" />
				</c:set>
				<input name="birth" type="date" value="${birthDate}"
						min = 1900-01-01
						max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
			</div>

			<div class="info-row">

				<label>내 관리목표:</label> <select name="goal">
					<option value="다이어트" ${customInfo.goal.equals("다이어트") ? "selected" : ""}>다이어트</option>
					<option value="벌크업" ${customInfo.goal.equals("벌크업") ? "selected" : ""}>벌크업</option>
					<option value="건강유지" ${customInfo.goal.equals("건강유지") ? "selected" : ""}>건강유지</option>

				</select>
			</div>
			
			<button class="btn" type="submit">저장하기</button>
			<button class="btn" type="button" onclick="location='<%=cp%>/myPage.do';">취소하기</button>
		</div>
	</form>
	</div>
	
</body>
</html>
