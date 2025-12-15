function validateForm() {
    var f = document.myForm;
    var str;
    const id = f.member_id.value.trim();
    const pw = f.password.value.trim();
    
    const idRegex = /^[a-zA-Z0-9]{5,20}$/;
    const pwRegex = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?])[A-Za-z\d!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]{8,15}$/;
    
    if (!idRegex.test(id)) {
        alert("아이디는 영문자와 숫자만 가능하며, 5~20자로 입력해주세요.");
        f.member_id.focus();
        return false;
    }

    if (!isIdChecked) {
	    alert("아이디 중복 검사를 해주세요.");
	    document.getElementById("userId").focus();
	    return false;
	}

    // 비밀번호 체크
    
    if (!pw) {
        alert("비밀번호를 입력하세요!");
        f.password.focus();
        return false;
    }
    
    if (!pwRegex.test(pw)) {
        alert("비밀번호는 8~15자이며, 영문자, 숫자, 특수문자를 모두 포함해야 합니다.");
        f.password.focus();
        return false;
    }

    // 이름 체크
    str = f.name.value.trim();
    if (!str) { alert("이름을 입력하세요!"); f.name.focus(); return false; }

    // 생년월일 체크
    str = f.birth.value.trim();
    if (!str) { alert("생일을 입력하세요!"); f.birth.focus(); return false; }

    // 이메일 체크
    var emailFull = getEmail();
    if (!emailFull) {
        alert("이메일을 입력하세요!");
        document.getElementById("emailUser").focus();
        return false;
    } else if (!isValidEmail(emailFull)) {
        alert("이메일 형식을 지켜주십시오.");
        document.getElementById("emailUser").focus();
        return false;
    }

    if (!isEmailChecked) {
        alert("이메일 중복 검사를 해주세요.");
        document.getElementById("emailUser").focus();
        return false;
    }

    document.getElementById("email").value = emailFull;

    // 성별 체크
    var genderChecked = false;
    for (var i = 0; i < f.gender.length; i++) {
        if (f.gender[i].checked) { genderChecked = true; break; }
    }
    if (!genderChecked) { alert("성별을 선택하세요!"); return false; }

    // 체중 체크
    str = f.weight.value.trim();
    if (!str) { 
        alert("체중을 입력하세요!"); 
        f.weight.focus();
        return false; 
    } else if (!isValidNumber(f.weight.value)) {
        alert("체중은 숫자만 입력 가능하며, 최대 5자리(소수 2자리)여야 합니다.");
        f.weight.focus();
        return false;
    }

    // 키 체크
    str = f.height.value.trim();
    if (!str) { 
        alert("키를 입력하세요!"); 
        f.height.focus(); 
        return false; 
    } else if (!isValidNumber(f.height.value)) {
        alert("키는 숫자만 입력 가능하며, 최대 5자리(소수 2자리)여야 합니다.");
        f.height.focus();
        return false;
    }

    // 목표 체크
    if (!f.goal.value) { alert("관리 목표를 선택하세요!"); return false; }
    
    // 개인정보 수집 이용 동의 체크
    var consent = document.getElementById("personalInformationChk");
    if (!consent.checked) {
        alert("개인정보 수집 이용 동의를 확인해주세요.");
        return false;
    }

    // 모든 검증 통과 시
    return true;
    alert("회원가입 성공!!<br>로그인 화면으로 이동합니다")
}

function isValidNumber(value) {
    // 숫자이고, 최대 5자리(소수 2자리)를 만족하는지 확인
    const regex = /^\d{1,3}(\.\d{1,2})?$/;
    return regex.test(value);
}

// E-Mail 검사
function isValidEmail(email) {
	var format = /^((\w|[\-\.])+)@((\w|[\-\.])+)\.([A-Za-z]+)$/;
    if (email.search(format) != -1)
        return true; //올바른 포맷 형식
    return false;
}



//개인정보 수집 이용 동의 팝업창 열기
function openPersonalInformationPopup() {
    window.open('/heal/personalInformation', 
    		'개인정보 수집 이용 동의',
                'width=800,height=1000,scrollbars=yes,resizable=yes');
}

//팝업창 닫기
function closePopup(){
    try {
        if (window.opener && !window.opener.closed) {
            var checkbox = window.opener.document.getElementById('personalInformationChk');
            if (checkbox) {
                checkbox.removeAttribute('disabled'); // disabled 해제
                checkbox.checked = true; // 체크
            }
        }
    } catch (e) {
        console.error(e);
    }
    window.close();
}

// 이메일 합치기
function getEmail() {
    const user = document.getElementById("emailUser").value.trim();
    const domain = document.getElementById("emailDomain").value.trim();
    if (!user || !domain) return "";
    return user + "@" + domain;
}

//이메일 js
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

let isEmailChecked = false; // 이메일 중복검사 완료 여부

function checkEmailDuplicate() {
    const emailUser = document.getElementById("emailUser").value.trim();
    const emailDomain = document.getElementById("emailDomain").value.trim();
    const email = emailUser + "@" + emailDomain;

    if (!emailUser || !emailDomain) {
        alert("이메일을 입력해주세요.");
        return;
    }

    if (!isValidEmail(email)) {
        alert("이메일 형식이 올바르지 않습니다.");
        return;
    }

    fetch(`/heal/checkEmail?email=${encodeURIComponent(email)}`)
        .then(response => response.text())
        .then(result => {
            if (result === "exist") {
                alert("이미 가입된 이메일입니다.");
                isEmailChecked = false;
            } else {
                alert("사용 가능한 이메일입니다.");
                isEmailChecked = true; // 중복 검사 통과
            }
        })
        .catch(err => console.error(err));
}

//아이디 중복검사
let isIdChecked = false; // 아이디 중복 체크 여부

function checkIdDuplicate() {
    const id = document.getElementById("userId").value.trim();
    if (!id) {
        alert("아이디를 입력해주세요.");
        return;
    }

    fetch(`/heal/checkId?member_id=${encodeURIComponent(id)}`)
        .then(res => res.text())
        .then(result => {
            if (result === "exist") {
                alert("이미 존재하는 아이디입니다.");
                isIdChecked = false;
            } else {
                alert("사용 가능한 아이디입니다.");
                isIdChecked = true;
            }
        })
        .catch(err => console.error(err));
}
// 비밀번호 input 변경 시 중복검사 초기화
document.getElementById("emailUser").addEventListener("input", () => {
    isEmailChecked = false;
});
document.getElementById("emailDomain").addEventListener("input", () => {
    isEmailChecked = false;
});
// 아이디 input 변경 시 중복검사 초기화
document.getElementById("userId").addEventListener("input", () => {
    isIdChecked = false;
});
