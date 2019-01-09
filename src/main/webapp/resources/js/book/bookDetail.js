$(function(){
	var MAX_INPUT_REVIEWCONTENT = 10; // 1000바이트 미만으로 쳐야 함.
	$("#book_detail_comparepricebtn").click(function(){
		var bid = $(this).val();
	});
	
	$("#book_detail_writereviewbtn").click(function(){
		//var reviewContentContainer = $("#book_detail_reviewcontent_container");
		var br_refuser = $("#book_detail_memberid").val();
		var br_refbook = $("#book_detail_bookisbn").val();	
		var br_title = $("#book_detail_reviewtitle").val();
		var br_content = $("#book_detail_reviewcontent").val();
		
		$.ajax({
			'url': '/book/writereview',
			'type': 'post',
			'dataType': 'json',
			'data': {
				'br_refuser': br_refuser,
				'br_refbook': br_refbook,
				'br_title': br_title,
				'br_content': br_content
			},
			'success': function(data){
				if(data.result === 'success') {
					requestBookReviewList(1);
				} else {
					alert("리뷰 작성에 실패하였습니다.");
				}
			}
		});
	});
	
	/*
	 * $("#book_detail_reviewcontent").on('change keydown',function(){ var
	 * curText = $(this).val(); var curByte = getByteLength(curText); if(curByte >
	 * MAX_INPUT_REVIEWCONTENT) { var text = MAX_INPUT_REVIEWCONTENT+"바이트 이상 입력
	 * 불가합니다."; $(this).val(curText); return false; } });
	 */
});

function putBasketBook() {
	var sendData = [ {
		'b_title' : $("#book_detail_infotitle").text(),
		'b_image' : $("#book_detail_bookimg").attr('src'),
		'b_author' : $("#book_detail_bookauthors").val(),
		'b_publisher' : $("#book_detail_bookpublisher").text(),
		'b_pubdate' : $("#book_detail_bookpubdate").text(),
		'b_isbn' : $("#book_detail_info_isbn").text(),
		'b_description' : $("#book_detail_info_description").text(),
		'b_price' : $("#book_detail_info_price").text(),
		'b_discount' : $("#book_detail_info_discount").text(),
		'b_id' : $("#book_detail_info_comparepricebtn").val()
	}];
	$.ajax({
		'url':'/book/myBookAdd',
		'type':'post',
		'data':{
			'bookList':JSON.stringify(sendData)
		},
		'success':function(html){
			$("body").append(html);
		}
	});
}

function requestBookReviewList(start) {
	var b_isbn = $("#book_detail_info_isbn").text();
	$.ajax({
		'url': '/book/reviewlist',
		'type': 'post',
		'dataType': 'json',
		'data': {
			'start': start,
			'b_isbn': b_isbn
		},
		'success': function(data){
			//console.log(JSON.stringify(data));
			if(data !== null){
				var stdNum = data.stdNum;
				var display = data.display;
				var totalPage = data.totalPage;
				var divPageNum = data.divPageNum;
				var items = data.items;
				var $reviewList = $("#book_detail_review_container");
				var memberid = $("#book_detail_memberid").val(); 
				var reviewItemHtml = "";
				var reviewPagerHtml = "";
				
				$reviewList.empty();
				
				for(var i = 0, j = items.length; i < j; i++){
					var review = items[i];
					var review_id = review.userid;
					reviewItemHtml = "<li class='review_item'>";
					reviewItemHtml += "<input class='review_item_title' type='text' value='"+review.title+"' readonly>";
					reviewItemHtml += "<textarea class='review_item_content' readonly>"+review.content+"</textarea>";
					reviewItemHtml += "<div class='review_item_writeinfo'>";
					reviewItemHtml += "<div>";
					reviewItemHtml += "<span class='review_item_writer'><span>작성자: </span><span class='review_item_writer_value'>"+review_id+"</span></span>";
					reviewItemHtml += "</div>";
					reviewItemHtml += "<div>";
					reviewItemHtml += "<span class='review_item_writetime'><span>작성시간: </span><span>"+review.regdate+"</span></span>";
					if(memberid === review_id || memberid === "admin") {
						reviewItemHtml += "<button class='review_item_modifybtn' type='button' style='margin-left:10px;' onclick='changeReviewModifyList(this, \"ready\");'></button>";
						reviewItemHtml += "<button class='review_item_executebtn' type='button' style='margin-left:10px;' onclick='executeReviewBtnControl(this);'></button>";
					}
					reviewItemHtml += "</div>";
					reviewItemHtml += "</div>";
					reviewItemHtml += "</li>";
					$reviewList.append(reviewItemHtml);
				}
				reviewPagerHtml += "<li class='pager' style='text-align:center;'>";
				if (stdNum > 0) {
					reviewPagerHtml += "<button type='button' onclick='requestBookReviewList("+(((stdNum - 1) * divPageNum * display)+1)+");'>이전 페이지</button>";
				}
				for (var i = 1; i <= display; i++) {
					var pageCount = i + 10 * stdNum;
					var startPageNum = ((pageCount - 1) * divPageNum)+1;
					if (pageCount === totalPage) {
						var activeClass = "";
						if (start === startPageNum)
							activeClass = "active";
						reviewPagerHtml += "<a class='pager_num "+ activeClass + "' href='javascript:requestBookReviewList("+startPageNum+");'>" + pageCount+ "</a>";
					} else
						break;
				}
				if ((stdNum + 1) * divPageNum < totalPage) {
					reviewPagerHtml += "<button type='button' onclick='requestBookReviewList("+(((stdNum + 1) * divPageNum * display)+1)+");'>다음 페이지</button>";
				}
				reviewPagerHtml += "</li>";
				$reviewList.append(reviewPagerHtml);
			}
		},
		'error': function(){
			alert("리뷰 리스트 요청에 실패하였습니다.");
		}
	});
}

