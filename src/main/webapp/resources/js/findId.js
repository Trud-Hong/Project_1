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

// 인증번호 발송
function sendAuthCode() {
    const email = getEmail();
    if (!email) { 
        alert("이메일을 입력하세요."); 
        return; 
    }

    console.log("전송할 이메일(JSON):", email);

    $.ajax({
        url: cp + "/sendAuthCode",  // contextPath 사용
        type: "POST",
        data: { email, id : null },
        success: function(res) {
        	let msg = ""; 
            if(res === "success") {
               msg = "인증번호가 이메일로 발송되었습니다.";
                startTimer();
                
                $("#sendBtn, #emailUser, #emailDomain, #emailSelect")
                .prop("disabled", true);
                
            } else if(res =="notExist"){
                msg = "가입된 이메일이 아닙니다.";
            }else if(res == "sendFail"){
            	msg = "인증번호 전송 실패";
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
        url: cp + "/verifyAuthCode",
        type: "POST",
        data: { email: email, code: code },
        success: function(res) {
            if(res.indexOf("success") === 0) {
                const member_id = res.split(":")[1]; // 서버가 반환한 memberId
                console.log("인증 성공, member_id:", member_id);

                // 아이디 찾기 화면에 member_id 표시
                $("#foundIdSection").show();
                $("#result").val(member_id);

                // 버튼 표시
                $("#foundIdSection .button-row").show();

                // 기존 input/버튼 비활성화
                $("#authCode, #verifyBtn, #sendBtn, #emailUser, #emailDomain, #emailSelect")
                    .prop("disabled", true)
                    .css({"background-color": "#eee", "cursor": "not-allowed"});

                // 타이머 정지
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
