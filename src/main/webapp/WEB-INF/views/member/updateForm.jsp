<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>멤버 정보 수정</title>
<link rel="stylesheet" href="/resources/css/member/updateForm.css">
<script type="text/javascript"
	src="/resources/js/lib/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/resources/js/member/updateForm.js"></script>
<script type="text/javascript">
	jQuery(function(){
		if(${bMemberId == null || memberInfo == null}) {
			alert("잘못된 접근입니다.");
			location.href="/book/main";
		}
	});
</script>
</head>
<body>
	<c:if test="${bMemberId != null && memberInfo != null}">
		<h1 id="updatetitle">UPDATE</h1>
	<form id="chaekgabang_update_inputform" action="/member/update" method="post">
		<ul id="chaekgabang_update_inputlist">
			<li>
				<label for="chaekgabang_update_inputid">ID: </label>
				<input type="text" id="chaekgabang_update_inputid" name="m_id" value="${memberInfo.m_id}" readonly>
				<p class="errMsg"></p>
			</li>
			<li>
				<label for="chaekgabang_update_inputpassword">PASSWORD: </label>
				<input type="password" id="chaekgabang_update_inputpassword" name="m_password">
				<input type="button" id="chaekgabang_update_updatepassbtn" value="Update Password" onclick="javascript:location.href='/member/updatePw';">
				<p class="errMsg"></p>
			</li>
			<li>
				<label for="chaekgabang_update_inputemail">E-MAIL: </label>
				<input type="text" id="chaekgabang_update_inputemail" name="m_email" value="${memberInfo.m_email}">
				<p class="errMsg"></p>
			</li>
			<li>
				<label for="chaekgabang_update_inputname">NAME: </label>
				<input type="text" id="chaekgabang_update_inputname" name="m_name" value="${memberInfo.m_name}">
				<p class="errMsg"></p>
			</li>
			<li>
				<label for="chaekgabang_update_inputage">AGE: </label>
				<input type="text" id="chaekgabang_update_inputage" name="m_age" value="${memberInfo.m_age}">
				<p class="errMsg"></p>
			</li>
			<li>
				<label for="chaekgabang_update_inputtel">TEL: </label>
				<input type="text" id="chaekgabang_update_inputtel" name="m_tel" value="${memberInfo.m_tel}">
				<p class="errMsg"></p>
			</li>
			<li>
				<input class="updatePageBtns" type="button" value="UPDATE" onclick="checkUpdateValidity();">
				&nbsp;&nbsp;
				<input class="updatePageBtns" type="button" value="CANCEL" onclick="javascript:history.back()">
			</li>
		</ul>
	</form>
	</c:if>
</body>
</html>