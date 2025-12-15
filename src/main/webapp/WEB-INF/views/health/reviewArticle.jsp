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
		<div class="card">
			<form action="<%=cp%>/reviewDelete.do" method="post" id="reviewDeleteForm">
				<input type="hidden" name="review_id" value="${dto.review_id}" />
				<input type="hidden" name="pageNum" value="${pageNum}" />
				<div class="form-group">
					<label for="title">후기 제목:</label>
					<input type="text" name="title" id="title" value="${dto.title}" readonly />
				</div>
				<div class="form-group">
					<label for="content">후기 내용:</label>
					<textarea name="content" id="content" readonly >${dto.content}</textarea>
				</div>
				<div class="form-group">
					<label for="writer">작성자:</label>
					<input type="text" name="writer" id="writer" value="${dto.member_name}" readonly style="background-color: #f8f9fa;">
				</div>
				
				<div class="form-submit">
				<c:choose>
					<c:when test="${sessionScope.customInfo.name == dto.member_name}">
						<button type="button" class="btnfoot danger" onclick="isDelete()">삭제</button>
						<button type="button" class="btnfoot" 
						    onclick="location.href='/heal/reviewEdit.do?review_id=${dto.review_id}&pageNum=${pageNum}&searchKey=${searchKey}&searchValue=${searchValue}'">
						    수정
						</button>
						<button type="button" class="btnfoot" 
						    onclick="location.href='/heal/review.do?pageNum=${pageNum}&searchKey=${searchKey}&searchValue=${searchValue}'">
						    목록
						</button>
					</c:when>
					<c:when test="${not empty sessionScope.customInfo }">
						<button type="button" class="btnfoot" 
						    onclick="location.href='/heal/review.do?pageNum=${pageNum}&searchKey=${searchKey}&searchValue=${searchValue}'">
						    목록
						</button>
					</c:when>
					<c:otherwise>
						<button type="button" class="btnfoot" 
						    onclick="location.href='/heal/review.do?pageNum=${pageNum}&searchKey=${searchKey}&searchValue=${searchValue}'">
						    목록
						</button>
					</c:otherwise>
				</c:choose>
				</div>
			</form>
		</div>
	</div>

</body>
<script src="<%=cp%>/resources/js/review.js"></script>
</html>