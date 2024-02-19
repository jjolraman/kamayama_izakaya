// menu.js 파일
document.addEventListener("DOMContentLoaded", (event) => {
  let menubarHtml = `
        <ul>
            <li><a href="index.html">ホーム</a></li>
            <li><a href="about.html">お店情報</a></li>
            <li><a href="menu.html">メニュー</a></li>
            <li><a href="contact.html">ご予約・お問合せ</a></li>
            <li><a href="signup.html">会員登録</a></li>
        </ul>
    `;
  // 메뉴바를 추가할 위치의 ID를 선택하여 HTML을 삽입합니다.
  document.getElementById("menubar").innerHTML = menubarHtml;
});
