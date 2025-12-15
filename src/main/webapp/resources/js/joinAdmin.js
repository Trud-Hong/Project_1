function isValidEmail(email) {
	var format = /^((\w|[\-\.])+)@((\w|[\-\.])+)\.([A-Za-z]+)$/;
    if (email.search(format) != -1)
        return true;
    return false;
}

function sendIt() {
		
		var f = document.myForm;
		
		str = f.admin_id.value;
		str = str.trim();
		if (!str) {
			alert("아이디를 입력하세요!");
			f.admin_id.focus();
			return;
		}
		f.admin_id.value = str;
		
		str = f.password.value;
		str = str.trim();
		if (!str) {
			alert("패스워드를 입력하세요!");
			f.password.focus();
			return;
		}
		f.password.value = str;
		
		str = f.name.value;
		str = str.trim();
		if (!str) {
			alert("이름을 입력하세요!");
			f.name.focus();
			return;
		}
		f.name.value = str;
		
		f.action = "joinAdmin_ok.do";
		f.submit();
		
	}