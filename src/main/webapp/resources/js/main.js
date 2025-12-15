//로그인 확인 여부
function loginCheck(){
	
	var result = confirm("로그인시 이용 가능합니다. 로그인 하시겠습니까?");
	if(result){
		window.location.href = 'login.do';
	}else{
		window.location.href = 'main.do';
	}
}

window.onload = function() {

    var today = new Date();
    var currentMonth = today.getMonth();
    var currentYear = today.getFullYear();
    var monthNames = ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"];
    
    // 혈압 경고 상태 처리
    function getBloodPressureStatus(BP_LEVEL) {
        let warningText = '';
        let warningText2 = '';
        let color = '';
        var scoreBP = 0;

        switch (BP_LEVEL) {
            case '정상':
                color = 'green';  // 정상은 초록색
                warningText = ' 정상';
                warningText2 = '';
                scoreBP = 20;
                break;
            case '주의':
                color = 'orange';  // 주의는 주황색
                warningText = ' 주의';
                warningText2 = '경고 :';
                scoreBP = 16;
                break;
            case '고혈압 전단계':
                color = 'tomato';  // 고혈압 전단계는 토마토색
                warningText = ' 고혈압 전단계';
                warningText2 = '경고 :';
                scoreBP = 12;
                break;
            case '고혈압':
                color = 'red';  // 1단계 고혈압은 빨간색
                warningText = ' 1단계 고혈압';
                warningText2 = '경고 :';
                scoreBP = 8;
                break;
            case '고혈압 위기':
                color = 'darkred';  // 2단계 고혈압은 어두운 빨간색
                warningText = ' 2단계 고혈압';
                warningText2 = '경고 :';
                scoreBP = 4;
                break;
            default:
                color = 'black';  
                warningText = ' 정보 없음';
                warningText2 = '';
                break;
        }

        return { warningText, color, warningText2, scoreBP };
    }
    
    // 수면 상태 처리
    function getSleepStatus(SLEEP) {
	    let SLEEPText = '';
	    let SLEEPText2 = '';
	    let color = '';
	    var scoreSleep = 0;
	    
	
	    if (SLEEP <= 6 && SLEEP > 0) {
	        color = 'orange';
	        SLEEPText = ' 수면 부족';
	        SLEEPText2 = '';
	        scoreSleep = 10;
	    } else if (SLEEP < 9 && SLEEP > 6) {
	        color = 'green';
	        SLEEPText = ' 정상';
	        SLEEPText2 = '';
	        scoreSleep = 20;
	    } else if (SLEEP >= 10) {
	        color = 'orange';
	        SLEEPText = ' 과도한 수면';
	        SLEEPText2 = '';
	        scoreSleep = 10;
	    } else {
	        color = 'black';
	        SLEEPText = '';
	        SLEEPText2 = '수면 정보 없음';
	        scoreSleep = 0;
	    }
	
	    return { SLEEPText, color, SLEEPText2, scoreSleep };
	}
	
	//취침시간 점수 계산
	function getBedTimeScore(bedTime){
		
		var score = 0;
		
		let hour = parseInt(bedTime.split(":")[0], 10);
		
		if(!bedTime){
			
			score = 0;
			
		} else {
			if((hour > 20 && hour <= 23) || hour == 0){
				score = 20;
			} else if(hour > 0 && hour < 6 ) {
				score = 10;
			} else {
				score = 0;
			}
		}
		
		return score;
	}
	
	//운동 점수 계산
	function getExerciseScore(exercise) {
		var score = 0;
		
		
		if(!exercise) {
			
			score = 0;
			
		} else {
			
			//운동명 제외한 숫자만 int 값으로 변경
			let ex = parseInt(exercise.replace(/[^0-9]/g, ""), 10);
			
			if(ex >= 120) {
				score = 20;
			} else if (ex >= 90) {
				score = 15;
			} else if (ex >= 60) {
				score = 10;
			} else if (ex >= 30) {
				score = 5;
			} else {
				score = 0;
			}
		}
		
		return score;
	}

	function getHabitScore(MORNING, LUNCH, DINNER) {
		
		let score = 0;
		if (MORNING) score++;
		if (LUNCH) score++;
		if (DINNER) score++;
		if (score === 3) return 20;
		if (score === 2) return 14;
		if (score === 1) return 7;
		return 0;
		
		
	}

    
    // 데이터 가져오는 함수
    function fetchDayData(year, month, day, memberId, callback) {
        fetch(`/heal/api/daydata?year=${year}&month=${month+1}&day=${day}&memberId=${memberId}`)
            .then(response => response.json())
            .then(data => {
                console.log("DB에서 가져온 데이터:", data);
                callback(data);
            })
            .catch(err => {
                console.error(err);
                callback({ 
                    BP: "",
                    BP_LEVEL: "",
                    SLEEP: "",
                    BEDTIME: "",
                    EXERCISE: "",
                    GOAL: "",
                    MORNING: "",
                    LUNCH: "",
                    DINNER: "",
                    CONDITION: "" });
            });
    }


    // 데이터 표시

    function displayData(year, month, day, memberId) {
        var container = document.getElementById("dayData");
        container.innerHTML = "<p>로딩중...</p>";

        fetchDayData(year, month, day, memberId, function(data){
            container.innerHTML = "<h3>" + year + "-" + (month+1) + "-" + day +  " 일 컨디션 정보" + "</h3>";

            // 혈압 경고 처리
            const bpStatus = getBloodPressureStatus(data.BP_LEVEL);
            const slStatus = getSleepStatus(data.SLEEP);
            const btStatus = getBedTimeScore(data.BEDTIME);
            const exStatus = getExerciseScore(data.EXERCISE);
            const hbStatus = getHabitScore(data.MORNING, data.LUNCH, data.DINNER);
            
            let totalScore = bpStatus.scoreBP + slStatus.scoreSleep + btStatus + exStatus + hbStatus;
			
			if(data.SLEEP !== null && data.SLEEP !== "" && !isNaN(data.SLEEP)){
	            container.innerHTML +=
	                "<p>❤️ 혈압: "+ (data.BP || "-") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (혈압&nbsp;" + bpStatus.warningText2 + "<span style='color:" + bpStatus.color + "'>" + bpStatus.warningText + "</span>" + ")</p>" +
	                "<p>💤 수면시간: " + (data.SLEEP ? data.SLEEP + "시간"+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (권장 수면시간 <span style='color:" + slStatus.color + "'>" + slStatus.SLEEPText + "</span>" + ")</p>" : "-" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (수면 정보 없음)" + "</span>" + "</p>") +
	                "<p>📝  → 영향 : " + (data.SLEEP >= 9 ? "집중력 저하, 대사 문제(당뇨병, 비만 등)" :
	                (data.SLEEP > 6 ? "호르몬 균형 유지, 면역력 강화" : (data.SLEEP >0 ? "면역력, 체력 저하" : "입력된 수면시간 없음"))) + "</p>" +
	                "<p>💤 취침시간: " + (data.BEDTIME || "-") + "</p>" +			
	                "<p>📝 → 영향 : " + ((data.BEDTIME > "20:00" && data.BEDTIME <= "23:59") ? "뇌 회복, 스트레스 완화" :
	                 (data.BEDTIME >= "00:00" && data.BEDTIME < "06:00" ? "기억력 저하, 우울감 증가, 판단력 저하" : ("옳지 않은 취침시간 "))) + "</p>" +
	                "<p>🏃 운동: " + (data.EXERCISE || "-") + "</p>" +
	                "<p>🎯 관리목표: " + (data.GOAL || "-") + "</p>" +
	                "<p>🍚 식사: 아침 " + (data.MORNING || "--:--") + " / 점심 " + (data.LUNCH || "--:--") + " / 저녁 " + (data.DINNER || "--:--") + "</p>" +
	                "<p>💯컨디션 점수: " + totalScore + "점</p>";
			}else {
	            container.innerHTML +=
	                "<p>❤️ 혈압: "+ (data.BP || "-") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (혈압&nbsp;" + bpStatus.warningText2 + "<span style='color:" + bpStatus.color + "'>" + bpStatus.warningText  + "</span>" + ")</p>" +
	                "<p>💤 수면시간: " + (data.SLEEP ? data.SLEEP + "시간" : "-") + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; (<span style='color:" + slStatus.color + "'>" + slStatus.SLEEPText2 + "</span>" + ")</p>" +
	                "<p>💤 취침시간: " + (data.BEDTIME || "-") + "</p>" +			
	                "<p>🏃 운동: " + (data.EXERCISE || "-") + "</p>" +
	                "<p>🎯 관리목표: " + (data.GOAL || "-") + "</p>" +
	                "<p>🍚 식사: 아침 " + (data.MORNING || "--:--") + " / 점심 " + (data.LUNCH || "--:--") + " / 저녁 " + (data.DINNER || "--:--") + "</p>" +
	
	                "<p>💯컨디션 점수: " + totalScore + "점</p>";
			}

        });
    }

    // 달력 생성
    function createCalendar(year, month) {
        document.getElementById("calendarTitle").innerText = year + "년 ";
        document.getElementById("currentMonth").innerText =  monthNames[month];

        var calendar = document.getElementById("calendar");
        calendar.innerHTML = "";

        var days = ['일','월','화','수','목','금','토'];
        for(var d=0; d<days.length; d++){
            var header = document.createElement("div");
            header.className = "day-header";
            header.innerText = days[d];
            calendar.appendChild(header);
        }

        var firstDay = new Date(year, month, 1).getDay();
        var lastDate = new Date(year, month+1, 0).getDate();

        for(var i=0; i<firstDay; i++){
            calendar.appendChild(document.createElement("div"));
        }

        for(var i=1; i<=lastDate; i++){
            (function(i){
                var day = document.createElement("div");
                day.className = "day";
                if(year === today.getFullYear() && month === today.getMonth() && i === today.getDate()) {
                    day.className += " today";
                }

                day.innerText = i;

                // DB에서 혈압 가져와서 표시
                fetchDayData(year, month, i, memberId, function(data){
                    if(data.BP) {
                        // 혈압 정보로 처리 가능
                    }
                });

                // 클릭 이벤트
                day.addEventListener("click", function(){
                    displayData(year, month, i, memberId);
                });

                calendar.appendChild(day);
            })(i);
        }

        displayData(today.getFullYear(), today.getMonth(), today.getDate(), memberId);
    }

    // 이전/다음 달 버튼
    document.getElementById("prevMonth").addEventListener("click", function(){
        currentMonth--;
        if(currentMonth<0){ currentMonth=11; currentYear--; }
        createCalendar(currentYear, currentMonth);
    });
    document.getElementById("nextMonth").addEventListener("click", function(){
        currentMonth++;
        if(currentMonth>11){ currentMonth=0; currentYear++; }
        createCalendar(currentYear, currentMonth);
    });

    createCalendar(currentYear, currentMonth);
};
