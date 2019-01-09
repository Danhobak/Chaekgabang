<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<ul id="book_basket_list_content">
	<li style="text-align:left;">
		<span>내가 가방에 넣은 책</span> <span>&lt;${myBookList == null ? 0 : myBookList.size()}&gt;</span>
	</li>
	<c:if test="${myBookList != null && myBookList.size() > 0}">
		<li style="text-align:left;">
			<button type="button" id="book_basket_list_all_toggleselectbtn"
				value="${myBookList != null ? 'active': ''}"></button>
			<button type="button" id="book_basket_list_deletebtn">선택 삭제</button>
		</li>
		<li>
			<table id="book_basket_list_table">
				<thead>
					<tr>
						<th>선택</th>
						<th colspan="2">책 정보</th>
						<th>최저가 가격비교</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="book" items="${myBookList}" begin="${start-1}" end="${myBookList.size() - (start-1) >= display ? start + display - 2 : myBookList.size()-1}">
						<tr class="book_item">
							<td><input type="checkbox" class="book_item_check">
							</td>
							<td><img class="book_item_img" src="${book.b_image}">
							</td>
							<td>
								<ul class="book_item_contents">
									<li class="book_item_title">
										<a class="book_item_title_value" href="#" onclick="javascript:bookDetail(this)">${book.b_title}</a>
									</li>
									<li class="book_item_publishinfo">
										<c:set var="authors" value="${fn:split(book.b_author,'|')}" />
										<c:forEach var="author" items="${authors}" varStatus="status">
											<span>${author}</span>
											<c:if test="${status.last == false}">
												<span> | </span>
											</c:if>
										</c:forEach>
										<span> | </span>
										<span>${book.b_publisher}</span>
										<span> | </span>
										<span>${book.b_pubdate}</span>
									</li>
									<li class="book_item_isbn">
										<label>ISBN: </label>
										<span class="book_item_isbn_value">${book.b_isbn}</span>
									</li>
								</ul>
							</td>
							<td>
								<ul class="book_item_compareprice">
									<li><span>최저가</span></li>
									<li><span
										style="color: gray; text-decoration: line-through; font-size: 12px;">${book.b_price}</span>
									</li>
									<li><span style="color: red; font-size: 14px;">${book.b_discount}</span>
									</li>
									<li>
										<button type="button" value="${book.b_id}"
											onclick="javascript:comparePrice(this);">가격 비교</button>
									</li>
								</ul>
							</td>
						</tr>
					</c:forEach>
					<tr>
						<td id="book_basket_list_pager" style="text-align: center;"
							colspan="4">
							<form id="book_basket_list_pagerform" style="display: none;"
								method="get" action="/book/myBookList">
								<input type="hidden" name="id" value="${bMemberId}"> <input
									type="hidden" id="book_basket_list_startPageNum" name="start">
							</form> <c:if test="${stdNum > 0}">
								<button type="button"
									onclick="javascript:searchMyBook(${((stdNum - 1) * divPageNum * display)+1});">이전
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
											href="javascript:searchMyBook(${startPageNum});">${pageCount}</a>
									</c:when>
								</c:choose>
							</c:forEach> <c:if test="${(stdNum + 1) * divPageNum < totalPage}">
								<button type="button"
									onclick="javascript:searchMyBook(${((stdNum + 1) * divPageNum * display)+1});">다음
									페이지</button>
							</c:if>
						</td>
					</tr>
				</tbody>
			</table>
		</li>
	</c:if>
</ul>