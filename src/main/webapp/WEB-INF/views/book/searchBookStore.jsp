<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript" src="https://dapi.kakao.com/v2/maps/sdk.js?appkey=e1bad08ca930d2fa51353eec33280830&libraries=services"></script>
<script type="text/javascript" src="/resources/js/book/searchBookStore.js"></script>
<ul id="chaekgabang_search_bookstore_uilist">
	<li>
		<input type="text" id="chaekgabang_search_bookstore_searchinput" placeholder="검색하실 서점이름을 입력해주세요.">
		<input type="button" id="chaekgabang_search_bookstore_searchbtn" value="검색">
	</li>
	<li>
		<div id="chaekgabang_search_bookstore_map" style="width: 800px; height: 400px; margin:0px auto;">
		
		</div>
	</li>
</ul>