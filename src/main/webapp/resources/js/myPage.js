
function isDelete(){
	
    var password = document.getElementById("password").value;


    if(confirm("정말로 삭제하시겠습니까?")) {
        // hidden input에 비밀번호 넣기
        document.getElementById("hiddenPassword").value = password;
        // form submit
        document.getElementById("deleteForm").submit();
    }
}

