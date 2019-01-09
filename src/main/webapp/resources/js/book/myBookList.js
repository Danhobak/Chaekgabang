$(function(){
	//전체 리스트 선택/해제 토글 버튼 누를 경우
	$("#book_basket_list_all_toggleselectbtn").click(function(){
		if($(this).val() === "active") {
			var isToggle = $(this).hasClass("checked");
			var $bookListItems = $("#book_basket_list_table").find(".book_item");
			for(var i = 0, j = $bookListItems.length; i < j; i++) {
				var $itemCheck = $bookListItems.find(".book_item_check");
				$itemCheck.prop("checked", !isToggle);
			}
			$(this).toggleClass("checked");
		}
	});
	$("#book_basket_list_deletebtn").click(deleteMyBook);
});

//내 찜 목록 리스트 가져오는 함수
//start = 시작 리스트 번호
function searchMyBook(start){
	$("#book_basket_list_startPageNum").val(start);
	$("#book_basket_list_pagerform").submit();
}

function deleteMyBook(){
	var $myBookList = $("#book_basket_list_table").find(".book_item");
	var myBookListLength = 0;
	var sendData = null;
	if((myBookListLength = $myBookList.length) > 0) {
		var count = 0;
		sendData = [];
		for(var i = 0; i < myBookListLength; i++) {
			var $book = $myBookList.eq(i);
			var isCheck = $book.find(".book_item_check").prop("checked");
			if(isCheck) {
				sendData[count++] = {
					'b_isbn': $book.find(".book_item_isbn_value").text()
				};
			}
		}
		if(sendData.length > 0) {
			$.ajax({
				'url':'/book/myBookDelete',
				'type':'post',
				'data':{
					'myBookList':JSON.stringify(sendData)
				},
				'success':function(html){
					console.log("success");
					$("body").append(html);
				},
				'error':function(){
					alert("삭제에 실패하였습니다.");
				}
			});
		}
	}
}