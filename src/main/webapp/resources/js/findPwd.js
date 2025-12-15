var timerInterval;
var countdown = 60;


const emailDomainInput = document.getElementById("emailDomain");
const domainList = document.getElementById("domainList");

// input 클릭 시 목록 보이기
emailDomainInput.addEventListener("focus", () => {
    domainList.style.display = "block";
});

// 목록 항목 클릭
domainList.querySelectorAll("li").forEach(li => {
    li.addEventListener("click", () => {
        if (li.dataset.value) {
            emailDomainInput.value = li.dataset.value;
        } else {
            emailDomainInput.value = ""; // 직접입력
            emailDomainInput.focus();
        }
        domainList.style.display = "none";
    });
});

// 외부 클릭 시 목록 닫기
document.addEventListener("click", (e) => {
    if (!e.target.closest(".email-domain-wrapper")) {
        domainList.style.display = "none";

    }
});

// 이메일 합치기
function getEmail() {
    const user = document.getElementById("emailUser").value.trim();
    const domain = document.getElementById("emailDomain").value.trim();
    if (!user || !domain) return "";
    return user + "@" + domain;
}

function getId(){
	const user = document.getElementById("userId").value.trim();
	return user;
}

//아이디 확인
function idCheck() {
    const id = $("#userId").val().trim();
    if (!id) { 
        alert("아이디를 입력하세요."); 
        return; 
    }

    $.ajax({
        url: cp + "/idCheck",
        type: "POST",
        data: { id: id },
        success: function(res) {
            if (res === "success") {
            
                alert("아이디가 확인되었습니다.");
                $("#sendBtn").prop("disabled", false);
                $("#userId").prop("disabled", true);
                $("#verifyBtn").prop("disabled", true);

            } else {
            
                alert("아이디가 존재하지 않습니다.");
                $("#sendBtn").prop("disabled", true);
                $("#userId").prop("disabled", false);
                $("#verifyBtn").prop("disabled", false);
            }
        },
        error: function() {
            alert("서버 오류가 발생했습니다.");
        }
    });
}

//이메일로 비밀번호 보내기
function sendPasswordToEmail() {
    const id = $("#userId").val().trim();
    const email = getEmail(); // 기존 getEmail() 사용

    if (!id) {
		alert("아이디와 이메일을 입력하세요.");
		return;
	}
	
    if (!email) { 
        alert("이메일을 입력하세요."); 
        return; 
    }

    $.ajax({
        url: cp + "/sendPasswordEmail",
        type: "POST",
        data: { id: id, email: email },
        success: function(res) {
            if (res === "success") {
                alert("비밀번호를 이메일로 발송했습니다.");

                // ✅ foundPwdSection 전체 보여주기
                $("#foundPwdSection").show();

                // ✅ 로그인/첫화면 버튼 보여주기
                $("#foundPwdSection .button-row").show();

                // ✅ 기존 input/버튼 비활성화
                $("#userId, #verifyBtn, #sendBtn, #emailUser, #emailDomain, #emailSelect").prop("disabled", true);

            } else {
                alert("아이디 또는 이메일이 올바르지 않습니다.");
            }
        },
        error: function() {
            alert("서버 오류가 발생했습니다.");
        }
    });
    
}
// 인증번호 발송
function sendAuthCode() {
    const id = $("#userId").val().trim();
    const email = getEmail(); 

    if (!id) {
        alert("아이디와 이메일을 입력하세요.");
        return;
    }

    if (!email) { 
        alert("이메일을 입력하세요."); 
        return; 
    }

    $.ajax({
        url: cp + "/sendAuthCode",
        type: "POST",
        data: { email, id },
        success: function(res) {
        	let msg = "";
            if(res === "success") {
            	
                msg = "인증번호가 이메일로 발송되었습니다.";
                
                $("#emailUser, #emailDomain").prop("disabled", true)
                             .css({"background-color": "#eee", "cursor": "not-allowed"});
                
                // ✅ 인증번호 입력창 표시
                $("#foundPwdSection").show();

                // 타이머 시작
                startTimer();
            } else if(res == "notExist"){
                msg = "가입된 이메일이 아닙니다.";
            } else if(res == "notEqual"){
            	msg = "가입한 아이디와 이메일이 일치하지 않습니다.";
            } else if(res == "sendFail"){
            	msg = "인증번호 전송에 실패했습니다";
            }
            
            alert(msg);
        },
        error: function(xhr, status, error) {
            console.error("AJAX 오류:", status, error, xhr.responseText);
            alert("서버 오류가 발생했습니다.");
        }
    });
}

function verifyAuthCode() {
    const email = getEmail();
    const code = $("#authCode").val().trim();

    if (!email) { 
        alert("이메일을 입력하세요."); 
        return; 
    }
    if (!code) { 
        alert("인증번호를 입력하세요."); 
        return; 
    }

    $.ajax({
        url: cp + "/verifyAuthCode",  // contextPath 사용
        type: "POST",
        data: { email: email, code: code },
        success: function(res) {
            if(res.indexOf("success") === 0) {
				
				alert("인증번호가 일치합니다.");
				
				// 인증번호 입력창과 확인 버튼 비활성화 및 회색 처리
			    $("#authCode").prop("disabled", true).
			    css({"background-color": "#eee", "cursor": "not-allowed"});
			    
			    $("#verifyAuthBtn").prop("disabled", true).
			    css({"background-color": "#ccc", "cursor": "not-allowed"});
			
			    // 비밀번호 변경/첫 화면 버튼 표시
			    $("#foundPwdSection .button-row").show();

                // 타이머 정지 및 초기화
                clearInterval(timerInterval);
                $("#timer").text("");
            } else {
                alert("인증번호가 올바르지 않습니다.");
            }
        },
        error: function(xhr, status, error) {
            console.error("AJAX 오류:", status, error, xhr.responseText);
            alert("서버 오류가 발생했습니다.");
        }
    });
}

// 타이머 시작
function startTimer() {
    countdown = 60;
    $("#sendBtn").prop("disabled", true);
    $("#timer").text("(" + countdown + "초 후 재전송 가능)");

    clearInterval(timerInterval);
    timerInterval = setInterval(function() {
        countdown--;
        if (countdown <= 0) {
            clearInterval(timerInterval);
            $("#sendBtn").prop("disabled", false);
            $("#timer").text("");
        } else {
            $("#timer").text("(" + countdown + "초 후 재전송 가능)");
        }
    }, 1000);
}

//비밀번호 팝업창
function openChangePasswordPopup() {
    const memberId = $("#userId").val().trim();
    if(!memberId) {
        alert("아이디를 먼저 확인하세요.");
        return;
    }

    window.open(cp + '/pwdChange.do', 
                '비밀번호 변경', 
                'width=650,height=450,scrollbars=no,resizable=no');
}