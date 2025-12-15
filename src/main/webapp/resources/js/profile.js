function confirmSave() {
    return confirm("저장하시겠습니까?");
}

function validateForm() {
	const f = document.profileForm;
	
	if (!f.weight.value) {
        alert("체중을 입력하세요!");
        f.weight.focus();
        return false;
    } else if (!isValidNumber(f.weight.value)) {
        alert("체중은 숫자만 입력 가능하며, 최대 5자리(소수 2자리)여야 합니다.");
        f.weight.focus();
        return false;
    }
    
    return confirmSave();
}

function isValidNumber(value) {
    // 숫자이고, 최대 5자리(소수 2자리)를 만족하는지 확인
    const regex = /^\d{1,3}(\.\d{1,2})?$/;
    return regex.test(value);
}