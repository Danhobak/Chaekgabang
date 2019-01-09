//아이디 체크여부 확인 
var idck = 0;
$(function() {
	//idck 버튼을 클릭했을 때 
	$("#idck").click(function() {
		//userid 를 param.
		var userid = $("#m_id").val();

		if(!userid || userid.length == 0){
			alert("아이디를 입력하세요!");
		}else{
			$.ajax({					// ajax로 비동기 요청 처리
				async : true,
				type : 'POST',
				data : {
					"id" : userid
				},
				url : "/idcheck",		// controller의 /idcheck로 요청 전달
				success : function(cnt) {
					cnt = parseInt(cnt);
					if (cnt > 0) {		// (아이디 중복일 경우 = 0 , 중복이 아닐경우 = 1 )
						alert("아이디가 존재합니다. 다른 아이디를 입력해주세요.");
						$("#userid").focus();
					} else {
						alert("사용가능한 아이디입니다.");
						$("#userpwd").focus();
						idck = 1;		//아이디가 중복하지 않으면  idck = 1
					}
				},
				error : function(xhr, status, error) {			// xhr : XMLhttprequest객체(Ajax)관련 자바스크립트 객체
					alert("error : " + error + ", " + status);	// error : error msg, status : error status
				}
			});
		}
	});
});

//유효성 검사 체크 함수
function checkJoinValidity() {
	var validOK = true;
	var validChecks = {
		id : {
			result : /^\S{4,8}$/.test($("#m_id").val()),
			errormsg : "공백없이 4~8자를 입력하세요."
		},
		password : {
			result : /^\S{4,12}$/.test($("#m_password").val()),
			errormsg : "패스워드는 4자~12자로 입력해주세요."
		},
		email : {
			result : /^[\S]+@[\S]+\.[a-zA-Z]+/.test($("#m_email").val()),
			errormsg : "메일형식을 준수하여 작성해주세요."
		},
		name : {
			result : /^\S{2,6}$/.test($("#m_name").val()),
			errormsg : "이름은 2~6자로 입력해주세요."
		},
		age : {
			result : /^\d{1,3}$/.test($("#m_age").val()),
			errormsg : "나이는 숫자만 입력가능합니다."
		},
		tel : {
			result : /^\d{10,11}$/.test($("#m_tel").val()),
			errormsg : "'-'없이 숫자만 입력해주세요."
		}
	};
	var validInfo = null;
	var $validErrMsg = null;
	for(var key in validChecks) {
		validInfo = validChecks[key];
		$validErrMsg = $("#m_"+key).parent("div").next(".errMsg");
		if(!validInfo.result) {
			if(validOK)
				validOK = false;
			$validErrMsg.html("<span>"+validInfo.errormsg+"</span>");
		} else {
			$validErrMsg.html("");
		}
	}
	if(validOK)
		$("#chaekgabang_join_inputform").submit();
}