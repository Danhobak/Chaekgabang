<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Join</title>
<link rel="stylesheet" href="/resources/css/member/joinForm.css">
<script type="text/javascript"
	src="/resources/js/lib/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/resources/js/member/joinForm.js"></script>
</head>
<body>
	<h1 id="jointitle">JOIN</h1>
	<form:form id="chaekgabang_join_inputform" action="join" method="post" commandName="memberDTO">
		<ul id="chaekgabang_join_inputlist">
			<li>
				<div>
					<span class="guideText">ID :</span>
					<form:input path="m_id" class="textfields" id="m_id" size="50" />
					<button id="idck" type="button">아이디 중복 체크</button>
				</div>
				<div class="errMsg">
					<form:errors path="m_id" cssClass="error" />
				</div>
			</li>
			<li>
				<div>
					<span class="guideText">PASSWORD : </span>
					<form:input path="m_password" class="textfields" id="m_password" size="50"
						type="password" />
				</div>
				<div class="errMsg">
					<form:errors path="m_password" cssClass="error" />
				</div>
			</li>
			<li>
				<div>
					<span class="guideText">EMAIL : </span>
					<form:input path="m_email" class="textfields" id="m_email" size="50" />
				</div>
				<div class="errMsg">
					<form:errors path="m_email" cssClass="error" />
				</div>
			</li>
			<li>
				<div>
					<span class="guideText">NAME : </span>
					<form:input path="m_name" class="textfields" id="m_name" size="50" />
				</div>
				<div class="errMsg">
					<form:errors path="m_name" cssClass="error" />
				</div>
			</li>
			<li>
				<div>
					<span class="guideText">AGE : </span>
					<form:input path="m_age" class="textfields" id="m_age" size="50" />
				</div>
				<div class="errMsg">
					<form:errors path="m_age" cssClass="error" />
				</div>
			</li>
			<li>
				<div>
					<span class="guideText">TEL : </span>
					<form:input path="m_tel" class="textfields" id="m_tel" size="50" />
				</div>
				<div class="errMsg">
					<form:errors path="m_tel" cssClass="error" />
				</div>
			</li>
			<li class="li5">
				<input class="joinPageBtns" type="button" value="JOIN" onclick="checkJoinValidity();">
				&nbsp;&nbsp;
				<input class="joinPageBtns" type="button" value="CANCEL" onclick="javascript:history.back()">
			</li>
		</ul>
	</form:form>
</body>
</html>