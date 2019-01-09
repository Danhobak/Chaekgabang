package com.soldesk.chaekgabang.board;

import java.util.List;

/**
 * Desc :  MemberMapper.xml 과 연동되는 인터페이스 Class
 * @Class Name : BoardMapper
 */

public interface BoardMapper {
	public List<BoardDTO> getBoardList();	//게시물 목록 불러오기
	public BoardDTO getBoardInfo(int bd_id);	//게시물 내용 상세 보기
	public int BoardWrite(BoardDTO bdDTO);	//게시물 등록
	public int BoardDelete(BoardDTO bdDTO);	//게시물 삭제
	public int BoardUpdate(BoardDTO bdDTO);	//게시물 수정
}
