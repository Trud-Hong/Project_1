<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="java.util.*, java.text.*" %>
<%
	request.setCharacterEncoding("UTF-8");
	String cp = request.getContextPath();
	String today = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
	
	// 관리목표 받아오기 (URL 파라미터 우선, 그 다음 세션)
	String managementGoal = request.getParameter("goal");
	if (managementGoal == null) {
		managementGoal = (String) session.getAttribute("managementGoal");
	}
	if (managementGoal == null) {
		managementGoal = "근력"; // 기본값
	}
	
	// 추천 카테고리 매핑 - 인코딩 문제 해결
	String recommendedCategory = "";
	if (managementGoal != null) {
		switch (managementGoal) {
			case "다이어트": recommendedCategory = "cardio"; break;
			case "벌크업": recommendedCategory = "strength"; break;
			case "체중감량": recommendedCategory = "cardio"; break;
			case "근육증가": recommendedCategory = "strength"; break;
			case "체력향상": recommendedCategory = "cardio"; break;
			case "건강관리": recommendedCategory = "daily"; break;
			case "건강유지": recommendedCategory = "daily"; break;
			case "유지": recommendedCategory = "daily"; break;
			default: recommendedCategory = ""; break;
		}
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
<title>운동 기록하기 - MyCondition</title>
<link rel="stylesheet" type="text/css" href="<%=cp%>/resources/css/main.css">
<style>
.exercise-container {
    max-width: 800px;
    margin: 20px auto;
    padding: 20px;
}

.exercise-form {
    background: white;
    padding: 30px;
    border-radius: 10px;
    box-shadow: 0 2px 10px rgba(0,0,0,0.1);
    margin-bottom: 20px;
}

/* 카테고리 선택 스타일 */
.category-selection {
    background: white;
    padding: 30px;
    border-radius: 10px;
    box-shadow: 0 2px 10px rgba(0,0,0,0.1);
    margin-bottom: 20px;
    text-align: center;
}

/* 관리목표 표시 영역 */
.management-info {
    background: #E3F2FD;
    border-radius: 12px;
    padding: 12px 16px;
    margin-bottom: 25px;
    text-align: center;
    font-size: 14px;
    color: #1976D2;
    font-weight: 500;
}

.category-grid {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    gap: 20px;
    margin-bottom: 30px;
}

.category-btn {
    position: relative;
    padding: 30px 20px;
    border: 3px solid #ddd;
    border-radius: 15px;
    background: white;
    cursor: pointer;
    text-align: center;
    transition: all 0.3s ease;
}

.category-btn:hover {
    transform: translateY(-5px);
    box-shadow: 0 8px 25px rgba(0,0,0,0.15);
}

/* 추천 배지 스타일 */
.recommendation-badge {
    position: absolute;
    top: -8px;
    right: 12px;
    background: #4CAF50;
    color: white;
    padding: 4px 12px;
    border-radius: 20px;
    font-size: 12px;
    font-weight: 600;
    box-shadow: 0 2px 8px rgba(76, 175, 80, 0.3);
    z-index: 10;
}

.category-btn.recommended {
    border: 3px solid #9C27B0;
    background: linear-gradient(135deg, #F3E5F5 0%, #E1BEE7 100%);
    box-shadow: 0 4px 15px rgba(156, 39, 176, 0.2);
    transform: scale(1.02);
}

.category-btn.recommended:hover {
    transform: translateY(-5px) scale(1.02);
    box-shadow: 0 8px 25px rgba(123, 31, 162, 0.3);
}

.category-btn.cardio {
    background: linear-gradient(135deg, #ffebee 0%, #fce4ec 100%);
    border-color: #FFC0CB;
}

.category-btn.cardio:hover {
    border-color: #e91e63;
}

.category-btn.cardio.recommended {
    background: linear-gradient(135deg, #E8F5E8 0%, #F1F8E9 100%);
    border-color: #4CAF50;
}

.category-btn.strength {
    background: linear-gradient(135deg, #e3f2fd 0%, #bbdefb 100%);
    border-color: #90caf9;
}

.category-btn.strength:hover {
    border-color: #2196f3;
}

.category-btn.strength.recommended {
    background: linear-gradient(135deg, #E8F5E8 0%, #F1F8E9 100%);
    border-color: #4CAF50;
}

/* 일상 카테고리 - 노란색으로 변경 */
.category-btn.daily {
    background: linear-gradient(135deg, #fff9c4 0%, #fff59d 100%);
    border-color: #FDD835;
}

.category-btn.daily:hover {
    border-color: #FBC02D;
}

.category-btn.daily.recommended {
    background: linear-gradient(135deg, #E8F5E8 0%, #F1F8E9 100%);
    border-color: #4CAF50;
}

.category-icon {
    font-size: 50px;
    margin-bottom: 15px;
    display: block;
}

.category-name {
    font-size: 20px;
    font-weight: bold;
    margin-bottom: 8px;
}

.category-btn.cardio .category-name {
    color: #c2185b;
}

.category-btn.strength .category-name {
    color: #1976d2;
}

/* 일상 카테고리 텍스트 - 노란색 계열로 변경 */
.category-btn.daily .category-name {
    color: #F57F17;
}

/* 추천 카테고리의 텍스트 색상 */
.category-btn.recommended .category-name {
    color: #2E7D32;
}

.category-description {
    font-size: 12px;
    color: #666;
}

.form-group {
    margin-bottom: 20px;
}

.form-group label {
    display: block;
    margin-bottom: 5px;
    font-weight: bold;
    color: #333;
}

.form-group input, .form-group select, .form-group textarea {
    width: 100%;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 5px;
    font-size: 14px;
}

.form-group textarea {
    height: 80px;
    resize: vertical;
}

.exercise-type-grid {
    display: grid;
    grid-template-columns: repeat(5, 1fr);
    gap: 15px;
    margin-bottom: 15px;
}

.exercise-type-btn {
    padding: 20px 15px;
    border: 2px solid #ddd;
    border-radius: 8px;
    background: white;
    cursor: pointer;
    text-align: center;
    transition: all 0.3s ease;
}

.exercise-type-btn:hover {
    border-color: #4CAF50;
    background-color: #f9f9f9;
}

.exercise-type-btn.active {
    border-color: #4CAF50;
    background-color: #e8f5e8;
    color: #4CAF50;
}

.exercise-type-btn .icon {
    font-size: 24px;
    display: block;
    margin-bottom: 8px;
}

/* 버튼 스타일 */
.btn {
    display: inline-block;
	margin-top: 15px;
	padding: 10px 20px;
	background: #4a76a8;
	color: white;
	text-decoration: none;
	border-radius: 25px;
	font-size: 16px;
	cursor: pointer;
	transition: background 0.2s;
	border: none;
}

.btn:hover {
    background: #3a5a85;
    transform: translateY(-2px);
    box-shadow: 0 4px 15px rgba(74, 118, 168, 0.4);
}

.btn.danger {
    background: #d9534f;
    margin-left: 5px;
}

.btn.danger:hover {
    background: #c12e2a;
}

.time-input-group {
    display: flex;
    gap: 10px;
    align-items: center;
}

.time-input {
    width: 80px !important;
    text-align: center;
}

.intensity-slider {
    width: 100%;
    margin: 10px 0;
}

.intensity-labels {
    display: flex;
    justify-content: space-between;
    font-size: 12px;
    color: #666;
}

/* 뒤로가기 버튼 스타일 */
.back-btn {
    display: inline-block;
    padding: 12px 24px;
    background: #d9534f;
    color: white;
    text-decoration: none;
    border-radius: 25px;
    font-size: 14px;
    font-weight: 500;
    cursor: pointer;
    border: none;
    min-width: 160px;
    transition: all 0.3s ease;
    box-shadow: 0 2px 8px rgba(217, 83, 79, 0.3);
}

.back-btn:hover {
    background: #c12e2a;
    transform: translateY(-2px);
    box-shadow: 0 4px 15px rgba(217, 83, 79, 0.4);
}

.back-btn:active {
    transform: translateY(0);
}

.hidden {
    display: none !important;
}
</style>
</head>
<body>

<!-- 헤더 -->
<header>
    <div class="left">
		<a href="<%=cp%>/main.do">MyCondition</a>
	</div>
	<div style="font-size: 20px;">
    	운 동 기 록
    </div>
    <div></div>
</header>

<div class="exercise-container">
    <!-- 카테고리 선택 영역 -->
    <div class="category-selection" id="categorySection">
        <h2>운동 카테고리 선택</h2>
        <p style="color: #666; margin-bottom: 20px;">어떤 종류의 운동을 하셨나요?</p>
        
        <!-- 관리목표 표시 -->
        <div class="management-info">
            현재 관리목표: <%=managementGoal%> 🔥
        </div>
        
        <div class="category-grid">
            <div class="category-btn cardio" onclick="selectCategory('cardio')">
                <span class="category-icon">💨</span>
                <div class="category-name">유산소</div>
                <div class="category-description">심폐지구력 향상<br>걷기, 달리기, 자전거, 수영 등</div>
            </div>
            
            <div class="category-btn strength" onclick="selectCategory('strength')">
                <span class="category-icon">💪</span>
                <div class="category-name">근력</div>
                <div class="category-description">근육량 증가<br>푸시업, 스쿼트, 웨이트 등</div>
            </div>
            
            <div class="category-btn daily" onclick="selectCategory('daily')">
                <span class="category-icon">🏠</span>
                <div class="category-name">일상</div>
                <div class="category-description">생활 속 활동<br>청소, 계단오르기, 스트레칭 등</div>
            </div>
        </div>
        
        <!-- 뒤로가기 버튼 추가 -->
        <div style="text-align: center; margin-top: 20px;">
            <button type="button" class="btn danger" onclick="location.href='<%=cp%>/main.do'">뒤로가기</button>
        </div>
    </div>

    <!-- 운동 기록 영역 -->
    <div class="card hidden" id="exerciseSection">
        <h2 id="exerciseTitle">운동 기록하기</h2>
        
        <form class="exercise-form" action="exerciseRecord.do" method="post">
            <input type="hidden" id="selectedCategory" name="category" value="">
            
            <!-- 날짜 선택 -->
            <div class="form-group">
                <label for="exerciseDate">운동 날짜</label>
                <input type="date" id="exerciseDate" name="exerciseDate" 
                value="<%=today%>"
                max="<%=today%>" 
                required>
            </div>

            <!-- 운동 종류 선택 -->
            <div class="form-group">
                <label>운동 종류</label>
                <div class="exercise-type-grid" id="exerciseTypeGrid">
                    <!-- JavaScript로 동적 생성 -->
                </div>
                <input type="hidden" id="exerciseType" name="exerciseType" required>
            </div>

            <!-- 운동 시간 -->
            <div class="form-group">
                <label>운동 시간</label>
                <div class="time-input-group">
                    <input type="number" class="time-input" id="hours" name="hours" min="0" max="23" value="0" placeholder="시간">
                    <span>시간</span>
                    <input type="number" class="time-input" id="minutes" name="minutes" min="0" max="59" value="30" placeholder="분">
                    <span>분</span>
                </div>
            </div>

            <!-- 강도 선택 -->
            <div class="form-group">
                <label for="intensity">운동 강도</label>
                <input type="range" class="intensity-slider" id="intensity" name="intensity" min="1" max="5" value="3">
                <div class="intensity-labels">
                    <span>낮음</span>
                    <span>보통</span>
                    <span>높음</span>
                </div>
            </div>

            <!-- 소모 칼로리 (자동 계산) -->
            <div class="form-group">
                <label for="calories">예상 소모 칼로리</label>
                <input type="number" id="calories" name="calories" readonly style="background-color: #f5f5f5;">
                <small style="color: #666;">운동 종류, 시간, 강도에 따라 자동 계산됩니다</small>
            </div>

<!--             메모
            <div class="form-group">
                <label for="memo">메모</label>
                <textarea id="memo" name="memo" placeholder="운동에 대한 메모를 입력하세요 (선택사항)"></textarea>
            </div> -->

            <!-- 버튼 그룹 -->
			<div style="text-align:center;">
			    <button type="submit" class="btn">저장하기</button>
			    <button type="button" class="btn danger" onclick="goBackToCategory()">뒤로가기</button>
			    <button type="button" class="btn secondary" onclick="location.href='<%=cp%>/main.do'">메인으로</button>
			</div>
        </form>
    </div>
</div>

<script>
// JSP에서 추천 카테고리 받기 (인코딩 문제 해결)
var recommendedCategory = '<%=recommendedCategory%>';

// 현재 관리목표
var currentManagementGoal = '<%=managementGoal%>';

// 디버깅용
console.log('Management Goal:', currentManagementGoal);
console.log('Recommended Category:', recommendedCategory);

// 운동 데이터 (3x2 그리드에 맞게 조정)
var exerciseData = {
    cardio: [
        { id: 1, name: '걷기', icon: '🚶‍♂️' },
        { id: 2, name: '조깅', icon: '🏃‍♂️' },
        { id: 3, name: '자전거 타기', icon: '🚴‍♂️' },
        { id: 4, name: '수영', icon: '🏊‍♂️' },
        { id: 5, name: '줄넘기', icon: '🤾' },
        { id: 6, name: '계단 오르기', icon: '📶' },
        { id: 7, name: '에어로빅', icon: '💃' },
        { id: 8, name: '복싱', icon: '🥊' },
        { id: 9, name: '하이킹', icon: '🥾' },
        { id: 10, name: '러닝', icon: '🏃' }
    ],
    strength: [
        { id: 11, name: '스쿼트', icon: '🦵' },
        { id: 12, name: '데드리프트', icon: '🏋️‍♂️' },
        { id: 13, name: '벤치프레스', icon: '🏋️‍♀️' },
        { id: 14, name: '풀업/턱걸이', icon: '💪' },
        { id: 15, name: '밀리터리 프레스', icon: '🏋️' },
        { id: 16, name: '바벨 로우', icon: '🏋️‍♂️' },
        { id: 17, name: '런지', icon: '🦵' },
        { id: 18, name: '랫풀다운', icon: '💪' },
        { id: 19, name: '레그 프레스', icon: '🦵' },
        { id: 20, name: '케이블 크로스오버/플라이', icon: '💪' }
    ],
    daily: [
        { id: 21, name: '빠르게 걷기', icon: '🚶‍♂️' },
        { id: 22, name: '요가', icon: '🧘‍♀️' },
        { id: 23, name: '스탭박스 운동', icon: '📦' },
        { id: 24, name: '실내 클라이밍', icon: '🧗‍♂️' },
        { id: 25, name: '배드민턴', icon: '🏸' },
        { id: 26, name: '훌라후프 돌리기', icon: '⭕' },
        { id: 27, name: '스피닝', icon: '🚴‍♀️' },
        { id: 28, name: '스탠딩 코어운동', icon: '🤸‍♂️' },
        { id: 29, name: '밴드 운동', icon: '🤾' },
        { id: 30, name: '트램폴린 점프 운동', icon: '🤸‍♀️' }
    ]
};

// 페이지 로드 시 추천 카테고리 강조
window.onload = function() {
    console.log('Setting recommendation for category:', recommendedCategory);
    
    // 추천 카테고리에 클래스와 배지 추가
    if (recommendedCategory) {
        var recommendedBtn = document.querySelector('.category-btn.' + recommendedCategory);
        if (recommendedBtn) {
            recommendedBtn.classList.add('recommended');
            
            // 추천 배지 추가
            var badge = document.createElement('div');
            badge.className = 'recommendation-badge';
            badge.textContent = '추천';
            recommendedBtn.appendChild(badge);
            
            console.log('Successfully added recommendation to:', recommendedCategory);
        } else {
            console.log('Could not find button for category:', recommendedCategory);
        }
    } else {
        console.log('No recommended category found');
    }
    
    // 기존 이벤트 리스너들
    document.getElementById('hours').addEventListener('input', calculateCalories);
    document.getElementById('minutes').addEventListener('input', calculateCalories);
    document.getElementById('intensity').addEventListener('input', calculateCalories);

    // 폼 제출 검증
    document.querySelector('.exercise-form').addEventListener('submit', function(e) {
        if (!document.getElementById('exerciseType').value) {
            e.preventDefault();
            alert('운동 종류를 선택해주세요.');
            return;
        }

        var hours = parseInt(document.getElementById('hours').value) || 0;
        var minutes = parseInt(document.getElementById('minutes').value) || 0;
        
        if (hours === 0 && minutes === 0) {
            e.preventDefault();
            alert('운동 시간을 입력해주세요.');
            return;
        }
    });
};

// 카테고리 선택
function selectCategory(category) {
    document.getElementById('selectedCategory').value = category;
    document.getElementById('categorySection').classList.add('hidden');
    document.getElementById('exerciseSection').classList.remove('hidden');
    
    // 제목 업데이트
    var titles = {
        cardio: '💨 유산소 운동 기록',
        strength: '💪 근력 운동 기록', 
        daily: '🏠 일상 활동 기록'
    };
    document.getElementById('exerciseTitle').textContent = titles[category];
    
    // 운동 종목 버튼 생성
    createExerciseButtons(category);
    
    // 디버깅: 선택한 카테고리 확인
    console.log('Selected category:', category);
    console.log('Recommended category:', recommendedCategory);
}

// 운동 종목 버튼 생성
function createExerciseButtons(category) {
    var grid = document.getElementById('exerciseTypeGrid');
    grid.innerHTML = '';
    
    // 디버깅: 현재 상태 확인
    console.log('Creating exercise buttons for category:', category);
    console.log('Current recommended category:', recommendedCategory);
    console.log('Management goal:', currentManagementGoal);
    
    // 선택한 카테고리의 운동들
    for(var i = 0; i < exerciseData[category].length; i++) {
        var exercise = exerciseData[category][i];
        var btn = document.createElement('div');
        btn.className = 'exercise-type-btn';
        btn.setAttribute('data-type', exercise.id);
        btn.innerHTML = '<span class="icon">' + exercise.icon + '</span><span>' + exercise.name + '</span>';
        btn.onclick = function() {
            selectExerciseType(this);
        };
        grid.appendChild(btn);
    }
}

// 운동 종목 선택
function selectExerciseType(btn) {
    // 모든 버튼 비활성화
    var buttons = document.querySelectorAll('.exercise-type-btn');
    for(var i = 0; i < buttons.length; i++) {
        buttons[i].classList.remove('active');
    }
    
    // 선택한 버튼 활성화
    btn.classList.add('active');
    
    var exerciseType = btn.getAttribute('data-type');
    document.getElementById('exerciseType').value = exerciseType;
    
    calculateCalories();
}

// 카테고리로 돌아가기
function goBackToCategory() {
    document.getElementById('exerciseSection').classList.add('hidden');
    document.getElementById('categorySection').classList.remove('hidden');
    
    // 폼 초기화
    document.getElementById('exerciseType').value = '';
    var buttons = document.querySelectorAll('.exercise-type-btn');
    for(var i = 0; i < buttons.length; i++) {
        buttons[i].classList.remove('active');
    }
    document.getElementById('calories').value = '';
}

// 칼로리 계산
function calculateCalories() {
    var exerciseType = parseInt(document.getElementById('exerciseType').value);
    var hours = parseInt(document.getElementById('hours').value) || 0;
    var minutes = parseInt(document.getElementById('minutes').value) || 0;
    var intensity = parseInt(document.getElementById('intensity').value) || 3;
    
    if (!exerciseType) return;
    
    var totalMinutes = hours * 60 + minutes;
    
    // 운동별 기본 칼로리 (분당) - 실제 운동 ID 기준
    var baseCalories = {
        // 유산소 (1-10)
        1: 5, 2: 8, 3: 8, 4: 11, 5: 10, 6: 8, 7: 7, 8: 9, 9: 7, 10: 12,
        // 근력 (11-20)  
        11: 5, 12: 7, 13: 6, 14: 8, 15: 6, 16: 6, 17: 5, 18: 6, 19: 5, 20: 5,
        // 일상 (21-30)
        21: 6, 22: 3, 23: 7, 24: 8, 25: 6, 26: 5, 27: 9, 28: 4, 29: 4, 30: 6
    };
    
    var intensityMultiplier = { 1: 0.7, 2: 0.85, 3: 1.0, 4: 1.2, 5: 1.4 };
    
    var calories = Math.round(
        totalMinutes * 
        (baseCalories[exerciseType] || 6) * 
        (intensityMultiplier[intensity] || 1.0)
    );
    
    document.getElementById('calories').value = calories;
}
</script>

</body>
</html>