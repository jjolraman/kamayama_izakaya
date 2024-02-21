// 아이디, 이메일, 비밀번호 등 관리

// 아이디 중복확인

function validateEmail(email) {
  const regEx = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  return regEx.test(email);
}

function validatePassword(password) {
  const minLength = 8; // 최소 길이
  const maxLength = 20; // 최대 길이
  // 패스워드 검증 로직 (예: 최소 8자, 하나 이상의 숫자 포함 등)
  if (password.length < minLength || password.length > maxLength) {
    alert(
      `비밀번호는 ${minLength}자 이상 ${maxLength}자 이하로 설정해야 합니다.`
    );
    return false; // 검증 실패
  }
}

function validateForm() {
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;

  if (!validateEmail(email)) {
    alert("유효한 이메일 주소를 입력하세요.");
    return false;
  }

  if (!validatePassword(password)) {
    alert("패스워드 규칙에 맞게 입력하세요.");
    return false;
  }

  // 기타 필요한 검증 로직 추가

  return true; // 검증이 모두 통과되면 true 반환
}

// Form submit 이벤트에 연결
document
  .querySelector(".signup-form")
  .addEventListener("submit", function (event) {
    if (!validateForm()) {
      event.preventDefault(); // 유효하지 않으면 form 제출 방지
    }
  });
