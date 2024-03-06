$(document).ready(function(){

        $('.datepicker').datepicker({
            format: 'yyyy-mm-dd',
            autoclose: true,
            startDate: '0d',
            todayBtn: "linked",
            todayHighlight: true,
        }).on('changeDate', function(e) {
        // 날짜가 변경될 때 실행될 코드
        var selectedDate = $(this).datepicker('getFormattedDate'); // 선택된 날짜 가져오기
    //  console.log("선택된 날짜: " + selectedDate);
        
        // URL에 날짜 입력 코드
         window.location.href = "/reserve?date=" + encodeURIComponent(selectedDate);
        	});
        
        $('.cell').click(function(){
            $('.cell').removeClass('select');
            $(this).addClass('select');
    	});
        
        });
        
        // 달력날짜 클릭시 활성화 효과
        document.addEventListener('DOMContentLoaded', function() {
        	const timeSlots = document.querySelectorAll('.time-slot'); // 모든 시간 슬롯을 선택
        	const selectedTimeInput = document.getElementById('selectedTime'); // 숨겨진 입력 필드 선택
    	
        	timeSlots.forEach(function(slot) {
				slot.addEventListener('click', function() {
                	const timeValue = this.getAttribute('data-time'); // 클릭된 슬롯의 시간 값을 가져옴
                	selectedTimeInput.value = timeValue; // 숨겨진 입력 필드에 시간 값을 설정
                	console.log(timeValue);
           			});
        		});
        
        	// 비회원 팝업창 닫기		
        	var closeButton = document.querySelector('.close');
    			closeButton.onclick = function() {
        			document.getElementById('loginModal').style.display = 'none';
			};
    			
		});
		
			// 비회원 예약시 팝업창 열기
			function showLoginModal() {
				document.getElementById('loginModal').style.display = 'block';
			}
			
			// 비회원 예약시 이메일 입력하고 제출
			function submitForms() {     	
			    var emailInput = document.getElementById('name') // 새로운 input 요소 생성
			
			    var reserveForm = document.getElementById('reserve'); // reserve 폼 가져오기
			    reserveForm.appendChild(emailInput); // 생성한 input 요소를 reserve 폼에 추가
			    
			    reserveForm.submit(); // reserve 폼 제출
			}

		
		/*	function submitForms() {
				fetch('/join', {
			    method: 'POST',
			    body: new FormData(document.getElementById('loginForm'))
			  })
			  .then(response => {
			    // 첫 번째 폼 제출 성공 후, 두 번째 폼 제출
			    return fetch('/reserve', {
			      method: 'POST',
			      body: new FormData(document.getElementById('reserve'))
			    });
			  })
			  .then(response => {
			    // 두 번째 폼 제출 성공 처리
			    console.log('두 폼 모두 성공적으로 제출됨');
			  })
			  .catch(error => {
			    // 에러 처리
			    console.error('폼 제출 중 에러 발생', error);
			  });
			}
		*/	