<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Member List</title>
<link rel="stylesheet" href="/resources/css/member/memberList.css">
<script type="text/javascript"
	src="/resources/js/lib/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
	jQuery(function() {
		if(${bMemberId == 'admin'}) { //jstl을 자바스크립트 내부에 사용하여 관리자가 아니면 접근 불가 처리
			alert("관리자님 반갑습니다 :)");
			$(".member_list_deletebtn").on("click", function() {
				var value = $(this).val();
				var isDelete = confirm("정말로 삭제하시겠습니까?");
				if (isDelete) {
					$.ajax({
						'url' : '/member/delete',
						'type' : 'post',
						'data' : {
							'm_id' : $(this).val()
						},
						'success' : function(data) {
							var result = parseInt(data);
							if (result === 1) {
								location.href = "/member/list";
							} else {
								alert("삭제에 실패하였습니다.");
							}
						},
						'error' : function() {
							alert("삭제에 실패하였습니다.");
						}
					});
				}
			});
		} else {
			alert("잘못된 접근시도 입니다.");
			history.back();
		}
	});
</script>
</head>
<body>
	<c:if test="${bMemberId == 'admin'}">
		<h3 style="text-align: center;">회원 정보 리스트</h3>
		<div style="text-align: center;">
			<table id="member_list_table">
				<thead>
					<tr>
						<th>아이디</th>
						<th>이름</th>
						<th>이메일</th>
						<th>나이</th>
						<th>전화번호</th>
						<th>삭제</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="member" items="${memberList}">
						<tr>
							<td>${member.m_id}</td>
							<td>${member.m_name}</td>
							<td>${member.m_email}</td>
							<td>${member.m_age}</td>
							<td>${member.m_tel}</td>
							<td><button class="member_list_deletebtn" type="button"
									style="width: 100%;" value="${member.m_id}">삭제</button></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</c:if>
</body>
</html>