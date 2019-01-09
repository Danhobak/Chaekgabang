<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<link rel="stylesheet" href="/resources/css/member/loginForm.css">
<script type="text/javascript" src="/resources/js/lib/jquery-3.3.1.min.js"></script>
<script src="/resources/js/member/loginForm.js"></script>
</head>
<body>
	<div id="headDiv">
		<h1>Login</h1>
	</div>
	<form action="login" method="post">
		<div id="tdiv">
			<ul>
				<li class="li1">ID :</li>
				<li class="li2"><input type="text" name="m_id" size="50" id="login_id_input"></li>
				<li class="li1">PASSWORD :</li>
				<li class="li2"><input type="password" name="m_password" id="login_pw_input" size="50"></li>
				<li class="li3">
					<input type="submit" value="LOGIN" id="loginBtn" >&nbsp;&nbsp;
					<input type="button" value="JOIN" id="joinBtn" onclick="location.href='join'">
				</li>
			</ul>
		</div>
	</form>
</body>
</html>