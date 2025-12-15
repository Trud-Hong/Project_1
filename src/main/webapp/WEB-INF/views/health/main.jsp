<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.*, java.text.*" %>
<%

	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	String today = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
	
	Calendar cal = Calendar.getInstance();
    int year = cal.get(Calendar.YEAR);
    int month = cal.get(Calendar.MONTH); // 0~11
    int selectedDay = 0;

    String selDate = request.getParameter("date"); // YYYY-MM-DD
    if(selDate != null){
        String[] parts = selDate.split("-");
        year = Integer.parseInt(parts[0]);
        month = Integer.parseInt(parts[1]) - 1;
        selectedDay = Integer.parseInt(parts[2]);
    }

    cal.set(year, month, 1);
    int firstDay = cal.get(Calendar.DAY_OF_WEEK) - 1; // 0=일
    int lastDate = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    
%>

<!-- 오늘 날짜 선언 -->
<c:set var="today" value="<%= new java.util.Date() %>" />
<fmt:formatDate value="${today}" pattern="yyyy-MM-dd" var="todayStr"/>

<!-- 혈압·혈당 기록 날짜 포맷 -->
<c:if test="${not empty dtoBp}">
    <fmt:formatDate value="${dtoBp.log_Date}" pattern="yyyy-MM-dd" var="bpLogDate"/>
</c:if>
<c:if test="${not empty dto}">
    <fmt:formatDate value="${dto.log_Date}" pattern="yyyy-MM-dd" var="bgLogDate"/>
</c:if>
        
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>건강관리 메인</title>

<link rel="stylesheet" type="text/css" href="<%=cp%>/resources/css/main.css">


<script>
    var memberId = "<c:out value='${sessionScope.customInfo.member_id}' />";
</script>

<script src="/heal/resources/js/main.js"></script>

