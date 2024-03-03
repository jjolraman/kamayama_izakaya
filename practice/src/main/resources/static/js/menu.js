
document.addEventListener("DOMContentLoaded", (event) => {
  let menubarHtml = `
          <ul>
              <li><a href="index.html">ホーム</a></li>
              <li><a href="about.html">お店情報</a></li>
              <li><a href="menu.html">メニュー</a></li>
              <li><a href="reserve">ご予約</a></li>
			  <li><a href="contact.html">お問合せ</a></li>
              
              <li>
              <a
                href="javascript:void(0)"
                onclick="openPopup('login')"
                class="popup-btn"
                >ログイン</a
              >
            </li>
          </ul>
          
          
          <ul class="icon">
<li><a href="#"><img src="images/icon_facebook.png" alt="Facebook"></a></li>
<li><a href="#"><img src="images/icon_twitter.png" alt="Twitter"></a></li>
<li><a href="#"><img src="images/icon_instagram.png" alt="Instagram"></a></li>
</ul>
      `;
  // 메뉴바를 추가할 위치의 ID를 선택하여 HTML을 삽입합니다.
  document.getElementById("menubar").innerHTML = menubarHtml;
});

function openPopup(url) {
  window.open(url, "Popup", "width=400,height=400");
}

function preventDefault(event) {
  event.preventDefault();
}
