function login() {
    var f = document.myForm;
    
    if (!f.member_id.value) {
        alert("아이디를 입력하세요!");
        f.member_id.focus();
        return;
    }

    if (!f.password.value) {
        alert("패스워드를 입력하세요!");
        f.password.focus();
        return;
    }

    fetch(f.action, {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8"
        },
        body: `member_id=${encodeURIComponent(f.member_id.value)}&password=${encodeURIComponent(f.password.value)}`
    })
    .then(res => res.json())
    .then(data => {
        if (data.success) {
            // 로그인 성공
            window.location.href = data.redirectUrl;
        } else {
            // 로그인 실패
            alert(data.message);
        }
    })
    .catch(err => {
        console.error(err);
        alert("서버 오류가 발생했습니다.");
    });
}
	
function enterkey(event){
		
		if(event.keyCode == 13){
			login();
		}
		
}