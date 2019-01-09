package com.soldesk.chaekgabang.board;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * Desc : 실질 적으로 행동하는 Method Class(DB저장 등..) 
 * @Class Name : BoardDAO
 */
@Service
public class BoardDAO {

	/**
	 * Desc : DB SQL문 처리를 위한 SqlSession 객체 변수
	 */
	@Inject
	private SqlSession sqlSession;

	/**
	 * Desc : 리스트 표시 할 때 보여줄 페이지 개수
	 */
	private final int DIV_PAGE_NUM = 10;

	/**
	 * 
	 * Desc : 책 추천 게시판 리스트를 DB에서 가져오는 함수
	 * 
	 * @Method Name : boardList
	 * @param model : 모델 객체
	 * @param start : 시작 리스트 번호
	 */
	public void boardList(Model model, int start) {
		// DB에서 책 추천 게시판 리스트를 가져온다.
		List<BoardDTO> bdList = sqlSession.getMapper(BoardMapper.class).getBoardList();

		long display = DIV_PAGE_NUM; // 한 페이지당 보여주는 리스트 갯수
		long total = bdList.size(); // 총 페이지 번호
		long totalPage = (long) Math.ceil(total / (double) display); // 총 페이지 수
		long stdNum = (long) ((start - 1) / DIV_PAGE_NUM / display); // 이전 페이지, 다음 페이지 버튼 처리를 위한 기준 수

		// 모델 객체에 클라이언트에 보내줄 속성값들을 추가한다.
		model.addAttribute("stdNum", stdNum);
		model.addAttribute("start", start);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("display", display);
		model.addAttribute("divPageNum", DIV_PAGE_NUM);
		model.addAttribute("bdList", bdList);
		model.addAttribute("urlPath", "/board/list"); // 클라이언트 메인페이지에서 import할 보여줄 jsp페이지 구분을 위한 urlPath값
	}

	/**
	 * Desc : 게시글 내용 보기
	 * 
	 * @Method Name : boardInfo
	 * @param model : 모델 객체
	 * @param bd_id : 게시글 ID
	 */
	public void boardInfo(Model model, int bd_id) {
		BoardDTO bdDTO = sqlSession.getMapper(BoardMapper.class).getBoardInfo(bd_id);
		model.addAttribute("bdInfo", bdDTO);
		model.addAttribute("urlPath", "/board/info");
	}

	/**
	 * Desc : 게시판 글쓰기(게시글 글쓰기 Form으로)
	 * 
	 * @Method Name : viewWriteBoard
	 * @param model : 모델 객체
	 */
	public void viewWriteBoard(Model model) {
		model.addAttribute("urlPath", "/board/write");
	}

