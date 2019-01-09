package com.soldesk.chaekgabang.book;

import java.util.List;

/**
 * 
 * Desc : 책 관련 DB 접근 처리를 위한 Mapper 인터페이스
 * @Class Name : BookMapper
 */
public interface BookMapper {
	/**
	 * 
	 * Desc : 내가 찜한 책 리스트를 DB에 요청하는 추상 메소드
	 * @Method Name : getMyBookList
	 * @param m_id : 로그인 한 멤버 ID
	 * @return : 내가 찜한 책 BookDTO객체 리스트
	 */
	public List<BookDTO> getMyBookList(String m_id);

	/**
	 * 
	 * Desc : 내가 찜한 책 리스트 개수를 DB에 요청하는 추상 메소드
	 * @Method Name : getMyBookCount
	 * @param m_id : 로그인 한 멤버 ID
	 * @return : 내가 찜한 책 리스트 개수
	 */
	public int getMyBookCount(String m_id);
	
	/**
	 * 
	 * Desc : 내가 찜한 책 리스트에서 선택한 책을 DB에 삭제 요청하는 추상 메소드
	 * @Method Name : deleteMyBook
	 * @param m_id : 로그인 한 멤버 ID
	 * @param b_isbn : 책 고유 ISBN
	 * @return 요청 처리 후 결과 값 (1:성공, 그외:실패)
	 */
	public int deleteMyBook(String m_id, String b_isbn);

	/**
	 * 
	 * Desc : 선택한 책 리스트를 내가 찜한 책으로 DB에 추가 요청하는 추상 메소드
	 * @Method Name : addMyBook
	 * @param m_id : 로그인 한 멤버 ID
	 * @param b_isbn : 책 고유 ISBN
	 * @return 요청 처리 후 결과 값 (1:성공, 그외:실패)
	 */
	public int addMyBook(String m_id, String b_isbn);
	
	/**
	 * 
	 * Desc : 해당 책이 책 정보 DB에 존재하는지 확인하는 추상 메소드
	 * @Method Name : isExistBook
	 * @param b_isbn : 책 고유 ISBN
	 * @return 요청 처리 후 결과 값 (1:성공, 그외:실패)
	 */
	public int isExistBook(String b_isbn);
	
	/**
	 * 
	 * Desc : 해당 책이 내가 찜한 책인지 DB에 확인하는 추상 메소드
	 * @Method Name : isExistMyBook
	 * @param m_id : 로그인 한 멤버 ID
	 * @param b_isbn : 책 고유 ISBN
	 * @return 요청 처리 후 결과 값 (1:성공, 그외:실패)
	 */
	public int isExistMyBook(String m_id, String b_isbn);
	
	/**
	 * 
	 * Desc : 해당 책을 책 정보 DB에 추가 요청하는 추상 메소드
	 * @Method Name : addBook
	 * @param book : 추가하려는 책의 정보 BookDTO객체
	 */
	public void addBook(BookDTO book);
	
	/**
	 * 
	 * Desc : 해당 책의 찜한 개수를 증가시키기 위해 DB에 요청하는 추상 메소드
	 * @Method Name : favcntUp
	 * @param b_id : 해당 책의 bid
	 */
	public void favcntUp(String b_id);
	
	/**
	 * 
	 * Desc : 해당 책의 리뷰 리스트를 DB에 요청하는 추상 메소드
	 * @Method Name : getReviewList
	 * @param b_isbn : 해당 책의 ISBN번호
	 * @return 리뷰 정보 ReviewDTO객체의 리스트
	 */
	public List<ReviewDTO> getReviewList(String b_isbn);
	
	/**
	 * 
	 * Desc : 리뷰 리스트 DB에 리뷰 추가를 요청하는 추상 메소드
	 * @Method Name : addReview
	 * @param review : 리뷰 정보 ReviewDTO객체
	 * @return 요청 처리 후 결과 값 (1:성공, 그외:실패)
	 */
	public int addReview(ReviewDTO review);
	
	/**
	 * 
	 * Desc : 리뷰 리스트 DB에서 리뷰 제거를 요청하는 추상 메소드
	 * @Method Name : deleteReview
	 * @param m_id : 로그인 한 맴버 ID
	 * @param b_isbn : 해당 책의 ISBN번호
	 * @return 요청 처리 후 결과 값 (1:성공, 그외:실패)
	 */
	public int deleteReview(String m_id, String b_isbn);

	/**
	 * 
	 * Desc : 리뷰 수정을 DB에 요청하는 추상 메소드
	 * @Method Name : changeReview
	 * @param review : 수정하려는 리뷰 정보 ReviewDTO객체
	 * @return 요청 처리 후 결과 값 (1:성공, 그외:실패)
	 */
	public int changeReview(ReviewDTO review);

	/**
	 * 
	 * Desc : 해당 책에 대한 리뷰를 작성한 적이 있는지 DB에 확인요청하는 추상 메소드
	 * @Method Name : reviewOk
	 * @param review : 작성 확인 여부를 하기 위한 리뷰 정보 ReviewDTO객체
	 * @return 해당 책에 내가 작성한 리뷰의 개수
	 */
	public int reviewOk(ReviewDTO review);
	
	/**
	 * 
	 * Desc : 가장 많이 찜한 책 리스트 5개를 DB에 요청하는 추상 메소드
	 * @Method Name : getHotBooks
	 * @return 가장 많이 찜한 책 정보 BookDTO객체 리스트
	 */
	public List<BookDTO> getHotBooks();
}
