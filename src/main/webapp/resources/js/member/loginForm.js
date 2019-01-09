/**
 * Desc : Login페이지 로그인 시도할 경우 모든 input field가 채워져있는지 검사 
 * @returns 둘 중 하나라도 비어있으면 alert() 실행
 */


$(function() {
	$("#login_id_input").focus(); 					//페이지 진입시 ID input field에 focus
	$("#loginBtn").click(function() {

		var $temp1 = $("#login_id_input").val();
		var $temp2 = $("#login_pw_input").val();
		
		if (!($temp1) || !($temp2)) {				//둘 중 하나라도 값이 없다면
			alert("잘못된 ID 또는 PW 입력값입니다 !");
		}
	});
});