	/**
	 * Desc : 게시판 글쓰기(작성이 완료된 게시글 DB에 저장)
	 * 
	 * @Method Name : writeBoard
	 * @param mp_req : 클라이언트로부터 받은 Multipart Request(첨부 이미지 업로드 처리를 위해서)
	 */
	public String writeBoard(MultipartHttpServletRequest mp_req) {
		BoardDTO bdDTO = getBoardDTOAndUploadImg("write", mp_req);
		int result = 0;
		try {
			result = sqlSession.getMapper(BoardMapper.class).BoardWrite(bdDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result != 1) {
			System.out.println("에러발생");
			return "redirect:/board/write";
		} else {
			System.out.println("성공");
			return "redirect:/board/list?start=1";
		}
	}

	/**
	 * Desc : 게시글 내용 수정(게시글 수정 Form으로)
	 * 
	 * @Method Name : viewUpdateBoard
	 * @param model : 모델 객체
	 * @param bd_id : 게시글 ID
	 */
	public void viewUpdateBoard(Model model, int bd_id) {
		BoardDTO bdDTO = sqlSession.getMapper(BoardMapper.class).getBoardInfo(bd_id);
		model.addAttribute("bdInfo", bdDTO);
		model.addAttribute("urlPath", "/board/update");
	}

	/**
	 * Desc : 게시판 내용 수정(수정이 완료된 게시글 DB에 저장)
	 * @Method Name : updateBoard
	 * @param mp_req : 클라이언트로부터 받은 Multipart Request(첨부 이미지 업로드 처리를 위해서)
	 */
	public String updateBoard(MultipartHttpServletRequest mp_req) {
		BoardDTO bdDTO = getBoardDTOAndUploadImg("update", mp_req);
		int result = 0;
		try {
			result = sqlSession.getMapper(BoardMapper.class).BoardUpdate(bdDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result != 1) {
			System.out.println("에러발생");
			return "redirect:/board/list?start=1";
		} else {
			System.out.println("수정성공");
			// 기존에 업로드 된 파일 제거
			removeUploadImg(mp_req, mp_req.getParameter("bd_curfilePath"));
			return "redirect:/board/list?start=1";
		}
	}

	/**
	 * Desc : 게시판 게시글 삭제
	 * @Method Name : deleteBoard
	 * @param req   : 클라이언트로부터 받은 파일삭제
	 * @param bdDTO : BoardDTO 객체
	 */
	public String deleteBoard(HttpServletRequest req, BoardDTO bdDTO) {
		int result = 0;
		try {
			result = sqlSession.getMapper(BoardMapper.class).BoardDelete(bdDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result != 1) {
			System.out.println("에러발생");
			return "error";
		} else {
			System.out.println("삭제성공");
			// 기존에 업로드 된 파일 제거
			removeUploadImg(req, bdDTO.getBd_filePath());
			return "success";
		}
	}

	/**
	 * Desc : 게시글 및 수정글 업로드 파일 경로 및 한글 처리
	 * @Method Name : getBoardDTOAndUploadImg
	 * @param type   : 호출 메소드 구분(게시글 작성인지 게시글 수정인지 구분)
	 * @param mp_req : 클라이언트로부터 받은 Multipart Request(첨부 이미지 업로드 처리를 위해서) 
	 */
	public BoardDTO getBoardDTOAndUploadImg(String type, MultipartHttpServletRequest mp_req) {
		List<MultipartFile> mfs = mp_req.getFiles("bd_filePath");
		String fullPath = mp_req.getSession().getServletContext().getRealPath("resources/upload/");
		String dbPath = "/resources/upload/";
		String newFileName = null;
		String safeFile = null;
		String fileName = null;
		String originFileName = null;

		BoardDTO bdDTO = new BoardDTO();
		if (type.equals("update"))
			bdDTO.setBd_id(Integer.parseInt(mp_req.getParameter("bd_id")));
		bdDTO.setM_id((String) mp_req.getSession().getAttribute("bMemberId"));
		bdDTO.setBd_title(mp_req.getParameter("bd_title"));
		bdDTO.setBd_content(mp_req.getParameter("bd_content"));

		// 멀티 파일 업로드
		for (MultipartFile mf : mfs) {
			try {
				originFileName = mf.getOriginalFilename();
				//인식못하는 특수문자 &와+를 _로 변경하여 처리
				originFileName = originFileName.replaceAll("[&+]", "_");
				// 한글일 경우를 대비하여 UTF-8인코딩
				fileName = URLEncoder.encode(originFileName, "utf-8");
				newFileName = System.currentTimeMillis() + fileName; //
				safeFile = fullPath + newFileName;
				dbPath += newFileName;
				mf.transferTo(new File(safeFile));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		bdDTO.setBd_filePath(dbPath);
		return bdDTO;
	}

	/**
	 * Desc : 게시글 삭제나 수정시 업로드 되어있던 기존 파일 삭제
	 * @Method Name : removeUploadImg
	 * @param req : 요청받은 리퀘스트 정보(HttpServletRequest) 객체 
	 * @param dbPath : DB에 저장된 파일 경로 
	 */
	public boolean removeUploadImg(HttpServletRequest req, String dbPath) {
		dbPath = dbPath.substring(dbPath.indexOf("/"));
		String fullPath = req.getSession().getServletContext().getRealPath(dbPath);
		File file = new File(fullPath);
		boolean isExistFile = file.exists();
		if (isExistFile)
			return file.delete();
		return isExistFile;
	}
}
