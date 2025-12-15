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

<title>혈 당 기 록</title>

<link rel="stylesheet" type="text/css" href="<%=cp%>/resources/css/bg.css"/>
<script src="<%=cp%>/resources/js/bg.js"></script>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>

</head>
<body>

<header>
	<div class="left">
		<a href="<%=cp%>/main.do">MyCondition</a>
	</div>
	<div style="font-size: 20px;">
       	혈당 기록하기
    </div>
    <div></div>	
</header>
<form action="/heal/bg_ok" method="post" name="myForm" onsubmit="fillEmptyWithZero()">
<div class="container">

	<div class="card" style="margin-top:20px;">
		<h3>📊 혈당 추이 그래프</h3>
		
		<!-- 리스트가 비어있으면 메시지 -->
        <div id="curve_chart" style="width: 100%; height: 300px; text-align:center; line-height:300px; color:red; font-size:18px;">
        <c:choose>
            <c:when test="${lists == null || lists.isEmpty()}">
                <b>혈당 기록이 없습니다.</b>
            </c:when>
        </c:choose>
    	</div>
        
        <!-- 리스트에 데이터가 있으면 차트 표시 -->
        <c:if test="${lists != null && !lists.isEmpty()}">
		    <script type="text/javascript">
		      // 1. Google Charts 로드
		      google.charts.load('current', {'packages':['corechart']});
		      google.charts.setOnLoadCallback(drawChart);
		    
		      // 2. 차트 그리기 함수
		      function drawChart() {
		        var data = google.visualization.arrayToDataTable([
		          ['날짜', 
		              '공복 혈당(mg/dL)', 
		              '식후 혈당(mg/dL)', 
		              '잠자기 전 혈당(mg/dL)'],
		          <c:forEach var ="row" items="${lists}" varStatus="st">
		            [new Date(${row.log_Date.time}), ${row.fasting}, ${row.afterMeal}, ${row.beforeBed}]
		            <c:if test="${!st.last}">,</c:if>
		          </c:forEach>
		        ]);
		
		        var ticks = [
		          <c:forEach var="row" items="${lists}" varStatus="st">
		            new Date(${row.log_Date.time})
		            <c:if test="${!st.last}">,</c:if>
		          </c:forEach>
		        ];
		
		        var options = {
		          hAxis: {
		            title: '기록한 날짜',
		            titleTextStyle: { fontSize: 16 },
		            format: 'yyyy-MM-dd',
		            ticks: ticks  
		          },
		          vAxis: {
		            title: '혈당 수치',
		            titleTextStyle: { fontSize: 16 }
		          },
		          allowHtml: true,
		          colors: ['#1E90FF', '#FFA500', '#32CD32'],
		          legend: { position: 'top' },
		          pointSize: 5
		        };
		    
		        var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));
		        chart.draw(data, options);
		      }
		    </script>
		</c:if>
			
	</div>
	
	<div class="card">
	    <label for="log_Date" class="input-label">
	        <h3>기록 날짜 선택하기</h3>
	    </label><br>
	    <div class="input-wrap">
	        <input type="date" id="log_Date" name="log_Date"
	       	value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>"
	       	max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">
	    </div>
	</div>
	
	<div class="glucose-inputs">
	    <div class="card">
	    	<label for="fasting" class="input-label">
	        	<h3>공복 혈당 입력하기</h3>
	        </label><br>
	        <div class="input-wrap">
	      		<input type="number" min=50 max=500 id="fasting" name="fasting" required> mg/dL
	    	</div>
	    </div>

	    <div class="card">
	        <label for="afterMeal" class="input-label">
	        	<h3>식후 2시간 혈당 입력하기</h3>
	        </label><br>
	        <div class="input-wrap">
	      		<input type="number" min=50 max=500 id="afterMeal" name="afterMeal" required> mg/dL
	    	</div>
	    </div>
	    
	    <div class="card">
	        <label for="beforeBed" class="input-label">
	        	<h3>잠자기 전 혈당 입력하기</h3>
	        </label><br>
	        <div class="input-wrap">
	      		<input type="number" min=50 max=500 id="beforeBed" name="beforeBed" required> mg/dL
	    	</div>
	    </div>
	</div>
	

    <div class="card" style="text-align:center;">
        <button type="submit" class="btn">저장하기</button>
		<button type="button" class="btn danger" onclick="location.href='/heal/main'">뒤로가기</button>
    </div>

</div>
</form>





</body>
</html>


