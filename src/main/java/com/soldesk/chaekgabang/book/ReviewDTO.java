package com.soldesk.chaekgabang.book;

import java.sql.Date;

/**
 * Desc : 책상세보기에 작성할 수 있는 리뷰 정보 집합 Class
 * @Class Name : ReviewDTO
 */

public class ReviewDTO {
	private int br_id;			//리뷰 번호(리뷰 ID)
	private String br_title;	//리뷰 제목
	private String br_refuser;	//작성자 ID
	private String br_refbook;	//책 고유 ID
	private String br_content;	//리뷰 내용
	private Date br_regdate;	//리뷰 작성일

	public int getBr_id() {
		return br_id;
	}

	public void setBr_id(int br_id) {
		this.br_id = br_id;
	}

	public String getBr_title() {
		return br_title;
	}

	public void setBr_title(String br_title) {
		this.br_title = br_title;
	}

	public String getBr_refuser() {
		return br_refuser;
	}

	public void setBr_refuser(String br_refuser) {
		this.br_refuser = br_refuser;
	}

	public String getBr_refbook() {
		return br_refbook;
	}

	public void setBr_refbook(String br_refbook) {
		this.br_refbook = br_refbook;
	}

	public String getBr_content() {
		return br_content;
	}

	public void setBr_content(String br_content) {
		this.br_content = br_content;
	}

	public Date getBr_regdate() {
		return br_regdate;
	}

	public void setBr_regdate(Date br_regdate) {
		this.br_regdate = br_regdate;
	}
}
