//유효성 검사 체크 함수
function checkUpdateValidity() {
	var validOK = true;
	var validChecks = {
		password : {
			result : /^\S{4,12}$/.test($("#chaekgabang_update_inputpassword").val()),
			errormsg : "패스워드는 4자~12자로 입력해주세요."
		},
		email : {
			result : /^[\S]+@[\S]+\.[a-zA-Z]+/.test($("#chaekgabang_update_inputemail").val()),
			errormsg : "메일형식을 준수하여 작성해주세요."
		},
		name : {
			result : /^\S{2,6}$/.test($("#chaekgabang_update_inputname").val()),
			errormsg : "이름은 2~6자로 입력해주세요."
		},
		age : {
			result : /^\d{1,3}$/.test($("#chaekgabang_update_inputage").val()),
			errormsg : "나이는 숫자만 입력가능합니다."
		},
		tel : {
			result : /^\d{10,11}$/.test($("#chaekgabang_update_inputtel").val()),
			errormsg : "'-'없이 숫자만 입력해주세요."
		}
	};
	var validInfo = null;
	var $validErrMsg = null;
	for(var key in validChecks) {
		validInfo = validChecks[key];
		$validErrMsg = $("#chaekgabang_update_input"+key).nextAll(".errMsg");
		if(!validInfo.result) {
			if(validOK)
				validOK = false;
			$validErrMsg.html("<span>"+validInfo.errormsg+"</span>");
		} else {
			$validErrMsg.html("");
		}
	}
	if(validOK)
		$("#chaekgabang_update_inputform").submit();
}