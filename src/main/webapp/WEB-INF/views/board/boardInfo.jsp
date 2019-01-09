<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h2>상세 리뷰</h2>
<input type="hidden" id="chaekgabang_boardinfo_boardid"
	value="${bdInfo.bd_id}">
<c:if test="${bdInfo != null}">
	<ul id="chaekgabang_boardinfo_infolist">
		<li><label>제목: </label> <span>${bdInfo.bd_title}</span></li>
		<li><label>작성자: </label> <span
			id="chaekgabang_boardinfo_memberid">${bdInfo.m_id}</span></li>
		<li><label>내용: </label>
			<div id="chaekgabang_boardinfo_contentcontainer">
				<img id="chaekgabang_boardinfo_boardimg" src="${bdInfo.bd_filePath}">
				<p>${bdInfo.bd_content}</p>
			</div></li>
		<li><label>등록일: </label> <span>${bdInfo.bd_regdate}</span></li>
		<li><c:if test="${bdInfo.m_id == bMemberId}">
				<button type="button"
					onclick="location.href='/board/update?bd_id=${bdInfo.bd_id}'">수정</button>
				<button type="button" onclick="deleteBoardInfo();">삭제</button>
			</c:if>
			<button type="button" onclick="history.back();">뒤로</button></li>
	</ul>
</c:if>