package com.soldesk.chaekgabang.book;

/**
 * Desc : 책관련 정보 집합 Class 
 * @Class Name : BookDTO
 */

public class BookDTO {
	private String b_id;			// 책 고유 ID
	private String b_title;			// 책 제목
	private String b_image;			// 책 겉표지 이미지
	private String b_author;		// 저자
	private String b_price;			// 책 가격
	private String b_discount;		// 할인
	private String b_publisher;		// 출판사
	private String b_pubdate;		// 출판 일자
	private String b_isbn;			// 책고유 ID
	private String b_description;	// 줄거리 또는 책에 대한 약식 설명
	private int b_favcnt;			// 찜목록에 등록된 횟수
	//private String[] b_authors;

	

	public String getB_id() {
		return b_id;
	}

	public void setB_id(String b_id) {
		this.b_id = b_id;
	}

	public String getB_title() {
		return b_title;
	}

	public void setB_title(String b_title) {
		this.b_title = b_title;
	}

	public String getB_image() {
		return b_image;
	}

	public void setB_image(String b_image) {
		this.b_image = b_image;
	}

	public String getB_author() {
		return b_author;
	}

	public void setB_author(String b_author) {
		this.b_author = b_author;
	}

	public String getB_price() {
		return b_price;
	}

	public void setB_price(String b_price) {
		this.b_price = b_price;
	}

	public String getB_discount() {
		return b_discount;
	}

	public void setB_discount(String b_discount) {
		this.b_discount = b_discount;
	}
	
	public String getB_publisher() {
		return b_publisher;
	}

	public void setB_publisher(String b_publisher) {
		this.b_publisher = b_publisher;
	}

	public String getB_pubdate() {
		return b_pubdate;
	}

	public void setB_pubdate(String b_pubdate) {
		this.b_pubdate = b_pubdate;
	}

	public String getB_isbn() {
		return b_isbn;
	}

	public void setB_isbn(String b_isbn) {
		this.b_isbn = b_isbn;
	}

	public String getB_description() {
		return b_description;
	}

	public void setB_description(String b_description) {
		this.b_description = b_description;
	}
	public int getB_favcnt() {
		return b_favcnt;
	}

	public void setB_favcnt(int b_favcnt) {
		this.b_favcnt = b_favcnt;
	}
	/*public String[] getB_authors() {
		return b_authors;
	}

	public void setB_authors(String[] authors) {
		this.b_authors = authors;
	}*/
}
