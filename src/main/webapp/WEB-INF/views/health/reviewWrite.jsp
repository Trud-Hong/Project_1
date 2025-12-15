<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>이용 후기</title>

<link rel="stylesheet" type="text/css" href="<%=cp%>/resources/css/review.css">

<script src="<%=cp%>/resources/js/review.js"></script>

</head>
<body>

	<header>
	    <div class="left">
			<a href="<%=cp%>/main.do">MyCondition</a>
		</div>
		<div style="font-size: 20px;">
        	이 용 후 기
        </div>
        <div></div>
	</header>

	<div class="container">

 		<!-- 로그인한 사용자만 후기 작성 가능 -->
		<c:choose>
			<c:when test="${not empty sessionScope.customInfo}">
				<!-- 로그인된 사용자 - 후기 작성 폼 표시 -->
				<div class="card">
					<h3>후기 작성하기</h3>
					<form action="<%=cp%>/reviewWrite" method="post">
						<div class="form-group">
							<label for="title">후기 제목:</label>
							<input type="text" name="title" id="title" placeholder="제목을 작성해주세요" required />
						</div>
						<div class="form-group">
							<label for="content">후기 내용:</label>
							<textarea name="content" id="content" placeholder="후기를 작성해주세요" required></textarea>
						</div>
						<div class="form-group">
							<label for="writer">작성자:</label>
							<input type="text" name="writer" id="writer" value="${sessionScope.customInfo.name}" readonly style="background-color: #f8f9fa;">
						</div>
						
						<div class="form-submit">
							<button type="submit" class="btnfoot">작성완료</button>
						</div>
					</form>
				</div>
			</c:when>
			<c:otherwise>
				<!-- 로그인하지 않은 사용자 - 안내 메시지 -->
				<div class="card">
					<h3>후기 작성하기</h3>
					<div style="text-align: center; padding: 40px 20px;">
						<p style="font-size: 16px; color: #666; margin-bottom: 20px;">
							후기를 작성하려면 로그인이 필요합니다.
						</p>
						<a href="<%=cp%>/login.do" class="btn">로그인하기</a>
					</div>
				</div>
			</c:otherwise>
		</c:choose>



</body>
</html>