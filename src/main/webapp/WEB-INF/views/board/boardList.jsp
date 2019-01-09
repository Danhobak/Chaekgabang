<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<h2 id="chaekgabang_board_title">책 추천 게시판</h2>
<table id="chaekgabang_board_listtable">
	<thead>
		<c:if test="${bMemberId != null}">
		<tr id="chaekgabang_board_btncontainer">
			<td colspan="5">
				<button type="button" onclick="javascript:location.href='/board/write';">글 쓰기</button>
			</td>
		</tr>
		</c:if>
		<tr>
			<th>아이디</th>
			<th>제목</th>
			<th>작성자</th>
			<th>등록일</th>
			<c:if test="${bMemberId == 'admin'}">
				<th></th>
			</c:if>
		</tr>
	</thead>
	<tbody>
		<c:if test="${bdList != null && bdList.size() > 0}">
		<c:forEach var="listitem" items="${bdList}" begin="${start-1}"
			end="${bdList.size() - (start-1) >= display ? start + display - 2 : bdList.size()-1}"
			varStatus="status">
			<tr class="bbs_list_item"
				onclick="javascript:location.href='/board/info?bd_id=${listitem.bd_id}';">
				<fmt:parseDate var="test" value="${listitem.bd_regdate}"
					pattern="yyyy-MM-dd HH:mm:ss" />
				<td>${listitem.bd_id}</td>
				<td>${listitem.bd_title}</td>
				<td>${listitem.m_id}</td>
				<td><fmt:formatDate value="${test}" pattern="yy.MM.dd / HH:mm" /></td>
				<c:if test="${bMemberId == 'admin'}">
					<td><button type="button" onclick="admin_delete(event, '${listitem.bd_id}', '${listitem.m_id}', '${listitem.bd_filePath}');">삭제</button></td>
				</c:if>
			</tr>
		</c:forEach>
		</c:if>
	</tbody>
</table>
<c:if test="${bdList != null}">
	<div style="text-align: center; margin-top: 20px;">
		<c:if test="${stdNum > 0}">
			<button type="button"
				onclick="javascript:searchBoard(${((stdNum - 1) * divPageNum * display)+1});">이전
				페이지</button>
		</c:if>
		<c:forEach var="i" begin="1" end="${display}">
			<c:set var="pageCount" value="${i+10*stdNum}" />
			<c:set var="startPageNum" value="${((pageCount - 1) * divPageNum)+1}" />
			<c:choose>
				<c:when test="${pageCount <= totalPage}">
					<c:set var="activeClass" value="" />
					<c:if test="${start == startPageNum}">
						<c:set var="activeClass" value=" active" />
					</c:if>
					<a class="pager_num${activeClass}"
						href="javascript:searchBoard(${startPageNum});">${pageCount}</a>
				</c:when>
			</c:choose>
		</c:forEach>
		<c:if test="${(stdNum + 1) * divPageNum < totalPage}">
			<button type="button"
				onclick="javascript:searchBook(${((stdNum + 1) * divPageNum * display)+1});">다음
				페이지</button>
		</c:if>
	</div>
</c:if>