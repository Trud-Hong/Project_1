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
<title>게시글 작성</title>

<link rel="stylesheet" type="text/css" href="<%=cp%>/resources/css/review.css">

</head>
<body>

	<header>
	    <div class="left">
			<a href="<%=cp%>/main.do">MyCondition</a>
		</div>
		<div style="font-size: 20px;">
        	커 뮤 니 티
        </div>
        <div></div>
	</header>

	<div class="container">
		<div class="card">
			<h3>게시글 작성하기</h3>
			<form action="<%=cp%>/communityDelete.do" method="post" id="communityDeleteForm">
				<input type="hidden" name="board_id" value="${dto.board_id}" />
				<input type="hidden" name="pageNum" value="${pageNum}" />
				<div class="form-group">
					<label for="title">게시글 제목:</label>
					<input type="text" name="title" id="title" value="${dto.title}" readonly />
				</div>
				<div class="form-group">
					<label for="content">게시글 내용:</label>
					<textarea name="content" id="content" readonly >${dto.content}</textarea>
				</div>
				<div class="form-group">
					<label for="writer">작성자:</label>
					<input type="text" name="writer" id="writer" value="${dto.member_name}" readonly style="background-color: #f8f9fa;">
				</div>
				
				<c:choose>
					<c:when test="${sessionScope.customInfo.name == dto.member_name}">
						
						<div class="form-submit">
							<button type="button" class="btnfoot danger" onclick="isDelete()">삭제</button>
							<button type="button" class="btnfoot" 
							    onclick="location.href='/heal/communityEdit.do?board_id=${dto.board_id}&pageNum=${pageNum}&category=${category}&searchKey=${searchKey}&searchValue=${searchValue}'">
							    수정
							</button>
							<button type="button" class="btnfoot" 
							    onclick="location.href='/heal/community.do?pageNum=${pageNum}&category=${category}&searchKey=${searchKey}&searchValue=${searchValue}'">
							    목록
							</button>
						</div>
					</c:when>
					<c:when test="${not empty sessionScope.customInfo }">
						<div class="form-submit">
							<button type="button" class="btnfoot" 
							    onclick="location.href='/heal/community.do?pageNum=${pageNum}&category=${category}&searchKey=${searchKey}&searchValue=${searchValue}'">
							    목록
							</button>
						</div>
					</c:when>
					<c:otherwise>
						<div class="form-submit">
							<button type="button" class="btnfoot" 
							    onclick="location.href='/heal/community.do?pageNum=${pageNum}&category=${category}&searchKey=${searchKey}&searchValue=${searchValue}'">
							    목록
							</button>
						</div>
					</c:otherwise>
				</c:choose>
				
			</form>
		</div>
	</div>

</body>
<script src="<%=cp%>/resources/js/community.js"></script>
</html>