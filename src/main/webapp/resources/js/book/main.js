$(function(){
	//메인화면에서 이미지 누를 경우
	$("#chaekgabang_main_imgcontainer").click(function(){
		location.href = "/book/main";
	});
	
	//전체 리스트 선택/해제 토글 버튼 누를 경우
	$("#book_all_toggleselectbtn").click(function(){
		if($(this).val() === "active") {
			var isToggle = $(this).hasClass("checked");
			var $bookListItems = $("#chaekgabang_main_booklist").children("li");
			for(var i = 0, j = $bookListItems.length; i < j; i++) {
				var $itemCheck = $bookListItems.find(".book_item_check");
				$itemCheck.prop("checked", !isToggle);
			}
			$(this).toggleClass("checked");
		}
	});
	
	//찜목록 추가
	$("#book_insertbasketbtn").click(function(){
		var $bookList = $("#chaekgabang_main_booklist").children(".book_item");
		var bookListLength = 0;
		var sendData = null;
		if((bookListLength = $bookList.length) > 0) {
			var count = 0;
			sendData = [];
			for(var i = 0; i < bookListLength; i++) {
				var $book = $bookList.eq(i);
				var isCheck = $book.find(".book_item_check").prop("checked");
				if(isCheck) {
					sendData[count++] = {
						'b_title':$book.find(".book_item_title_value").text(),
						'b_image':$book.find(".book_item_img").attr('src'),
						'b_author': $book.find(".book_item_author_value").text(),
						'b_publisher': $book.find(".book_item_publisher_value").text(),
						'b_pubdate': $book.find(".book_item_pubdate_value").text(),
						'b_isbn': $book.find(".book_item_isbn_value").text(),
						'b_description': $book.find(".book_item_description_value").text(),
						'b_price': $book.find(".book_item_price_value").text(),
						'b_discount': $book.find(".book_item_discount_value").text(),
						'b_id': $book.find(".book_item_comparepricebtn").val()
					};
				}
			}
			if(sendData.length > 0) {
				$.ajax({
					'url':'/book/myBookAdd',
					'type':'post',
					'data':{
						'bookList':JSON.stringify(sendData)
					},
					'success':function(html){
						$("body").append(html);
					},
					'error':function(){
						
					}
				});
			}
		}
	});
	
	//각 리스트 별 가격비교 버튼을 클릭했을 때
	$(".book_item_comparepricebtn").click(function(){
		var bid = $(this).val();
		window.open("https://book.naver.com/bookdb/price.nhn?bid="+bid,"_blank");
	});
});

function comparePrice(obj){
	var bid = $(obj).val();
	window.open("https://book.naver.com/bookdb/price.nhn?bid="+bid,"blank");
}

function bookDetail(obj) {
	var $bookItem = $(obj).parents(".book_item");
	var title = null;
	var isbn = null; 
	
	if($bookItem.length == 0){
		$bookItem = $(obj).parents(".hot_book_item");
		var index = $bookItem.index();
		
		title = removeHtmlTag($bookItem.find(".hot_book_item_title_value").text());
		isbn = $bookItem.find(".hot_book_item_isbn_value").val();
		location.href = "/book/detail?b_title="+title+"&b_isbn="+isbn;
		return false;
	}

	title = removeHtmlTag($bookItem.find(".book_item_title_value").text());
	isbn = $bookItem.find(".book_item_isbn_value").text();
	location.href = "/book/detail?b_title="+title+"&b_isbn="+isbn;
	return false;
}

function searchBook(start, query = null){
	if(query === null)
		query = $("#chaekgabang_book_search_input").val();
	else {
		query = removeHtmlTag(query);
		$("#chaekgabang_book_search_input").val(query);
	}
		
	var pattern = /[\s]/g;
	//공백체크 - 정규식 이용
	if(query.trim() === "") {
		alert("검색어를 입력해주세요.");
	} else {
		$("#chaekgabang_book_search_start").val(start);
		$("#chaekgabang_book_search_form").submit();
	}
}

function removeHtmlTag(text) {
	//text = text.replace(/<br\/>/ig, "\n"); 
	text = text.replace(/<(\/)?([a-zA-Z]*)(\s[a-zA-Z]*=[^>]*)?(\s)*(\/)?>/ig, "");
	return text;
}