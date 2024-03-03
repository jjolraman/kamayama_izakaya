$(document).ready(function(){

        $('.datepicker').datepicker({
            format: 'yyyy-mm-dd',
            autoclose: true,
            startDate: '0d',
            todayBtn: "linked",
            todayHighlight: true,
        });
        
        $('.cell').click(function(){
            $('.cell').removeClass('select');
            $(this).addClass('select');
        });
        
        });
        
        document.addEventListener('DOMContentLoaded', function() {
        const timeSlots = document.querySelectorAll('.time-slot'); // 모든 시간 슬롯을 선택
        const selectedTimeInput = document.getElementById('selectedTime'); // 숨겨진 입력 필드 선택
    	let reserveValues = [];
    	
        timeSlots.forEach(function(slot) {
            slot.addEventListener('click', function() {
                const timeValue = this.getAttribute('data-time'); // 클릭된 슬롯의 시간 값을 가져옴
                if (!reserveValues.includes(timeValue)) {
                reserveValues.push(timeValue); // 배열에 존재하지 않으면 추가
                selectedTimeInput.value = reserveValues.join(', '); // 숨겨진 입력 필드에 배열의 모든 시간 값을 설정 (예: "09:00, 10:00")
            }
            	console.log(reserveValues);
            });
        });
    });