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
<script>var CONTEXT_PATH = "<%=cp%>";</script>

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
		
		<!-- 검색창 -->
		<div id="searchFormContainer">
		    <form action="" name="searchForm" method="get">
		        <select name="searchKey" class="selectFiled">
		            <option value="title" ${param.searchKey == 'title' ? 'selected' : ''}>제목</option>
		            <option value="member_name" ${param.searchKey == 'member_name' ? 'selected' : ''}>이름</option>
		            <option value="content" ${param.searchKey == 'content' ? 'selected' : ''}>내용</option>
		        </select>
		        <input type="text" name="searchValue" class="textFilde" value="${param.searchValue}">
		        <input type="button" value="검 색" class="btn2" onclick="sendIt();">
		    </form>
		</div>

		<!-- 후기 통계 -->
		<div class="stats">
			<div class="stat-item">
				<div class="stat-label">총 후기</div>
				<div class="stat-number">${dataCount}</div>
			</div>
			<c:choose>
				<c:when test="${not empty sessionScope.customInfo}">
						<button type="button" class="btn" onclick="location.href='/heal/reviewWritePage'">작성</button>
				</c:when>
				<c:otherwise>
						<button type="button" class="btn" onclick="location.href='/heal/login'">작성</button>
				</c:otherwise>
			</c:choose>
					
<%-- 		<div class="stat-item">
=======
				<div class="stat-number">${totalReviews}</div>
				<div class="stat-label">총 후기</div>
			</div>
			<div class="stat-item">
>>>>>>> test
				<div class="stat-number">${avgRating}</div>
				<div class="stat-label">평균 별점</div>
			</div>
			<div class="stat-item">
				<div class="stat-number">${myReviews}</div>
				<div class="stat-label">내 후기</div>
<<<<<<< HEAD
			</div> --%>
		</div>

		<c:choose>
		    <c:when test="${not empty lists}">
		        <div class="reviews-container">
		            <c:forEach var="dto" items="${lists}">
		                <div class="review-card">
		                    <div class="review-header">
		                        <span class="review-title">
		                            <a href="${articleUrl}&review_id=${dto.review_id}">${dto.title}</a>
		                        </span>
		                        <span class="review-date">${dto.reg_date}</span>
		                    </div>
		                    <div class="review-author">
		                        작성자: ${dto.member_name}
		                    </div>
		                    <div class="review-content">
		                        ${dto.content}
		                    </div>
		                    <div class="review-footer">
		                        조회수: ${dto.hit_count}
		                    </div>
		                </div>
		            </c:forEach>
		        </div>
		    </c:when>
		    <c:otherwise>
		        <div class="no-reviews">
		            아직 등록된 후기가 없습니다.<br> 첫 번째 후기를 작성해보세요!
		        </div>
		    </c:otherwise>
		</c:choose>
	
		<div class="footer">
			<p>
				<c:if test="${dataCount!=0 }">
					${pageIndexList }
				</c:if>
				<c:if test="${dataCount == 0 }">
					등록된 게시물이 없습니다.
				</c:if>
			</p>
		</div>
	

	</div>

</body>
</html>