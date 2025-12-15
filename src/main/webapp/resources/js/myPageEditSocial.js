document.addEventListener("DOMContentLoaded", function() {
    const form = document.forms["myForm"];
    const submitBtn = form.querySelector("button[type=submit]");

    // 저장 버튼 클릭 이벤트
    submitBtn.addEventListener("click", function(e) {
        // 1. 유효성 검사
        if (!validateForm()) {
            e.preventDefault(); // 조건 불만족 → 제출 막기
            return;
        }

        // 2. 확인창
        const confirmSave = confirm("저장하시겠습니까?");
        if (!confirmSave) {
            e.preventDefault(); // 제출 막기
            return;
        }
        // 통과 시 form.submit() 그대로 진행
    });

    // 엔터키 입력 시 저장 버튼 클릭 트리거
    const inputs = form.querySelectorAll("input");
    inputs.forEach(function(input) {
        input.addEventListener("keydown", function(e) {
            if (e.key === "Enter") {
                e.preventDefault();
                submitBtn.click();
            }
        });
    });
});

// 유효성 검사 함수
function validateForm() {
    const f = document.forms["myForm"];

    if (!f.name.value) {
        alert("이름을 입력하세요!");
        f.name.focus();
        return false;
    }

    if (!f.weight.value) {
        alert("체중을 입력하세요!");
        f.weight.focus();
        return false;
    } else if (!isValidNumber(f.weight.value)) {
        alert("체중은 숫자만 입력 가능하며, 최대 5자리(소수 2자리)여야 합니다.");
        f.weight.focus();
        return false;
    }

    if (!f.height.value) {
        alert("키를 입력하세요!");
        f.height.focus();
        return false;
    } else if (!isValidNumber(f.height.value)) {
        alert("키는 숫자만 입력 가능하며, 최대 5자리(소수 2자리)여야 합니다.");
        f.height.focus();
        return false;
    }

    return true; // 모든 조건 통과
}

function isValidNumber(value) {
    // 숫자이고, 최대 5자리(소수 2자리)를 만족하는지 확인
    const regex = /^\d{1,3}(\.\d{1,2})?$/;
    return regex.test(value);
}

