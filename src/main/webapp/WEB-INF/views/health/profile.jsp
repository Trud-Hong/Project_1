<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>체중 변화</title>

<link rel="stylesheet" type="text/css" href="<%=cp%>/resources/css/profile.css"/>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script src="<%=cp%>/resources/js/profile.js"></script>
</head>
<body>

	<header>
	    <div class="left">
			<a href="<%=cp%>/main.do">MyCondition</a>
		</div>
		<div style="font-size: 20px;">
        	체 중 변 화
        </div>
        <div></div>
	</header>
	
	<form method="post" action="profileUpdate.do" name="profileForm" onsubmit="return validateForm()">
	<div class="container">
		<div class="card" style="margin-top:20px;">
			<h3>📊 체중 변화 그래프</h3>
			<div id="chart_div" style="width: 100%; height: 300px;">
				<!-- 리스트에 데이터가 있으면 차트 표시 -->
			<c:choose>
		        <c:when test="${lists != null && !lists.isEmpty()}">
				<script type="text/javascript">
				  google.charts.load('current', {'packages':['corechart']});
				  google.charts.setOnLoadCallback(drawChart);
				
				  function drawChart() {
				    var data = google.visualization.arrayToDataTable([
				      ['날짜', '체중(Kg)', 'BMI(kg*㎡)'],
				      <c:forEach var="row" items="${lists}" varStatus="st">
				        ['${row.record_date}', ${row.weight}, ${row.bmi}]
				        <c:if test="${!st.last}">,</c:if>
				      </c:forEach>
				    ]);
				
				    var options = {
				      title: '체중 & BMI 변화',
				      hAxis: { title: '날짜' },
				      vAxis: { title: '체중(kg) & BMI(kg*㎡)' },
				      colors: ['#1E90FF', '#32CD32'],
				      legend: { position: 'top' },
				      pointSize: 5
				    };
				
				    var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
				    chart.draw(data, options);
				  }
				</script>
				</c:when>
				<c:otherwise>
				    <div class="no-data-message">
				        <b>기록된 체중이 없습니다</b>
				    </div>
				</c:otherwise>
			</c:choose>
			</div>
			
		</div>
		
		
		<!-- 내 정보 수정 -->
		<div class="card">
			<h3>🧾 오늘의 체중 기록</h3>
			
			<input type="hidden" name="member_no" value="${customInfo.member_no }">
			
			<div class="info-row">
				<label>체중:</label>
				<div style="display: flex; align-items: center; gap: 5px;">
					<input type="text" name="weight" value="${customInfo.weight }" min="3" id="weightInput"
						style="flex: 1;"> <span>kg</span>
				</div>
			</div>
			<div class="button-wrapper">
			<button type="submit" class="btn">저장하기</button>
			<button type="button" class="btn danger" onclick="location.href='/heal/main'">뒤로가기</button>
			</div>
		</div>
		
	</div>
	</form>
</body>
</html>

