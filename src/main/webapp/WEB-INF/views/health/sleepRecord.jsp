<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.*, java.text.*" %>
<%
    request.setCharacterEncoding("UTF-8");
    String cp = request.getContextPath();

    // 현재 날짜와 시간을 datetime-local 형식으로 포맷
    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    String now = sdf.format(new java.util.Date());

    // 에러 메시지 처리
    String message = (String) request.getAttribute("message");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>수면패턴 기록</title>

<link rel="stylesheet" type="text/css" href="<%=cp%>/resources/css/sleep.css"/>
<script>
    <% if (message != null) { %>
        alert("<%=message%>");
    <% } %>

    function validateForm() {
        const sleepHours = document.getElementsByName('sleepHours')[0].value;
        const bedtime = document.getElementsByName('bedtimeString')[0].value;

        if (!sleepHours || !bedtime) {
            alert('필수 항목을 모두 입력해주세요.');
            return false;
        }

        const hours = parseFloat(sleepHours);

        if (hours < 1 || hours > 12) {
            alert('수면시간은 1-12시간 사이의 값을 입력해주세요.');
            return false;
        }

        return confirm('수면패턴 기록을 저장하시겠습니까?');
    }
</script>

</head>
<body>

<header>
	<div class="left">
		<a href="<%=cp%>/main.do">MyCondition</a>
	</div>
	<div style="font-size: 20px;">
       	수면패턴 기록
       </div>
       <div style="position: absolute; left: 95%; transform: translateX(-50%); 
       	display: flex; gap: 15px;" align="right">
       </div>
</header>

<div class="container">

    <div class="card">
        <h3>🌙 수면패턴 기록</h3>

        <form action="<%=cp%>/sleep_ok.do" method="post" onsubmit="return validateForm()">
            <div class="info-row">
                <label>수면시간(시간):</label>
                <input type="number" name="sleepHours" step="0.5"
                       value="<c:out value='${sleep_dto.sleepHours}'/>"
                       min="1" max="12" required>
                <small style="color: #666; margin-left: 10px;">권장: 7-9시간</small>
            </div>

            <div class="info-row">
			    <label>취침 날짜 및 시간:</label>
			    <input type="datetime-local" 
			           name="bedtimeString"
			           value="<%= now %>"   
			           max="<%= now %>"     
			           required>
			    <small style="color: #666; margin-left: 10px;"></small>
			</div>

            

        <div style="margin-top: 30px; padding: 15px; background-color: #f8f9fa; border-radius: 8px; border-left: 4px solid #007bff;">
            <h4 style="margin: 0 0 10px 0; color: #333;">수면 가이드라인</h4>
            <table style="width: 100%; font-size: 14px; border-collapse: collapse;">
                <tr style="background-color: #e9ecef;">
                    <th>연령대</th><th>권장 수면시간</th><th>권장 취침시간</th>
                </tr>
                <tr><td style="color:#28a745;">성인 (18-64세)</td><td>7-9시간</td><td>22:00-24:00</td></tr>
                <tr><td style="color:#17a2b8;">노인 (65세 이상)</td><td>7-8시간</td><td>21:00-23:00</td></tr>
                <tr><td style="color:#ffc107;">청소년 (14-17세)</td><td>8-10시간</td><td>21:00-23:00</td></tr>
            </table>
            
            <div style="margin-top: 15px; font-size: 13px; color: #666;">
                <strong>수면의 질 개선 팁:</strong><br>
                • 규칙적인 수면 스케줄 유지 • 취침 2시간 전 카페인 금지<br>
                • 적절한 실내 온도 (18-22°C) • 취침 1시간 전 전자기기 사용 금지<br>
                • 규칙적인 운동 (취침 4시간 전까지) • 편안한 수면 환경 조성
            </div>
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