<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<ul id="chaekgabang_main_booklist">
	<c:if test="${bookList != null}">
		<c:forEach var="book" items="${bookList}">
			<li class="book_item">
				<div class="book_item_checkcontainer">
					<input type="checkbox" class="book_item_check">
				</div> <img class="book_item_img" src="${book.b_image}">
				<ul class="book_item_contents">
					<li class="book_item_title"><a class="book_item_title_value"
						href="#" onclick="javascript:bookDetail(this)">${book.b_title}</a>
					</li>
					<li class="book_item_publishinfo"><c:set var="authors"
							value="${fn:split(book.b_author,'|')}" /> <span
						class="book_item_author_value" style="display: none;">${book.b_author}</span>
						<c:forEach var="author" items="${authors}" varStatus="status">
							<a href="javascript:searchBook(1,'${author}');">${author}</a>
							<c:if test="${status.last == false}">
								<span> | </span>
							</c:if>
						</c:forEach> <span> | </span> <a class="book_item_publisher_value"
						href="javascript:searchBook(1,'${book.b_publisher}');">${book.b_publisher}</a>
						<span> | </span> <span class="book_item_pubdate_value">${book.b_pubdate}</span>
					</li>
					<li class="book_item_isbn"><label>ISBN: </label> <span
						class="book_item_isbn_value">${book.b_isbn}</span></li>
					<li class="book_item_description"><span
						class="book_item_description_value">${book.b_description}</span></li>
				</ul>
				<ul class="book_item_compareprice">
					<li><span>최저가</span></li>
					<li><span class="book_item_price_value"
						style="color: gray; text-decoration: line-through; font-size: 12px;">${book.b_price}</span>
					</li>
					<li><span class="book_item_discount_value"
						style="color: red; font-size: 14px;">${book.b_discount}</span></li>
					<li>
						<button class="book_item_comparepricebtn" type="button"
							value="${book.b_id}">가격 비교</button>
					</li>
				</ul>
			</li>
		</c:forEach>
		<li class="pager" style="text-align: center;"><c:if
				test="${stdNum > 0}">
				<button type="button"
					onclick="javascript:searchBook(${((stdNum - 1) * divPageNum * display)+1});">이전
					페이지</button>
			</c:if> <c:forEach var="i" begin="1" end="${display}">
				<c:set var="pageCount" value="${i+10*stdNum}" />
				<c:set var="startPageNum"
					value="${((pageCount - 1) * divPageNum)+1}" />
				<c:choose>
					<c:when test="${pageCount <= totalPage}">
						<c:set var="activeClass" value="" />
						<c:if test="${start == startPageNum}">
							<c:set var="activeClass" value=" active" />
						</c:if>
						<a class="pager_num${activeClass}"
							href="javascript:searchBook(${startPageNum});">${pageCount}</a>
					</c:when>
				</c:choose>
			</c:forEach> <c:if test="${(stdNum + 1) * divPageNum < totalPage}">
				<button type="button"
					onclick="javascript:searchBook(${((stdNum + 1) * divPageNum * display)+1});">다음
					페이지</button>
			</c:if></li>
	</c:if>
</ul>