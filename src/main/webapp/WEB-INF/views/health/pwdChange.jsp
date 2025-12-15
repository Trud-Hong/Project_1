<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%
    String cp = request.getContextPath();
    String memberId = request.getParameter("memberId");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 변경</title>
<link rel="stylesheet" type="text/css" href="<%=cp%>/resources/css/pwdChange.css"/>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>

<div class="container">
    <div class="card">
        <h3>비밀번호 변경</h3>
        <form id="pwdChange">
            <input type="hidden" id="member_id" value="${memberId}"/>
            
            <script>
			    console.log("팝업 열림! hidden memberId:", $("#memberId").val());
			</script>
            
            <div class="input-group">
                <label>새 비밀번호</label>
                <input type="password" id="newPassword" placeholder="새 비밀번호 입력" required/>
            </div>
            <div class="input-group">
                <label>새 비밀번호 확인</label>
                <input type="password" id="confirmPassword" placeholder="비밀번호 확인" required/>
            </div>
            <div class="button-row">
                <button type="button" id="changeBtn" class="btn">변경하기</button>
            </div>
        </form>
    </div>
</div>

<script>
const cp = "<%=cp%>";

$("#changeBtn").click(function(){
    const pwd = $("#newPassword").val();
    const confirm = $("#confirmPassword").val();
    const member_id = $("#member_id").val(); // 여기서 가져온 값 사용

    console.log("클릭! member_id:", member_id, "newPassword:", pwd); // member_id 사용

    if(!pwd || !confirm) {
        alert("비밀번호를 입력하세요.");
        return;
    }

    if(pwd !== confirm) {
        alert("비밀번호가 일치하지 않습니다.");
        return;
    }

    $.ajax({
        url: cp + '/updatePassword',
        type: 'POST',
        data: { member_id: member_id, newPassword: pwd },
        success: function(res){
            console.log("서버 응답:", res);
            if(res === 'success'){
                alert("비밀번호가 변경되었습니다.");
                window.opener.location.href = cp + '/login.do';
                window.close();
            } else {
                alert("비밀번호 변경 실패");
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
