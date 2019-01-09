package com.soldesk.chaekgabang.board;

/**
 * Desc : 글작성시 넘어오는 정보들을 담기위한 변수들의 집합 Class
 * @Class Name : BoardDTO
 */

public class BoardDTO {
	private int bd_id;	//게시글 ID
	private String bd_title;	//게시물 제목
	private String m_id;		//작성자 ID
	private String bd_content;	//게시물 내용
	private String bd_regdate;	//작성 날짜
	private String bd_filePath;	//업로드될 파일 경로
	
	public int getBd_id() {
		return bd_id;
	}
	public void setBd_id(int bd_id) {
		this.bd_id = bd_id;
	}
	public String getBd_title() {
		return bd_title;
	}
	public void setBd_title(String bd_title) {
		this.bd_title = bd_title;
	}
	public String getM_id() {
		return m_id;
	}
	public void setM_id(String m_id) {
		this.m_id = m_id;
	}
	public String getBd_content() {
		return bd_content;
	}
	public void setBd_content(String bd_content) {
		this.bd_content = bd_content;
	}
	public String getBd_regdate() {
		return bd_regdate;
	}
	public void setBd_regdate(String bd_regdate) {
		this.bd_regdate = bd_regdate;
	}
	public String getBd_filePath() {
		return bd_filePath;
	}
	public void setBd_filePath(String bd_filePath) {
		this.bd_filePath = bd_filePath;
	}
}