<script>
    console.log("세션 CustomInfo:", {
        member_id: "${sessionScope.customInfo.member_id}",
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
        	<a href="review.do" class="btn2">이용후기</a>
        	<a href="community.do" class="btn2">커뮤니티</a>
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
        	<a href="community.do" class="btn2">커뮤니티</a>
        </div>
        <div style="font-size: 10pt; flex:1; text-align:right;">
			<b style="color: yellow;">${sessionScope.customInfo.name }</b>님 반갑습니다.
			<a href="logout.do" class="btn2">로그아웃</a>
		</div>
    </header>
    </c:otherwise>
    </c:choose>
    

    <!-- 메인 카드 영역 -->
    <div class="container">
		
		<!-- 식단 추천 -->
        <div class="card">
            <h3>🥗 식단 추천</h3>
			<c:choose>
				<c:when test="${not empty sessionScope.customInfo.member_id }">
		           <p>오늘의 추천: 
			        <c:choose>
			            <c:when test="${customInfo.goal == '다이어트'}">
			                저칼로리/다이어트
			            </c:when>
			            <c:when test="${customInfo.goal == '건강유지'}">
			                건강식
			            </c:when>
			            <c:when test="${customInfo.goal == '벌크업'}">
			                고단백/고칼로리
			            </c:when>
			            <c:otherwise>
			                목표를 설정해 주세요
			            </c:otherwise>
			        </c:choose>
		    		</p>
					<a href="diet.do" class="btn-card">식단보기</a>
				</c:when>
				<c:otherwise>
					로그인해 주시기 바랍니다.<br/><br/>
					<a href="javascript:void(0);" class="btn-card"
						onclick="loginCheck();">식단보기</a>
				</c:otherwise>
			</c:choose>
        </div>


		<!-- 식습관 -->
        <div class="card">
            <h3>🍚 식습관</h3>
            
			<c:choose>
				<c:when test="${not empty sessionScope.customInfo.member_id }">
					<c:choose>
						<c:when test="${not empty dtoHb and recordDate eq todayStr}">
				            <p>일일 식사 횟수: ${habitNum }회</p>
				            <p>시간: 
				            <br/>
				            <c:choose>
				            	<c:when test="${dtoHb.breakfast_time eq null }">
				            		--:--
				            	</c:when>
				            	<c:otherwise>
				            		아침 ${breakfastTimeStr }
				            	</c:otherwise>
				            </c:choose>
				            |
				            <c:choose>
				            	<c:when test="${dtoHb.lunch_time eq null }">
				            		--:--
				            	</c:when>
				            	<c:otherwise>
				            		점심 ${lunchTimeStr }
				            	</c:otherwise>
				            </c:choose>
				            |
				            <c:choose>
				            	<c:when test="${dtoHb.dinner_time eq null }">
				            		--:--
				            	</c:when>
				            	<c:otherwise>
				            		저녁 ${dinnerTimeStr }
				            	</c:otherwise>
				            </c:choose>
				            </p>
				        </c:when>
				        <c:otherwise>
				            <p>오늘의 식습관 기록이 없습니다.</p>
				        </c:otherwise>
				    </c:choose>
					<a href="habit.do" class="btn-card">식사기록</a>
				</c:when>
				<c:otherwise>
					로그인해 주시기 바랍니다.<br/><br/>
					<a href="javascript:void(0);" class="btn-card"
						onclick="loginCheck();">식사기록</a>
				</c:otherwise>
			</c:choose>
        </div>


		<!-- 운동 카드 부분 -->
		<div class="card">
			<h3>🏃 운동</h3>
				<c:choose>
					<c:when test="${not empty sessionScope.customInfo.member_id }">
						<p>추천: 
							<c:choose>
								<c:when test="${customInfo.goal == '다이어트'}">
									유산소
								</c:when>
								<c:when test="${customInfo.goal == '벌크업'}">
									근력
								</c:when>
								<c:when test="${customInfo.goal == '건강유지'}">
									일상
								</c:when>
								<c:otherwise>
									유산소
								</c:otherwise>
					        </c:choose>
						</p>
						<a href="exercise.do?goal=${customInfo.goal}" class="btn-card">운동기록</a>
					</c:when>
					<c:otherwise>
						로그인해 주시기 바랍니다.<br/><br/>
						<a href="javascript:void(0);" class="btn-card" onclick="loginCheck();">운동기록</a>
					</c:otherwise>
				</c:choose>
		</div>

		
		<!-- 혈압 카드 부분 -->
		<div class="card">
		    <h3>❤️ 혈압</h3>
			
		    
		    <c:choose>
				<c:when test="${not empty sessionScope.customInfo.member_id }">
					
					<!-- 범례 표시 -->
					<div class="blood-glucose-legend">
					    <div class="legend-item"><span class="color-normal"></span>정상</div>
					    <div class="legend-item"><span class="color-warning"></span>주의</div>
					    <div class="legend-item"><span class="color-danger"></span>위험</div>
					</div>
					
				    <c:choose>
				         <c:when test="${not empty dtoBp and bpLogDate eq todayStr}">
				            
				            <p>최근 결과: 
				            	<span style="color: ${dtoBp.bpLevel == '정상' ? '#32CD32' 
				            	: (dtoBp.bpLevel == '주의' || dtoBp.bpLevel == '고혈압 전단계' ? '#FFA500' : '#FF6347')};
				            	font-weight:bold; font-size:1.2em;">
				            	${dtoBp.bpHigh}/${dtoBp.bpLow}</span> mmHg
				            	<br/>
			            	</p>
				        </c:when>
				        <c:otherwise>
				            <p>오늘의 혈압 기록이 없습니다.</p>
				        </c:otherwise>
				    </c:choose>
					<a href="blood.do" class="btn-card">혈압기록</a>
				</c:when>
				<c:otherwise>
					로그인해 주시기 바랍니다.<br/><br/>
					<a href="javascript:void(0);" class="btn-card"
						onclick="loginCheck();">혈압기록</a>
				</c:otherwise>
			</c:choose>
		</div>


		<!-- 체중 변화 카드 부분 -->
		<div class="card">

            <h3>📉 체중 변화</h3>

            <c:choose>
        <c:when test="${not empty sessionScope.customInfo.member_id }">
            <!-- 키(m) -->
            <c:set var="heightM" value="${customInfo.height / 100}" />

            <!-- 정상 BMI 기준 체중 -->
            <c:set var="normalMinWeight" value="${18.5 * heightM * heightM}" />
            <c:set var="normalMaxWeight" value="${23 * heightM * heightM}" />
			<!-- 정상 체중 평균값 계산 -->
			<c:set var="standardWeight" value="${(normalMinWeight + normalMaxWeight) / 2.0}" />
            <!-- BMI 클래스 -->
            <c:set var="bmiClass" value="
                ${customInfo.bmi lt 18.5 ? 'bmi-low' :
                  customInfo.bmi lt 23 ? 'bmi-normal' :
                  customInfo.bmi lt 25 ? 'bmi-risk' :
                  customInfo.bmi lt 30 ? 'bmi-mild' :
                  customInfo.bmi lt 35 ? 'bmi-moderate' : 'bmi-severe'}"/>

            <!-- BMI 텍스트 -->
            <c:set var="bmiText" value="
                ${customInfo.bmi lt 18.5 ? '저체중' :
                  customInfo.bmi lt 23 ? '정상체중' :
                  customInfo.bmi lt 25 ? '과체중(위험체중)' :
                  customInfo.bmi lt 30 ? '경도 비만' :
                  customInfo.bmi lt 35 ? '중등도 비만' : '고도 비만'}"/>

            <!-- 화면 출력 -->
            <p>
                현재 BMI: <span class="bmi-value bmi-black">${customInfo.bmi}</span>
                <span class="bmi-badge ${bmiClass}">${bmiText}</span>
            </p>
            <p>
                현재 체중: <span class="bmi-value bmi-black">${customInfo.weight}kg</span>
            </p>
			<p>
				&nbsp;&nbsp;➜표준 체중 : 
				<span class="standard-weight">
			        <fmt:formatNumber value="${normalMinWeight}" maxFractionDigits="1"/>kg ~
			        <fmt:formatNumber value="${normalMaxWeight}" maxFractionDigits="1"/>kg
			    </span>
			</p>
            <p>
                &nbsp;&nbsp;➜
                <c:choose>
			    <c:when test="${customInfo.weight lt normalMinWeight}">
			        표준까지 
			        <span class="weight-value weight-gain">
			            최소&nbsp;+&nbsp;<fmt:formatNumber value="${normalMinWeight - customInfo.weight}" maxFractionDigits="1"/>kg
			        </span> 
			    </c:when>
			    <c:when test="${customInfo.weight gt normalMaxWeight}">
			        표준까지 
			        <span class="weight-value weight-loss">
			            최소&nbsp;-&nbsp;<fmt:formatNumber value="${customInfo.weight - normalMaxWeight}" maxFractionDigits="1"/>kg
			        </span> 
			    </c:when>
			    <c:otherwise>
			        현재 <span class="standard-weight">표준 체중</span>입니다.
			    </c:otherwise>
			</c:choose>
            </p>
       
					<a href="profile.do" class="btn-card">체중기록</a>
				</c:when>
				<c:otherwise>
					로그인해 주시기 바랍니다.<br/><br/>
					<a href="javascript:void(0);" class="btn-card"
						onclick="loginCheck();">체중기록</a>
				</c:otherwise>
			</c:choose>
        </div>


		<!-- 관리목표 카드 부분 -->
        <div class="card">
            <h3>🎯 관리목표</h3>
            <c:choose>
				<c:when test="${not empty sessionScope.customInfo.member_id }">
					<p>현재 목표: ${customInfo.goal }</p>
					<a href="goal.do" class="btn-card">목표변경</a>
				</c:when>
				<c:otherwise>
					로그인해 주시기 바랍니다.<br/><br/>
					<a href="javascript:void(0);" class="btn-card"
						onclick="loginCheck();">목표변경</a>
				</c:otherwise>
			</c:choose>
        </div>



		<!-- 수면패턴 카드 부분 -->
		<div class="card">
	        <h3>😴 수면패턴</h3>
	        			<c:choose>
				<c:when test="${not empty sessionScope.customInfo.member_id }">
	        <c:choose>
	            <c:when test="${not empty sleep_dto}">
	                <p>기록일자: <fmt:formatDate value="${sleep_dto.bedtime}" pattern="yyyy-MM-dd"/></p>
	            </c:when>
	            <c:otherwise>
	                <p>오늘의 수면 기록이 없습니다.</p>
	            </c:otherwise>
	        </c:choose>
	        <p>권장: 8시간 (23시~07시)</p>
	        <p>권장 취침 시간 : 23시</p>
	        <a href="sleep.do" class="btn-card">수면기록</a>
	        				</c:when>
				<c:otherwise>
					로그인해 주시기 바랍니다.<br/><br/>
					<a href="javascript:void(0);" class="btn-card"
						onclick="loginCheck();">수면기록</a>
				</c:otherwise>
			</c:choose>
        </div>


		<!-- 혈당 카드 부분 -->
		<div class="card">
		    <h3>🩸 혈당</h3>
		    
		    

		    <c:choose>
		    	<c:when test="${not empty sessionScope.customInfo.member_id }">
				    <!-- 범례 표시 -->
					<div class="blood-glucose-legend">
					    <div class="legend-item"><span class="color-normal"></span>정상</div>
					    <div class="legend-item"><span class="color-warning"></span>주의</div>
					    <div class="legend-item"><span class="color-danger"></span>위험</div>
					</div>
				    
				    <c:choose>
			        <c:when test="${not empty dto and bgLogDate eq todayStr}">
		        	<p>공복 혈당: 
					    <span style="color: ${dto.fasting < 100 ? '#32CD32' 
					    : (dto.fasting <= 125 ? '#FFA500' : '#FF6347')};
					    font-weight:bold; font-size:1.2em;">
					        ${dto.fasting} 
					    </span>mg/dL
					</p>
					<p>식사 2시간 후 혈당: 
					    <span style="color: ${dto.afterMeal < 140 ? '#32CD32' 
					    : (dto.afterMeal <= 199 ? '#FFA500' : '#FF6347')};
					    font-weight:bold; font-size:1.2em;">
					        ${dto.afterMeal} 
					    </span>mg/dL
					</p>
					<p>취침 전 혈당: 
					    <span style="color: ${dto.beforeBed < 100 ? '#32CD32' 
					    : (dto.beforeBed <= 125 ? '#FFA500' : '#FF6347')};
					    font-weight:bold; font-size:1.2em;">
					        ${dto.beforeBed} 
					    </span>mg/dL
					</p>
			        </c:when>
			        <c:otherwise>
			            <p>오늘의 혈당 기록이 없습니다.</p>
			        </c:otherwise>
				    </c:choose>
					<a href="bg.do" class="btn-card">혈당기록</a>		    	
		    	</c:when>
		    	<c:otherwise>
		    		로그인해 주시기 바랍니다.<br/><br/>
		    		<a href="javascript:void(0);" class="btn-card"
		    			onclick="loginCheck();">혈당기록</a>
		    	</c:otherwise>
		    </c:choose>
		    

		</div>
			

</div>
    
    <c:choose>
		<c:when test="${not empty sessionScope.customInfo.member_id }">
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
				<div id="dayData" style="min-height:500px; padding:10px;">
				  
				</div>
				</div>
			
			</div>
		</c:when>
		<c:otherwise>
		</c:otherwise>
	</c:choose>



</body>
</html>