// 글자 바이트 크기 구하기
function getByteLength(s,b,i,c){
    for(b=i=0;c=s.charCodeAt(i++);b+=c>>11?3:c>>7?2:1);
    return b;
}

//type = ready, cancel;
function changeReviewModifyList(obj, type) {
	var $reviewItem = $(obj).parents(".review_item");
	var $reviewTitle = $reviewItem.find(".review_item_title");
	var $reviewContent = $reviewItem.find(".review_item_content");
	var $reviewModifyBtn = $reviewItem.find(".review_item_modifybtn");
	var $reviewExecuteBtn = $reviewItem.find(".review_item_executebtn");
	
	var _changeReviewModifyList = function(){
		$reviewTitle.toggleClass("active");
		$reviewTitle.prop("readonly", !$reviewTitle.prop("readonly"));
		$reviewContent.toggleClass("active");
		$reviewContent.prop("readonly", !$reviewContent.prop("readonly"));
		$reviewModifyBtn.toggleClass("active");
		$reviewExecuteBtn.toggleClass("cancel");
	};
	
	if(type === "cancel") { // 취소 버튼을 누르면 이전에 작성되어있던 타이틀, 내용으로 돌려짐
		$reviewTitle.val($reviewTitle.data("title_value"));
		$reviewContent.val($reviewContent.data("content_value"));
		_changeReviewModifyList();
	} else {
		if($reviewModifyBtn.hasClass("active")) { //리뷰 수정 활성화 상태에서 버튼을 다시 누르면(수정 완료 처리)
			var memberId = $("#book_detail_memberid").val();
			var m_id = memberId === "admin" ? $(obj).parents(".review_item").find(".review_item_writer_value").text() : memberId;
			var bookIsbn = $("#book_detail_bookisbn").val();
			request_review_change(m_id, bookIsbn, $reviewTitle.val(), $reviewContent.val(), null, _changeReviewModifyList);
		} else {
			$reviewTitle.data("title_value", $reviewTitle.val());
			$reviewContent.data("content_value", $reviewContent.val());
			_changeReviewModifyList();
		}
	}
}

function executeReviewBtnControl(obj) {
	if($(obj).hasClass("cancel")) { //리뷰 수정 활성화 상태일 때는 취소가 된다.
		changeReviewModifyList(obj, "cancel");
	} else {
		request_review_delete(obj);
	}
}

function request_review_delete(obj){
	var memberId = $("#book_detail_memberid").val();
	var m_id = memberId === "admin" ? $(obj).parents(".review_item").find(".review_item_writer_value").text() : memberId;
	var b_isbn = $("#book_detail_info_isbn").text();
	
	$.ajax({
		'url' : '/book/deletereview',
		'type' : 'post',
		'dataType': 'json',
		'data': {
			'm_id': m_id,
			'b_isbn': b_isbn
		},
		'success': function(data){
			if(data !== null)
				requestBookReviewList(1);
		}
	});
}

function request_review_change(m_id, b_isbn, b_title, b_content, success, fail) {
	$.ajax({
		'url': '/book/reviewchange',
		'type': 'post',
		'dataType': 'json',
		'data': {
			'br_refuser': m_id,
			'br_refbook': b_isbn,
			'br_title': b_title,
			'br_content': b_content
		},
		'success': function(data){
			if(data != null)
				requestBookReviewList(1);
		}
	});
}