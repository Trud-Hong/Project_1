function loginAdmin() {
		
		var f = document.myForm;
		
		if (!f.admin_id.value) {
			alert("아이디를 입력하세요!");
			f.admin_id.focus();
			return;
		}

		if (!f.password.value) {
			alert("패스워드를 입력하세요!");
			f.password.focus();
			return;
		}
		
		f.action = "/heal/loginAdmin_ok.do";
		f.submit();
		
		
	}