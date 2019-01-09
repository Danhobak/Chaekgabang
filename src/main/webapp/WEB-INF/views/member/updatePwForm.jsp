<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>멤버 비밀번호 수정</title>
<link rel="stylesheet" href="/resources/css/member/updatePwForm.css">
<script type="text/javascript"
	src="/resources/js/lib/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/resources/js/member/updatePwForm.js"></script>
<script type="text/javascript">
	jQuery(function(){
		if(${bMemberId == null}) {
			alert("잘못된 접근입니다.");
			location.href="/book/main";
		}
	});
</script>
</head>
<body>
	<c:if test="${bMemberId != null}">
		<h1 id="update_pwtitle">UPDATE Password</h1>
	<form id="chaekgabang_update_pw_inputform" action="/member/updatePw" method="post">
		<ul id="chaekgabang_update_pw_inputlist">
			<li>
				<label for="chaekgabang_update_pw_inputid">ID: </label>
				<input type="text" id="chaekgabang_update_pw_inputid" name="m_id" value="${bMemberId}" readonly>
				<p class="errMsg"></p>
			</li>
			<li>
				<label for="chaekgabang_update_pw_inputpw">PASSWORD: </label>
				<input type="password" id="chaekgabang_update_pw_inputpw" name="m_password">
				<p class="errMsg"></p>
			</li>
			<li>
				<label for="chaekgabang_update_pw_input_newpw">NEW P.W: </label>
				<input type="password" id="chaekgabang_update_pw_inputnewpw" name="m_newpw">
				<p class="errMsg"></p>
			</li>
			<li>
				<label for="chaekgabang_update_pw_input_confirmpw">CONFIRM P.W: </label>
				<input type="password" id="chaekgabang_update_pw_inputconfirmpw">
				<p class="errMsg"></p>
			</li>
			<li>
				<input class="update_PwPageBtns" type="button" value="UPDATE" onclick="javascript:checkUpdatePwValidity();">
				&nbsp;&nbsp;
				<input class="update_PwPageBtns" type="button" value="CANCEL" onclick="javascript:history.back();">
			</li>
		</ul>
	</form>
	</c:if>
</body>
</html>