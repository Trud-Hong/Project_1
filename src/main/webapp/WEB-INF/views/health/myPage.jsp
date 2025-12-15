<%@page import="com.health.dto.CustomInfo"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	
	CustomInfo info = (CustomInfo) session.getAttribute("customInfo");
    
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>내 정보</title>


<link rel="stylesheet" type="text/css" href="<%=cp%>/resources/css/myPage.css">
<script src="<%=cp %>/resources/js/myPage.js"></script>

</head>
<body>

<header>
    <div class="left">
			<a href="<%=cp%>/main.do">MyCondition</a>
		</div>
		<div style="font-size: 20px;">
        	내 정 보
        </div>
        <div></div>

</header>


<div class="container">

    <!-- 내 정보 조회 -->
    <div class="card">
        <h3>🧾 내 정보 조회</h3>
        <div class="info-row"><strong>아이디:</strong> ${customInfo.member_id }</div>
        <div class="info-row"><strong>이름:</strong> ${customInfo.name }</div>
        <div class="info-row"><strong>체중:</strong> ${customInfo.weight }kg</div>
        <div class="info-row"><strong>키:</strong> ${customInfo.height }cm</div>
        <div class="info-row"><strong>생년월일:</strong> ${customInfo.birth }</div>
        <div class="info-row"><strong>내 관리목표:</strong> ${customInfo.goal }</div>
    </div>

    <!-- 내 정보 수정 -->
    <div class="card">
        <h3>✏️ 내 정보 수정</h3>
        <p>체중, 키, 생년월일을 수정하려면 버튼을 눌러주세요.</p>
        <a href="myPageEdit.do" class="btn" onclick="myPageEditSocial(event);">정보수정</a>
    </div>

    <!-- 회원탈퇴 -->
    <div class="card">
        <h3>⚠️ 회원탈퇴</h3>
        <p>회원탈퇴 시 모든 데이터가 삭제됩니다.</p>

        
        <div class="info-row">
		<form id="deleteForm" method="post" action="delete_ok.do">
			<input type="hidden" name="member_no" value="${customInfo.member_no}" />
			<input type="hidden" name="member_id" value="${customInfo.member_id}" />
		    <input type="hidden" id="hiddenPassword" name="password" />
		</form>
		
		<input type="password" id="password" placeholder="비밀번호 입력"/><br/>
			<c:if test="${not empty message}">
				<div>
					<div class="fault">
						${message }
					</div>
				</div>
			</c:if>
		<a href="javascript:isDelete()" class="btn danger">회원탈퇴</a>
		</div>
    </div>

	<input type="hidden" name="member_no" value="${customInfo.member_no}">
	
	
	
	

</div>

</body>
<script type="text/javascript">
	function myPageEditSocial(e){
		const pw = <%= info.getPassword()%>
		
		if(pw == null || pw == ""){
			if(!confirm("소셜 회원용 화면으로 이동합니다")){
				e.preventDefault();
			}
		}
	}
</script>
</html>


