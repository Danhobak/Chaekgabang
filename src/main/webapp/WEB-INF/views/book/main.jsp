<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>책가방</title>
<link rel="stylesheet" href="/resources/css/book/main.css">
<link rel="stylesheet" href="/resources/css/book/intro.css">
<link rel="stylesheet" href="/resources/css/book/searchBookStore.css">
<link rel="stylesheet" href="/resources/css/book/bookList.css">
<link rel="stylesheet" href="/resources/css/book/bookDetail.css">
<link rel="stylesheet" href="/resources/css/book/myBookList.css">
<link rel="stylesheet" href="/resources/css/book/hotBooks.css">
<link rel="stylesheet" href="/resources/css/book/tab.css">
<link rel="stylesheet" href="/resources/css/board/boardList.css">
<link rel="stylesheet" href="/resources/css/board/boardInfo.css">
<link rel="stylesheet" href="/resources/css/board/boardWrite.css">
<script type="text/javascript" src="/resources/js/lib/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="/resources/js/book/main.js"></script>
<script type="text/javascript" src="/resources/js/book/myBookList.js"></script>
<script type="text/javascript" src="/resources/js/book/bookDetail.js"></script>
<script type="text/javascript" src="/resources/js/book/tab.js"></script>
<script type="text/javascript" src="/resources/js/board/boardList.js"></script>
<script type="text/javascript" src="/resources/js/board/boardInfo.js"></script>
</head>
<body>
	<header>
		<div id="chaekgabang_main_header_1">
			<div id="chaekgabang_main_imgcontainer" style="cursor:pointer;">
				<img id="chaekgabang_main_img" src="/resources/images/backpack.png">
				<span id="chaekgabang_main_title_txt">책가방</span> 
			</div>
			<div>
				<form id="chaekgabang_book_search_form" method="post" action="/book/search">
					<input type="text" name="query" id="chaekgabang_book_search_input" value="${query}">
					<input type="hidden" name="start" id="chaekgabang_book_search_start" value="1">
					<button type="submit" id="chaekgabang_book_search_btn">검색</button>
				</form>
				<!-- <a href="">상세 검색</a> -->
			</div>
			<div style="position:absolute; right:10px; text-align:right; line-height:70px;">
				<c:if test="${bMemberId != null}">
					<c:choose>
						<c:when test="${bMemberId != 'admin'}">
							<a href="/member/update" style="font-size:14px; vertical-align:middle;">${bMemberId}</a><span style="font-size:13px; vertical-align:middle;">님 환영합니다!</span>
						</c:when>
						<c:otherwise>
							<a style="font-size:14px; vertical-align:middle;">${bMemberId}</a><span style="font-size:13px; vertical-align:middle;">님 환영합니다!</span>
						</c:otherwise>
					</c:choose>
				</c:if>
				<!-- 로그인 중이면 로그아웃 버튼이, 비 로그인 상태면 로그인 버튼이 나옴 -->
				<c:choose>
					<c:when test="${bMemberId == null}">
						<button type="button" id="chaekgabang_book_login_btn" onclick="javascript:location.href='/login';">로그인</button>
					</c:when>
					<c:otherwise>
						<button type="button" id="chaekgabang_book_login_btn" onclick="javascript:location.href='/logout';">로그아웃</button>
					</c:otherwise>
				</c:choose>
				<!-- 관리자 계정이면 회원관리 버튼이 나옴 -->
				<c:if test="${bMemberId == 'admin'}">
					<button type="button" id="chaekgabang_book_management_btn" onclick="javascript:location.href='/member/list';">회원관리</button>
				</c:if>
			</div>
		</div>
		<ul class="navbar">
			<li><a href="/book/intro">소개</a></li>
			<li><a href="/board/list?start=1">책 추천 게시판</a></li>
			<li><a href="/book/searchBookStore">주변 서점 찾기</a></li>
		</ul>
	</header>
	<div class="content">
		<!-- 로그인 한 경우에만 전체 선택 및 전체 선택 해제, 찜하기 가능 -->
		<c:if test="${bMemberId != null && (urlPath == '/book/search')}">
			<div id="chaekgabang_main_booklist_controller">
				<button type="button" id="book_all_toggleselectbtn"
					value="${bookList != null ? 'active': ''}"></button>
				<button type="button" id="book_insertbasketbtn">찜하기</button>
				<button type="button" id=""
					onclick="javascript:location.href='/book/myBookList?id=${bMemberId}&start=1'">찜
					목록 보기</button>
			</div>
		</c:if>
		<c:choose>
			<c:when test="${urlPath == '/book/intro'}">
				<article style="text-align:center;">
					<c:import url="intro.jsp"/>
				</article>
			</c:when>
			<c:when test="${urlPath == '/book/search'}">
				<article>
					<c:import url="bookList.jsp"/>
				</article>
			</c:when>
			<c:when test="${urlPath == '/book/detail'}">
				<article style="text-align:center;">
					<c:import url="bookDetail.jsp"/>
				</article>
			</c:when>
			<c:when test="${urlPath == '/book/myBookList'}">
				<article style="text-align:center;">
					<c:import url="myBookList.jsp"/>
				</article>
			</c:when>
			<c:when test="${urlPath == '/board/list'}">
				<article style="text-align:center;">
					<c:import url="../board/boardList.jsp"/>
				</article>
			</c:when>
			<c:when test="${urlPath == '/board/info'}">
				<article style="text-align:center;">
					<c:import url="../board/boardInfo.jsp"/>
				</article>
			</c:when>
			<c:when test="${urlPath == '/board/update' || urlPath == '/board/write'}">
				<article style="text-align:center;">
					<c:import url="../board/boardWrite.jsp"/>
				</article>
			</c:when>
			<c:when test="${urlPath == '/book/searchBookStore'}">
				<article style="text-align: center;">
					<c:import url="searchBookStore.jsp" />
				</article>
			</c:when>
			<c:when test="${urlPath == '/book/intro'}">
				<article style="text-align: center;">
					<c:import url="intro.jsp" />
				</article>
			</c:when>
			<c:otherwise>
				<article style="text-align:center;">
					<c:import url="hotBooks.jsp"/>
				</article>
			</c:otherwise>
		</c:choose>
	</div>
	<footer>
		<div id="map" style="width:100%;height:400px;"></div>
	</footer>
</body>
</html>