<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div id="book_detail_maincontainer">
	<ul>
		<li class="under_line">
			<h2 id="book_detail_infotitle">${bookInfo.b_title}</h2>
		</li>
		<li>
			<img id="book_detail_bookimg" src="${bookInfo.b_image}">
			<ul id="book_detail_infolist">
				<li class="under_line">
					<span>저자 :</span>
					<c:set var="authors" value="${fn:split(bookInfo.b_author,'|')}" />
					<input type="hidden" id="book_detail_bookauthors" value="${bookInfo.b_author}">
					<c:forEach var="author" items="${authors}" varStatus="status">
						<a href="javascript:searchBook(1,'${author}');">${author}</a>
						<c:if test="${status.last == false}">
							<span>, </span>
						</c:if>
					</c:forEach>
				</li>
				<li class="under_line">
					<span>출판사 :</span>
					<span id="book_detail_bookpublisher">${bookInfo.b_publisher}</span>
				</li>
				<li class="under_line">
					<span>출간일 :</span>
					<span id="book_detail_bookpubdate">${bookInfo.b_pubdate}</span>
				</li>
				<li class="under_line">
					<span>ISBN :</span>
					<span id="book_detail_info_isbn">${bookInfo.b_isbn}</span>
				</li>
				<li class="under_line">
					<span>최저가격 :</span>
					<span id="book_detail_info_price">${bookInfo.b_price}</span>
					<span id="book_detail_info_discount">${bookInfo.b_discount}</span>
					<button type="button" id="book_detail_info_comparepricebtn" value="${bookInfo.b_id}" onclick="javascript:comparePrice(this);">가격 비교</button>
				</li>
				<li class="under_line">
					<button type="button" id="book_detail_info_basketbookbtn" onclick="javascript:putBasketBook();" ${bMemberId == null ? 'disabled' : ''}>찜하기</button>
					<button type="button" id="book_detail_info_basketbooklistbtn" onclick="javascript:location.href='/book/myBookList?id=${bMemberId}&start=1'" ${bMemberId == null ? 'disabled' : ''}>찜 목록 보기</button>
				</li>
			</ul>
		</li>
	</ul>
	<ul class="tabmenu">
		<!-- tab -->
		<li>
			<a href="#link">책 간단 소개</a>
			<ul class="tabcontent">
				<li id="book_detail_info_description">${bookInfo.b_description}</li>
			</ul>
		</li>
		<li><a href="javascript:requestBookReviewList(1);">리뷰</a>
			<ul class="tabcontent">
				<li>
					<c:if test="${bMemberId != null}">
						<ul id="book_detail_reviewcontent_container">
							<li>
								<input id="book_detail_memberid" type="hidden" name="br_refuser" value="${bMemberId}">
								<input id="book_detail_bookisbn" type="hidden" name="br_refbook" value="${bookInfo.b_isbn}">
								<input id="book_detail_reviewtitle" name="br_title" type="text" placeholder="제목">
							</li>
							<li>
								<textarea id="book_detail_reviewcontent" name="br_content" placeholder="로그인 시 리뷰 작성이 가능합니다." maxlength="300"></textarea>
							</li>
							<li style="text-align:right;">
								<button type="button" id="book_detail_writereviewbtn">리뷰등록</button>
							</li>
						</ul>
					</c:if>
					<ul id="book_detail_review_container">
					</ul>
				</li>
			</ul>
		</li>
	</ul>
</div>