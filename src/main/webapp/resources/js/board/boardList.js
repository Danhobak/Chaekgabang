function searchBoard(start) {
	location.href = "/board/list?start=" + start;
}

function admin_delete(e, bd_id, m_id, bd_filePath){
	e.stopPropagation();
	$.ajax({
		'url' : '/board/delete',
		'type' : 'post',
		'data' : {
			'bd_id' : bd_id,
			'm_id' : m_id,
			'bd_filePath' : bd_filePath
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