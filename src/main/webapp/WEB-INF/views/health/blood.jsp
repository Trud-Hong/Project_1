<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%

    request.setCharacterEncoding("UTF-8");
    String cp = request.getContextPath();

    // 에러 메시지 처리
    String message = (String) request.getAttribute("message");

%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>혈압 기록</title>

<link rel="stylesheet" type="text/css" href="<%=cp%>/resources/css/blood.css"/>
<script>
    <% if (message != null) { %>
        alert("<%=message%>");
    <% } %>

    // 폼 유효성 검사
    function validateForm() {
        const systolic = document.getElementsByName('bpHigh')[0].value;
        const diastolic = document.getElementsByName('bpLow')[0].value;
        const measureDate = document.getElementsByName('log_Date')[0].value;

        if (!systolic || !diastolic || !measureDate) {
            alert('필수 항목을 모두 입력해주세요.');
            return false;
        }

        const sys = parseInt(systolic);
        const dia = parseInt(diastolic);

        if (sys < 50 || sys > 250) {
            alert('수축기 혈압은 50-250 사이의 값을 입력해주세요.');
            return false;
        }

        if (dia < 30 || dia > 150) {
            alert('이완기 혈압은 30-150 사이의 값을 입력해주세요.');
            return false;
        }

        if (sys <= dia) {
            alert('수축기 혈압은 이완기 혈압보다 높아야 합니다.');
            return false;
        }

        return confirm('혈압 기록을 저장하시겠습니까?');
    }
</script>


</head>
<body>


<header>
	<div class="left">
		<a href="<%=cp%>/main.do">MyCondition</a>
	</div>
	<div style="font-size: 20px;">
       	혈압 기록하기
    </div>
    <div></div>	
</header>


<div class="container">

    <!-- 혈압 기록 -->
    <div class="card">
        <h3>🩺 혈압 기록</h3>

        <!-- ✅ 컨트롤러와 맞추기: /blood_ok.do -->
        <form action="/heal/blood_ok" method="post" onsubmit="return validateForm()">
            <div class="info-row">
                <label>수축기(mmHg):</label>
                <input type="number" name="bpHigh"
                       id="bpHigh"
                       min="50" max="250" required>
                <small style="color: #666; margin-left: 10px;">정상: 120 미만</small>
            </div>

            <div class="info-row">
                <label>이완기(mmHg):</label>
                <input type="number" name="bpLow"
                       id="bpLow"
                       min="30" max="150" required>
                       
                <small style="color: #666; margin-left: 10px;">정상: 80 미만</small>
            </div>

            <div class="info-row">
                <label>측정 날짜:</label>
                <input type="date" name="log_Date" id="log_Date"
	       		 value="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>"
	       		 max="<%= new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()) %>">       
            </div>

            <%-- <div style="margin-top: 20px; text-align: center;">
                <button type="submit" class="btn">저장하기</button>
                <button type="button" class="btn danger" onclick="location.href='<%=cp%>/main.do'">뒤로가기</button>
            </div> --%>
        

        <!-- 혈압 기준 정보 (기존 테이블 그대로 유지) -->
        <div style="margin-top: 30px; padding: 15px; background-color: #f8f9fa; border-radius: 8px; border-left: 4px solid #007bff;">
            <h4 style="margin: 0 0 10px 0; color: #333;">혈압 기준</h4>
            <table style="width: 100%; font-size: 14px; border-collapse: collapse;">
                <tr style="background-color: #e9ecef;">
                    <th>구분</th><th>수축기</th><th>이완기</th>
                </tr>
                <tr><td style="color:#28a745;">정상</td><td>120 미만</td><td>80 미만</td></tr>
                <tr><td style="color:#ffc107;">주의</td><td>120-129</td><td>80 미만</td></tr>
                <tr><td style="color:#fd7e14;">고혈압 전단계</td><td>130-139</td><td>80-89</td></tr>
                <tr><td style="color:#dc3545;">1단계 고혈압</td><td>140-159</td><td>90-99</td></tr>
                <tr><td style="color:#dc3545;font-weight:bold;">2단계 고혈압</td><td>160 이상</td><td>100 이상</td></tr>
            </table>
        </div>
        	<div style="margin-top: 20px; text-align: center;">
                <button type="submit" class="btn">저장하기</button>
                <button type="button" class="btn danger" onclick="location.href='<%=cp%>/main.do'">뒤로가기</button>
            </div>
		</form>
    </div>


</div>


</body>

</html>

