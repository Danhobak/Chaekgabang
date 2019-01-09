<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<form id="chaekgabang_board_writeform"
	action="${bdInfo != null ? '/board/update' : '/board/write'}"
	method="post" enctype="multipart/form-data">
	<h2>${bdInfo != null ? '글 수정' : '글 작성'}</h2>
	<input type="hidden" name="bd_id"
		value="${bdInfo != null ? bdInfo.bd_id : ''}">
	<ul id="chaekgabang_board_writelist">
		<li><label for="chaekgabang_board_write_title">제목: </label> <input
			type="text" id="chaekgabang_board_write_title" name="bd_title"
			value="${bdInfo != null ? bdInfo.bd_title : ''}" required></li>
		<li><label for="chaekgabang_board_write_id">작성자: </label> <input
			type="text" id="chaekgabang_board_write_id" name="m_id"
			value="${sessionScope.bMemberId}" readonly required></li>
		<li><label for="chaekgabang_board_write_content">내용: </label> <textarea
				rows="16" cols="31" id="chaekgabang_board_write_content"
				name="bd_content" required>${bdInfo != null ? bdInfo.bd_content : ''}</textarea>
		</li>
		<li><label for="chaekgabang_board_write_bookimg">책 이미지: </label>
			<input type="file" id="chaekgabang_board_write_bookimg"
			name="bd_filePath" required></li>
		<c:if test="${bdInfo != null}">
			<li><label>기존 이미지: </label> <input type="text"
				name="bd_curfilePath" value="${bdInfo.bd_filePath}" readonly>
			</li>
		</c:if>
		<li><input type="submit" value="${bdInfo != null ? '수정' : '작성'}">
			<input type="reset" value="리셋">
			<input type="button" value="뒤로" onclick="javascript:history.back();"></li>
	</ul>
</form>