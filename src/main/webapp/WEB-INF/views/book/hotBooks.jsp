<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<ul id="hot_bookList_content">
	<li class="hot_book_top_five"><span>회원님들이 가장 많이 찜한 책 TOP5!</span></li>
	<li>
		<table>
			<tr>
				<c:forEach var="book" items="${hotBooks}" varStatus="status">
					<!-- begin -->
					<td class="hot_book_item">
						<div class="hot_book_item_title_rank">${status.index+1}</div>
						<a  href="#" onclick="javascript:bookDetail(this)">
							<img class="hot_book_item_img" src="${book.b_image}">
						</a>
						<ul class="hot_book_item_contents">
							<li class="hot_book_item_title">
								<a class="hot_book_item_title_value" href="#" onclick="javascript:bookDetail(this)">${book.b_title}</a>
							</li>
							<li class="hot_book_item_isbn">
								<input type="hidden" class="hot_book_item_isbn_value" value="${book.b_isbn}">
							</li>
							<li class="hot_book_item_publishinfo">
								<c:set var="authors" value="${fn:split(book.b_author,'|')}" /> 
								<c:forEach var="author" items="${authors}" varStatus="status">
									<span>${author}</span>
									<c:if test="${status.last == false}">
										<span> | </span>
									</c:if>
								</c:forEach>
							</li>
						</ul>
						<ul class="hoat_book_item_compareprice">
							<li>
								<span style="color: gray; text-decoration: line-through; font-size: 12px;">${book.b_price}</span>
							</li>
							<li>
								<span style="color: red; font-size: 14px;">${book.b_discount}</span>
							</li>
							<li>
								<button type="button" value="${book.b_id}" onclick="javascript:comparePrice(this);">가격 비교</button>
							</li>
						</ul>
					</td>
				</c:forEach>
			</tr>
		</table>
	</li>
</ul>