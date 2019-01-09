//유효성 검사 체크 함수
function checkUpdatePwValidity() {
	var validOK = true;
	var validChecks = {
		pw : {
			result : /^\S{4,12}$/.test($("#chaekgabang_update_pw_inputpw").val()),
			errormsg : "패스워드는 4자~12자로 입력해주세요."
		},
		newpw : {
			result : /^\S{4,12}$/.test($("#chaekgabang_update_pw_inputnewpw").val()),
			errormsg : "패스워드는 4자~12자로 입력해주세요."
		},
		confirmpw : {
			result : $("#chaekgabang_update_pw_inputnewpw").val() === 
				$("#chaekgabang_update_pw_inputconfirmpw").val() ? true : false,
			errormsg : "입력한 두 패스워드가 다릅니다."
		}
	};
	var validInfo = null;
	var $validErrMsg = null;
	for(var key in validChecks) {
		validInfo = validChecks[key];
		$validErrMsg = $("#chaekgabang_update_pw_input"+key).nextAll(".errMsg");
		if(!validInfo.result) {
			if(validOK)
				validOK = false;
			$validErrMsg.html("<span>"+validInfo.errormsg+"</span>");
		} else {
			$validErrMsg.html("");
		}
	}
	if(validOK)
		$("#chaekgabang_update_pw_inputform").submit();
}