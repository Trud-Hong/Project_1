<%@page import="com.health.dto.CustomInfo"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	
	String id = (String) session.getAttribute("googleUserId");
	session.removeAttribute("googleUserId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>비밀번호 설정</title>
<link rel="stylesheet" type="text/css" href="<%=cp%>/resources/css/join.css"/>
<script src="<%=cp %>/resources/js/googlePw.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

</head>
<body>
<form action="googleJoin_ok.do" method="post" name="myForm">
<input type="hidden" name="member_id" id="member_id" value="<%=id%>">
	<header>
		<div class="left">
			<a href="<%=cp%>/main.do">MyCondition</a>
		</div>
		<div style="font-size: 20px;">
        	비밀번호 설정
        </div>
        <div></div>
	</header>

	<div class="container">

		<!-- 내 정보 수정 -->
		<div class="card">

			<div class="info-row">
				<label>비밀번호:</label> <input type="password" name="password" id="password">
			</div>

			<div class="info-row">
				<!-- <input type="submit" id="googleBtn" class="btn" onclick="return validateForm();" value="완료"/> -->
				<button id="googleBtn" class="btn" type="button">완료</button>
			</div>
		</div>
		
	</div>
</form>


<!-- 
<script>
const cp = "<%=cp%>";

$("#googleBtn").click(function(){
    const pwd = $("#newPassword").val();
    const member_id = $("#member_id").val(); // 여기서 가져온 값 사용

    console.log("클릭! member_id:", member_id, "newPassword:", pwd); // member_id 사용

    $.ajax({
        url: cp + '/updatePassword',
        type: 'POST',
        data: { member_id: member_id, newPassword: pwd },
        success: function(res){
            console.log("서버 응답:", res);
            if(res === 'success'){
                alert("비밀번호가 설정되었습니다.");
            } else {
                alert("비밀번호 설정 실패");
            }
        },
        error: function(){
            alert("서버 오류 발생");
        }
    });
});
</script>
 -->
 
<script>
    var memberId = "<c:out value='${sessionScope.customInfo.member_id}' />";
    console.log("세션 CustomInfo:", {
        member_id: "${sessionScope.customInfo.member_id}",
        name: "${sessionScope.customInfo.name}"
    });
    
</script>

<script>
const cp = "<%=cp%>";

$("#googleBtn").click(function(){
    const pwd = $("#password").val();
    const member_id = '<%=id%>';

    console.log("클릭! member_id:", member_id, "newPassword:", pwd); // member_id 사용
    
    if(member_id == ""){
    	
    	alert("로그인을 다시 해주세요");
    	//location.href = cp + '/login.do';
    	location.href = `\${cp}/login.do`;
    	return;
    }
    
    if(!pwd || !confirm) {
        alert("비밀번호를 입력하세요.");
        return;
    }

    $.ajax({
        url: cp + '/updatePassword',
        type: 'POST',
        data: { member_id: member_id, newPassword: pwd },
        success: function(res){
            console.log("서버 응답:", res);
            if(res === 'success'){
                alert("비밀번호가 설정되었습니다.");
                window.location.href = cp + '/googleJoin_ok.do';
            } else {
                alert("비밀번호 설정 실패");
            }
        },
        error: function(){
            alert("서버 오류 발생");
        }
    });
});
</script>


</body>
</html>
