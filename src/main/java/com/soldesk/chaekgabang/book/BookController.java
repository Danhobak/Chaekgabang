package com.soldesk.chaekgabang.book;

import java.io.UnsupportedEncodingException;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * Desc : 책 관련 요청을 받기 위한 Controller
 * @Class Name : BookController
 */
@Controller
public class BookController {

	@Inject
	private BookDAO bookDAO;
	
	@Inject
	private ReviewDAO reviewDAO;
	
	/**
	 * 
	 * Desc : 처음 나오는 메인화면 보여주는 함수
	 * @Method Name : main
	 * @param model : 모델 객체
	 * @return book/main : book/main.jsp 실행
	 */
	@RequestMapping(value = "/book/main", method = RequestMethod.GET)
	public String main(Model model) {
		bookDAO.setMainPage(model);
		return "/book/main";
	}
	
	/**
	 * 
	 * Desc : 소개 페이지를 보여주는 함수
	 * @Method Name : showIntro
	 * @param model : 모델 객체
	 * @return book/main : book/main.jsp 실행
	 */
	@RequestMapping(value = "/book/intro")
	public String showIntro(Model model) {
		bookDAO.showIntro(model);
		return "/book/main";
	}
	
	/**
	 * 
	 * Desc : 
	 * @Method Name : searchBookStore
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/book/searchBookStore", method=RequestMethod.GET)
	public String searchBookStore(Model model) {
		bookDAO.searchBookStore(model);
		return "/book/main";
	}

	/**
	 * 
	 * Desc : BookDAO에서 Naver API를 이용한 책 리스트 검색을 수행 한 후  반환된 jsp파일 경로를 리턴하는 함수
	 * @Method Name : searchBook
	 * @param query : 검색할 책 관련 검색어
	 * @param start : 시작 리스트 번호
	 * @param model : 모델 객체
	 * @return BookDAO에서 로직 처리 후 반환된 jsp파일 경로
	 */
	@RequestMapping(value = "/book/search", method = RequestMethod.POST)
	public String searchBook(@RequestParam("query") String query, @RequestParam("start") String start, Model model) {
		return bookDAO.searchBook(query, start, model);
	}

	/**
	 * 
	 * Desc : BookDAO에서 Naver API를 이용한 책 상세정보 검색을 수행 한 후  반환된 jsp파일 경로를 리턴하는 함수
	 * @Method Name : detailBook
	 * @param b_title : 검색할 책 제목
	 * @param b_isbn : 검색할 책 ISBN번호
	 * @param model : 모델 정보 객체
	 * @return BookDAO에서 로직 처리 후 반환된 jsp파일 경로
	 */
	@RequestMapping(value = "/book/detail", method = RequestMethod.GET)
	public String detailBook(@RequestParam("b_title") String b_title, 
			@RequestParam("b_isbn") String b_isbn, Model model) {
		return bookDAO.detailBook(b_title, b_isbn, model);
	}

	/**
	 * 
	 * Desc : 내가 찜한 책 목록 리스트를 가져오는 함수 
	 * @Method Name : getMyBookList
	 * @param model : 모델 객체
	 * @param m_id : 로그인 한 맴버 ID
	 * @param start : 시작 리스트 번호
	 * @return book/main : book/main.jsp 실행
	 */
	@RequestMapping(value = "/book/myBookList", method = RequestMethod.GET)
	public String getMyBookList(Model model, @RequestParam("id") String m_id,
			@RequestParam("start") int start) {
		bookDAO.getMyBookList(model, m_id, start);
		return "/book/main";
	}

	/**
	 * 
	 * Desc : 내가 찜한 책 목록을 삭제한 후 결과를 HTMLString형태로 반환하는 Ajax함수
	 * @Method Name : deleteMyBook
	 * @param request : 클라이언트로 부터 받은 요청 정보 객체
	 * @param myBookListData : 내가 삭제하려고 선택 한 JSONString형태의 책 찜목록 
	 * @return 찜목록 삭제 후 결과 내용이 포함된 HTMLString
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/book/myBookDelete", method = RequestMethod.POST)
	public @ResponseBody String deleteMyBook(HttpServletRequest request,
			@RequestParam("myBookList") String myBookListData) throws UnsupportedEncodingException {
		return bookDAO.deleteMyBooks(request, myBookListData);
	}

	/**
	 * 
	 * Desc : 내가 선택한 책을 찜목록에 추가한 후 해당 결과를 JSONString형태로 반환하는 Ajax함수
	 * @Method Name : addMyBook
	 * @param request : 클라이언트로 부터 받은 요청 정보 객체
	 * @param bookListData : 내가 찜목록에 추가하려고 선택 한 JSONString형태의 책 목록 
	 * @return 찜목록 추가 후 결과 내용이 포함된 HTMLString
	 */
	@RequestMapping(value = "/book/myBookAdd", method = RequestMethod.POST)
	public @ResponseBody String addMyBook(HttpServletRequest request, 
			@RequestParam("bookList") String bookListData) {
		return bookDAO.addBooks(request, bookListData);
	}
	
	/**
	 * 
	 * Desc : 책 상세정보에서 표시되는 책 리뷰 리스트를 JSONString형태로 반환하는 Ajax함수
	 * @Method Name : getReviewList
	 * @param start : 시작 리스트 번호
	 * @param b_isbn : 해당 책의 ISBN번호
	 * @return JSONString형태의 리뷰 리스트
	 */
	@RequestMapping(value="/book/reviewlist", method=RequestMethod.POST)
	public @ResponseBody String getReviewList(@RequestParam("start") int start,
			@RequestParam("b_isbn") String b_isbn) {
		return reviewDAO.getReviewList(start, b_isbn);
	}
	
	/**
	 * 
	 * Desc : 책 리뷰 작성 요청한 후 결과를 JSONString형태로 반환하는 Ajax함수
	 * @Method Name : writeReview
	 * @param review : 작성하려는 리뷰 정보가 담긴 ReviewDTO객체
	 * @return JSONString형태의 리뷰 작성 결과
	 */
	@RequestMapping(value="/book/writereview", method=RequestMethod.POST)
	public @ResponseBody String writeReview(ReviewDTO review) {
		return reviewDAO.writeReview(review);
	}
	
	/**
	 * 
	 * Desc : 책 리뷰 삭제 요청한 후 결과를 JSONString형태로 반환하는 Ajax함수
	 * @Method Name : deleteReview
	 * @param m_id : 로그인 중인 맴버 ID
	 * @param b_isbn : 해당 책의 ISBN번호
	 * @return JSONString형태의 리뷰 삭제 결과
	 */
	@RequestMapping(value="/book/deletereview", method=RequestMethod.POST)
	public @ResponseBody String deleteReview(@RequestParam("m_id") String m_id,
			@RequestParam("b_isbn") String b_isbn) {
		return reviewDAO.deleteReview(m_id, b_isbn);
	}
	
	/**
	 * 
	 * Desc : 책 리뷰 수정을 요청한 후 결과를 JSONString형태로 반환하는 Ajax함수
	 * @Method Name : reviewchange
	 * @param review : 수정하려는 리뷰 정보가 담긴 ReviewDTO객체
	 * @return JSONString형태의 리뷰 수정 결과
	 */
	@RequestMapping(value="/book/reviewchange", method=RequestMethod.POST)
	public @ResponseBody String reviewchange(ReviewDTO review) {
		return reviewDAO.changeReview(review);
	}
}
