<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 성공</title>
<script>
    alert('${successMsg}');
    window.location.href = '<%=request.getContextPath()%>/login.do';
</script>
</head>
<body>
</body>
</html>