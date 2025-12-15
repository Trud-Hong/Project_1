function fillEmptyWithZero() {
    var fasting = document.getElementById("fasting");
    var afterMeal = document.getElementById("afterMeal");
    var beforeBed = document.getElementById("beforeBed");

    if (fasting.value === "") {
    	alert("공복 혈당 값을 입력해주세요.");
    	document.myForm.fasting.focus();
    	event.preventDefault();		//폼 제출 막기
    	return false;
    }
    if (afterMeal.value === "") {
    	alert("식후 2시간 혈당 값을 입력해주세요.");
    	document.myForm.afterMeal.focus();
    	event.preventDefault();		//폼 제출 막기
    	return false;
    }
    if (beforeBed.value === "") {
    	document.myForm.beforeBed.focus();
    	alert("취침 전 혈당 값을 입력해주세요.");
    	event.preventDefault();		//폼 제출 막기
    	return false;
    }

}

document.addEventListener("DOMContentLoaded", function() {
    const form = document.forms["myForm"];
    const submitBtn = form.querySelector("button[type=submit]");
    
    form.onsubmit = function(event) {
        fillEmptyWithZero(event); // 유효성 검사 함수 호출
    };

    // 1. 유효성 검사
    if (!validateForm()) {
        event.preventDefault(); // 조건 불만족 → 제출 막기
        return;
    }

    // 1. 저장 버튼 클릭 시 확인창
    submitBtn.addEventListener("click", function(e) {
        const confirmSave = confirm("저장하시겠습니까?");
        if (!confirmSave) {
            e.preventDefault(); // 제출 취소
            window.location.href = '/heal/bg'; // 취소
        }
        // 확인하면 그대로 제출됨
    });

    // 2. 엔터키 입력 시 저장 버튼 클릭
    const inputs = form.querySelectorAll("input[type=number], input[type=date]");
    inputs.forEach(function(input) {
        input.addEventListener("keydown", function(e) {
            if (e.key === "Enter") {
                e.preventDefault();
                submitBtn.click(); // 저장 버튼 클릭 이벤트 트리거
            }
        });
    });
});
