function deleteBoardInfo() {
	var isDelete = confirm("해당 리뷰를 삭제하시겠습니까?");
	if (isDelete) {
		$.ajax({
			'url' : '/board/delete',
			'type' : 'post',
			'data' : {
				'bd_id' : $("#chaekgabang_boardinfo_boardid").val(),
				'm_id' : $("#chaekgabang_boardinfo_memberid").text(),
				'bd_filePath' : $("#chaekgabang_boardinfo_boardimg").attr("src")
			},
			'success' : function() {
				alert("삭제 성공하였습니다.");
				location.href = "/board/list?start=1";
			},
			'error' : function() {
				alert("삭제 실패하였습니다.");
			}
		});
	}
}