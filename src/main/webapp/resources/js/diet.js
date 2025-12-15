window.onload = function() {



    // JSP에서 세팅한 memberId
    var memberId = window.memberId;
    var today = new Date();
    var currentMonth = today.getMonth();
    var currentYear = today.getFullYear();
    var monthNames = ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"];

    // =========================
    // 1. 주간 식단 가져오기 (회원 최근 goal 기준)
    // =========================
    function fetchWeeklyDiet(memberId, callback) {
        fetch(`/heal/api/weeklyDiet?memberId=${memberId}`)
            .then(res => res.json())
            .then(data => callback(data))
            .catch(err => {
                console.error(err);
                callback([]);
            });
    }

    // =========================

    // 2. 날짜 비교 함수 (시간 제외, 연/월/일만 비교)
    // =========================
    function isSameDate(d1, d2) {
        return d1.getFullYear() === d2.getFullYear() &&
               d1.getMonth() === d2.getMonth() &&
               d1.getDate() === d2.getDate();
    }

    // =========================
    // 3. 식단 데이터 표시
    // =========================
    function displayWeeklyDiet(data, selectedDate) {
        var container = document.getElementById("weeklyDiet");
        container.innerHTML = "<p>로딩중...</p>";

        if(!data || data.length === 0) {
            container.innerHTML = "<p>식단 데이터 없음</p>";
            return;
        }

        let filteredData = data;
        if(selectedDate) {
            filteredData = data.filter(d => {
                let recDate = new Date(d.REC_DATE);
                return isSameDate(recDate, selectedDate);
            });
        }


        if(filteredData.length === 0) {
            container.innerHTML = "<p>선택한 날짜에는 식단 데이터가 없습니다.</p>";
            return;
        }

        let html = "";
        filteredData.forEach(d => {
            html += `
                <div class="diet-day">
                    <h3>${new Date(d.REC_DATE).toLocaleDateString()} (${d.CATEGORY || "-"})</h3>
                    <h4>🍳아침</h4>
                    <div> - ${d.MORNING || "-"}</div>
                    <h4>🍲점심</h4>
                    <div> - ${d.LUNCH || "-"}</div>
                    <h4>🍞저녁</h4>
                    <div> - ${d.DINNER || "-"}</div>
                    <br/><br/>
                    <div><b>🔥일일 총 섭취 칼로리: ${d.TOTALKCAL || "-"}Kcal</b></div>
                    <div>성인 하루 칼로리 권장량은 성별, 나이, 활동량에 따라 다르며, 일반적으로 <br/>성인 남성은 2,400~2,600kcal, 성인 여성은 1,800~2,200kcal가 기준입니다.</div>
                    <br/><br/>
                    <div style="text-align: center;">
					  <button type="button"
					          style="background: #d9534f; color: #fff; border: none; padding: .5rem 1rem; border-radius: 20px; transition: ;"
					          class="btn danger"
					          onclick="location.href='/heal/main.do'">뒤로가기
					  </button>
					</div>
                </div>
            `;
        });
        container.innerHTML = html;
    }

    // =========================
    // 4. 달력 생성
    // =========================
    function createCalendar(year, month, weeklyDietData) {
        document.getElementById("calendarTitle").innerText = year + "년 ";
        document.getElementById("currentMonth").innerText = monthNames[month];


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
                if(year===today.getFullYear() && month===today.getMonth() && i===today.getDate()) {
                    day.className += " today";
                }
                day.innerText = i;

                // 클릭 시 해당 날짜 식단 표시
                day.addEventListener("click", function(){
                    let selectedDate = new Date(year, month, i);
                    displayWeeklyDiet(weeklyDietData, selectedDate);

                });

                calendar.appendChild(day);
            })(i);
        }

    }

    // =========================
    // 5. 이전/다음 달 버튼
    // =========================
    document.getElementById("prevMonth").addEventListener("click", function(){
        currentMonth--;
        if(currentMonth<0){ currentMonth=11; currentYear--; }
        createCalendar(currentYear, currentMonth, weeklyDietData);
    });

    document.getElementById("nextMonth").addEventListener("click", function(){
        currentMonth++;
        if(currentMonth>11){ currentMonth=0; currentYear++; }
        createCalendar(currentYear, currentMonth, weeklyDietData);

    });

    // =========================
    // 6. 초기 로딩
    // =========================
    var weeklyDietData = [];
    fetchWeeklyDiet(memberId, function(data){
        weeklyDietData = data;
        createCalendar(currentYear, currentMonth, weeklyDietData);
        displayWeeklyDiet(weeklyDietData, today); // 오늘 날짜 기준 초기 표시
    });


};
