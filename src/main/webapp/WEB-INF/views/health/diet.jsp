<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주간 식단 추천</title>

<link rel="stylesheet" type="text/css" href="<%=cp%>/resources/css/diet.css">
<link rel="stylesheet" type="text/css" href="<%=cp%>/resources/css/main.css">

<script>

    var memberId = "<c:out value='${sessionScope.customInfo.member_no}' />";

</script>


<script src="<%=cp%>/resources/js/diet.js"></script>

<script>
    console.log("세션 CustomInfo:", {

        member_id: "${sessionScope.customInfo.member_no}",

        name: "${sessionScope.customInfo.name}"
    });
</script>

</head>
<body>

<!-- 상단 -->
    <c:choose>
	<c:when test="${empty sessionScope.customInfo.member_id }">
    <header>
        <div style="flex:1; text-align:left;"><a href="<%=cp%>/main.do">MyCondition</a></div>

        <div class="center-menu">
        	<a href="myPage.do" class="btn2">마이페이지</a>
        	<a href="review.do" class="btn2">이용후기</a>
        	<a href="commu.do" class="btn2">커뮤니티</a>
        </div>
        <div style="flex:1; text-align:right;">
			<a href="<%=cp %>/join" class="btn2">회원가입</a>|
			<a href="<%=cp %>/login" class="btn2">로그인</a>
		</div>

    </header>
    </c:when>
    <c:otherwise>
    <!-- 로그인 성공시 상단 -->
    <header>
        <div style="flex:1; text-align:left;"><a href="<%=cp%>/main.do">MyCondition</a></div>
        <div class="center-menu">
        	<a href="myPage.do" class="btn2">마이페이지</a>
        	<a href="review.do" class="btn2">이용후기</a>
        	<a href="commu.do" class="btn2">커뮤니티</a>
        </div>
        <div style="font-size: 10pt; flex:1; text-align:right;">
			<b style="color: yellow;">${sessionScope.customInfo.name }</b>님 반갑습니다.
			<a href="logout.do" class="btn2">로그아웃</a>
		</div>
    </header>
    </c:otherwise>
    </c:choose>

<h2>&nbsp;&nbsp;&nbsp;주간 식단 추천</h2>
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;달력에서 날짜를 클릭하면 해당 주차 식단이 표시됩니다.</p>


  <!-- 좌우 2열 레이아웃: 달력 + 데이터 영역 -->
<div style="display:flex; gap:20px; padding:20px;">
  
  <!-- 달력 영역 -->
  <div class="card" style="flex:1;">
    <h3 id="calendarTitle"></h3>
    <div style="display:flex; justify-content:space-between; align-items:center; margin-bottom:10px;">
      <button id="prevMonth" class="btn2">◀</button>
      <div id="currentMonth" style="font-weight:bold; font-size: 20pt"></div>
      <button id="nextMonth" class="btn2">▶</button>
    </div>
    <div id="calendar"></div>
  </div>

  <!-- 데이터 표시 영역 -->
  <div class="card" style="flex:1; padding:10px; overflow-y:auto; max-height:600px;">

    <div id="weeklyDiet" style="min-height:500px; padding:10px;">

      
    </div>
  </div>

</div>


</body>
</html>
