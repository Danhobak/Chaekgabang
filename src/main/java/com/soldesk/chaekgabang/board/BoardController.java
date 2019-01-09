package com.soldesk.chaekgabang.board;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * Desc : Board 관련 Controller 
 * @Class Name : BoardController
 */

@Controller
public class BoardController {
	
	/**
	 * Desc : 책 추천 게시판 관련 로직 처리를 위한 BoardDAO 객체 변수
	 */
	@Inject
	private BoardDAO bdDAO;
	
	/**
	 * 
	 * Desc : 책 추천 게시판 리스트 가져오는 함수
	 * @Method Name : boardList
	 * @param model : 모델 객체
	 * @param start : 시작 리스트 번호
	 * @return book/main : book/main.jsp 실행
	 */
	@RequestMapping(value = "/board/list", method = RequestMethod.GET)
	public String boardList(Model model, @RequestParam("start") int start) {
		bdDAO.boardList(model, start);
		return "book/main";
	}
	
	/**
	 * 
	 * Desc : 선택한 책 추천 글에 대한 정보를 가져오는 함수
	 * @Method Name : getBoardInfo
	 * @param bd_id : 책 bd_id
	 * @param model : 모델 객체
	 * @return book/main : book/main.jsp 실행
	 */
	@RequestMapping(value = "/board/info", method = RequestMethod.GET)
	public String getBoardInfo(@RequestParam("bd_id") int bd_id, Model model) {	
		bdDAO.boardInfo(model, bd_id);
		return "book/main";
	}
	
	/**
	 * 
	 * Desc : 책 추천 글을 쓰기 위해 작성 페이지를 여는 함수
	 * @Method Name : boardWrite
	 * @param model : 모델 객체
	 * @return book/main : book/main.jsp 실행
	 */
	@RequestMapping(value = "/board/write", method = RequestMethod.GET)
	public String boardWrite(Model model) {
		bdDAO.viewWriteBoard(model);
		return "book/main";
	}
	
	/**
	 * 
	 * Desc : 책 추천을 쓰는 요청을 받아서 BoardDAO에서 처리 후 반환된 주소를 재요청 해주는 함수
	 * @Method Name : boardWrite
	 * @param mp_req : 클라이언트로부터 받은 Multipart Request(첨부 이미지 업로드 처리를 위해서)
	 * @return BoardDAO에서 처리한 후 반환 된 재요청 주소 String값
	 */
	@RequestMapping(value = "/board/write", method = RequestMethod.POST)
	public String boardWrite(MultipartHttpServletRequest mp_req) {
		return bdDAO.writeBoard(mp_req);
	}
	
	/**
	 * 
	 * Desc : 책 추천 글 삭제 요청을 받아서 BoardDAO에서 처리 후 JSONString형태로 반환해 주는 Ajax용 함수
	 * @Method Name : boardDelete
	 * @param req : 요청받은 리퀘스트 정보(HttpServletRequest) 객체 
	 * @param bdDTO : 클라이언트로 부터 받은 책 추천 글 데이터
	 * @return BoardDAO에서 처리한 후 반환 된 JSONString값
	 */
	@RequestMapping(value = "/board/delete", method = RequestMethod.POST)
	public @ResponseBody String boardDelete(HttpServletRequest req, BoardDTO bdDTO) {
		return bdDAO.deleteBoard(req, bdDTO);
	}
	
	/**
	 * 
	 * Desc : 책 추천 글 수정을 위해 수정 페이지를 여는 함수
	 * @Method Name : boardUpdate
	 * @param model : 모델 객체
	 * @param bd_id : 책 bd_id
	 * @return book/main : book/main.jsp 실행
	 */
	@RequestMapping(value = "/board/update", method = RequestMethod.GET)
	public String boardUpdate(Model model, @RequestParam("bd_id") int bd_id) {
		bdDAO.viewUpdateBoard(model, bd_id);
		return "book/main";
	}

	/**
	 * 
	 * Desc : 책 추천 글 수정 요청을 받으면 BoardDAO에서 처리 후 반환된 주소를 재요청 해주는 함수
	 * @Method Name : boardUpdate
	 * @param mp_req : 클라이언트로부터 받은 MultipartRequest(첨부 이미지 업로드 처리를 위해서)
	 * @return BoardDAO에서 처리한 후 반환 된 재요청 주소 String값
	 */
	@RequestMapping(value = "/board/update", method = RequestMethod.POST)
	public String boardUpdate(MultipartHttpServletRequest mp_req) {
		return bdDAO.updateBoard(mp_req);
	}
}
