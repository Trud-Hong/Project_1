<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%	
	String cp = request.getContextPath();
	request.setCharacterEncoding("UTF-8");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시글 수정</title>

<link rel="stylesheet" type="text/css" href="<%=cp%>/resources/css/review.css">

<script type="text/javascript">
function goBack() {
    location.href = "<%=cp%>/community.do";
}
	
function validateForm() {
	var content = document.getElementById('content').value.trim();
	if (content === '') {
		alert('후기 내용을 입력해주세요.');
		return false;
	}
	return true;
}

function submitForm() {
	if (validateForm()) {
		document.getElementById('reviewForm').submit();
	}
}
</script>


</head>
<body>

	<header>
	    <div class="left">
			<a href="<%=cp%>/main.do">MyCondition</a>
		</div>
		<div style="font-size: 20px;">
        	게 시 글 수 정
        </div>
        <div></div>
	</header>

	<div class="container">
		<div class="card">
			<h3>게시글 수정하기</h3>
			<form action="<%=cp%>/communityEdit_ok" method="post">
				<input type="hidden" name="board_id" value="${dto.board_id}" />
				<input type="hidden" name="pageNum" value="${pageNum}" />
				<div class="form-group">
					<label for="title">게시글 제목:</label>
					<input type="text" name="title" id="title" value="${dto.title}" required />
				</div>
				<div class="form-group">
					<label for="content">게시글 내용:</label>
					<textarea name="content" id="content" required >${dto.content}</textarea>
				</div>
				<div class="form-group">
					<label for="writer">작성자:</label>
					<input type="text" name="writer" id="writer" value="${dto.member_name}" readonly style="background-color: #f8f9fa;">
				</div>
		
				<div class="form-submit">
					<button type="submit" class="btnfoot danger">작성 완료</button>
					<button type="button" class="btnfoot" 
					    onclick="location.href='/heal/communityArticle.do?board_id=${dto.board_id}&pageNum=${pageNum}&category=${category}&searchKey=${searchKey}&searchValue=${searchValue}'">
					    뒤로가기
					</button>
				</div>
				
				<input type="hidden" name="category" value="${category}" />
				<input type="hidden" name="searchKey" value="${searchKey}" />
				<input type="hidden" name="searchValue" value="${searchValue}" />
				
			</form>
		</div>
	</div>

</body>
</html>