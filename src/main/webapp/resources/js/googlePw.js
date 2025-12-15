function validateForm() {
    var f = document.myForm;
    var str;

    // 비밀번호 체크
    str = f.password.value.trim();
    if (!str) { alert("패스워드를 입력하세요!"); f.password.focus(); return false; }

    // 모든 검증 통과 시
    return true;
}