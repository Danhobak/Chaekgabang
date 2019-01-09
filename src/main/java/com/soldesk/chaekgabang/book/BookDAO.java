package com.soldesk.chaekgabang.book;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 *
 * Desc : 책 관련 로직 처리를 위한 Data Access Object
 * @Class Name : BookDAO
 */
@Service
public class BookDAO {
	
	/**
	 * Desc : DB SQL문 처리를 위한 SqlSession 객체 변수
	 */
	@Inject
	private SqlSession sqlSession;
	
	/**
	 * Desc: 네이버 API 애플리케이션 클라이언트 아이디값";
	 */
	private final String NAVER_CLIENT_ID = "VuNrZrcvw71OqxvCEQq8";
	
	/**
	 * Desc: 네이버 API 애플리케이션 클라이언트 시크릿값";
	 */
	private final String NAVER_CLIENT_SECRET = "oty8P01XGM";
	
	/**
	 * Desc : 리스트 표시 할 때 보여줄 페이지 개수
	 */
	private final int DIV_PAGE_NUM = 10;
	
	/**
	 * 
	 * Desc : 메인 페이지 처음화면을 보여줄 때 필요한 데이터를 처리/추가해주는 함수
	 * @Method Name : setMainPage
	 * @param model : 모델 객체
	 */
	public void setMainPage(Model model) {
		//가장 많이 찜 한 책 5권을 가져오는 함수
		List<BookDTO> hotBooks = getHotBooks();
		//원본 이미지링크로 수정
		for(int i=0; i<hotBooks.size(); i++) {		
			String OriginImagePath = hotBooks.get(i).getB_image().replace("type=m1&", "");
			hotBooks.get(i).setB_image(OriginImagePath);
		}	
		model.addAttribute("hotBooks",hotBooks);
		model.addAttribute("urlPath", "/book/main");
	}
	
	/**
	 * 
	 * Desc : 소개 페이지를 보여줄 때 필요한 데이터를 추가해주는 함수
	 * @Method Name : showIntro
	 * @param model : 모델 객체
	 */
	public void showIntro(Model model) {
		model.addAttribute("urlPath", "/book/intro");
	}
	
	/**
	 * 
	 * Desc : 
	 * @Method Name : searchBookStore
	 * @param model
	 */
	public void searchBookStore(Model model) {
		model.addAttribute("urlPath", "/book/searchBookStore");
	}
	
	/**
	 * 
	 * Desc : 네이버 검색 API를 이용하여 검색에 맞는 JSONString형태의 책 리스트를 가져와서 반환하는 함수
	 * @Method Name : searchBook
	 * @param query : 검색할 책 관련 검색어
	 * @param start : 시작 리스트 번호
	 * @param model : 모델 객체
	 * @return 검색한 JSONString형태의 책 리스트
	 */
	public String searchBook(String query, String start, Model model) {
		String returnPath = "redirect:/book/main";
		try {
			//검색할 키워드가 한글일 경우를 생각하여 UTF-8로 인코딩
			query = URLEncoder.encode(query, "UTF-8");
			
			//Naver API를 이용한 요청을 수행
			String apiURL = "https://openapi.naver.com/v1/search/book.json?query=" + query + "&start=" + start;
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", NAVER_CLIENT_ID);
			con.setRequestProperty("X-Naver-Client-Secret", NAVER_CLIENT_SECRET);
			con.setDoOutput(true);
			int responseCode = con.getResponseCode();
			
			if (responseCode == 200) { // 정상 호출
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer sb = new StringBuffer();
				while ((inputLine = br.readLine()) != null) {
					sb.append(inputLine);
				}

				br.close();

				//JSONParsing 처리용 변수들
				JSONParser jp = new JSONParser();
				JSONObject jo = (JSONObject) jp.parse(sb.toString());
				JSONArray ja = (JSONArray) jo.get("items");
				JSONObject info = null;
				
				//BookDTO 리스트 값 입력을 위한 변수들
				ArrayList<BookDTO> bookList = new ArrayList<BookDTO>();
				BookDTO bdto = null;

				long startNum = (Long) jo.get("start"); //시작 리스트 번호
				long total = (Long) jo.get("total"); //총 페이지 번호
				long totalPage = (long)Math.ceil(total / (double)DIV_PAGE_NUM); //총 페이지 수
				long stdNum = (long) ((startNum - 1) / DIV_PAGE_NUM / DIV_PAGE_NUM); //이전 페이지, 다음 페이지 버튼 처리를 위한 기준 수
				
				//Naver API를 이용하여 받은 JSON데이터를 이용하여 BookDTO 리스트에 값 추가
				for (int i = 0, j = ja.size(); i < j; i++) {
					info = (JSONObject) ja.get(i);
					bdto = new BookDTO();
					String linkStr = (String) info.get("link");
					String bid = linkStr.substring(linkStr.indexOf("?bid=") + 5);
					String author = (String)info.get("author");
					/*if(author != null) {
						String[] authors = author.split("\\|");
						if(authors.length > 0)
							bdto.setB_authors(authors);
					}*/
					bdto.setB_title((String) info.get("title"));
					bdto.setB_id(bid);
					bdto.setB_image((String) info.get("image"));
					bdto.setB_author(author);
					bdto.setB_price((String) info.get("price"));
					bdto.setB_discount((String) info.get("discount"));
					bdto.setB_publisher((String) info.get("publisher"));
					bdto.setB_pubdate((String) info.get("pubdate"));
					bdto.setB_isbn((String) info.get("isbn"));
					bdto.setB_description((String) info.get("description"));
					bookList.add(bdto);
				}
				//처리 후 결과를 Model객체에 저장 후, /book/main.jsp에서 보여주기 위해 return 값 변경 
				model.addAttribute("bookList", bookList);
				model.addAttribute("query", URLDecoder.decode(query, "utf-8"));
				model.addAttribute("stdNum", stdNum);
				model.addAttribute("start", startNum);
				model.addAttribute("totalPage", totalPage);
				model.addAttribute("display", DIV_PAGE_NUM);
				model.addAttribute("divPageNum", DIV_PAGE_NUM);
				model.addAttribute("urlPath", "/book/search");
				returnPath = "/book/main";
			} else {
				System.out.println("Error!!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnPath;
	}
	
	/**
	 * 
	 * Desc : 네이버 책 상세정보 API를 이용하여 조건에 맞는 JSONString형태의 책 정보를 가져와서 반환하는 함수
	 * @Method Name : detailBook
	 * @param b_title : 검색할 책 제목
	 * @param b_isbn : 검색할 책 ISBN번호
	 * @param model : 모델 객체
	 * @return 검색한 JSONString형태의 책 상세 정보
	 */
	public String detailBook(String b_title, String b_isbn, Model model) {
		String returnPath = "redirect:/book/main";
		try {
			//검색할 키워드가 한글일 경우를 생각하여 UTF-8로 인코딩
			StringBuffer apiUrlSb = new StringBuffer();
			apiUrlSb.append("https://openapi.naver.com/v1/search/book_adv.json");
			apiUrlSb.append("?d_titl=" + URLEncoder.encode(b_title, "utf-8"));
			apiUrlSb.append("&d_isbn=" + URLEncoder.encode(b_isbn, "utf-8"));
			
			//Naver API를 이용한 요청을 수행
			URL url = new URL(apiUrlSb.toString());
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("X-Naver-Client-Id", NAVER_CLIENT_ID);
			con.setRequestProperty("X-Naver-Client-Secret", NAVER_CLIENT_SECRET);
			con.setDoOutput(true);
			
			int responseCode = con.getResponseCode();
			if (responseCode == 200) { // 정상 호출
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer sb = new StringBuffer();
				while ((inputLine = br.readLine()) != null) {
					sb.append(inputLine);
				}

				br.close();
				
				//JSONParsing 처리용 변수들
				JSONParser jp = new JSONParser();
				JSONObject jo = (JSONObject) jp.parse(sb.toString());
				JSONArray ja = (JSONArray) jo.get("items");
				JSONObject info = (JSONObject) ja.get(0);

				//BookDTO 객체에 값 추가 후, /book/main.jsp에서 보여주기 위해 return 값 변경 
				BookDTO bdto = new BookDTO();
				String linkStr = (String) info.get("link");
				String bid = linkStr.substring(linkStr.indexOf("?bid=") + 5);
				String imageStr = (String) info.get("image");
				String imagePath = imageStr.replace("type=m1&", ""); // 썸네일 이미지를 큰 이미지로 보이게 하기 위해 추가
				String author = (String)info.get("author");
				/*if(author != null) {
					String[] authors = author.split("\\|");
					if(authors.length > 0)
						bdto.setB_authors(authors);
				}*/
				bdto.setB_title((String) info.get("title"));
				bdto.setB_id(bid);
				bdto.setB_image(imagePath);
				bdto.setB_author(author);
				bdto.setB_price((String) info.get("price"));
				bdto.setB_discount((String) info.get("discount"));
				bdto.setB_publisher((String) info.get("publisher"));
				bdto.setB_pubdate((String) info.get("pubdate"));
				bdto.setB_isbn((String) info.get("isbn"));
				bdto.setB_description((String) info.get("description"));
				model.addAttribute("bookInfo", bdto);
				model.addAttribute("urlPath", "/book/detail");
				returnPath = "/book/main";
			} else {
				System.out.println("Error!!! responseCode : "+responseCode);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return returnPath;
	}

	/**
	 * 
	 * Desc : 내가 찜한 책 리스트를 DB에서 가져온 값과 리스트 관련 값들을 세팅하는 함수
	 * @Method Name : getMyBookList
	 * @param model : 모델 객체
	 * @param m_id : 로그인한 맴버 ID
	 * @param start : 시작 리스트 번호
	 */
	public void getMyBookList(Model model, String m_id, int start) {
		List<BookDTO> myBookList = sqlSession.getMapper(BookMapper.class).getMyBookList(m_id);
		
		long display = DIV_PAGE_NUM;//한 페이지당 보여주는 리스트 갯수
		long total = myBookList.size();//총 페이지 번호
		long totalPage = (long)Math.ceil(total / (double)display); //총 페이지 수
		long stdNum = (long) ((start - 1) / DIV_PAGE_NUM / display); //이전 페이지, 다음 페이지 버튼 처리를 위한 기준 수
		
		model.addAttribute("stdNum", stdNum);
		model.addAttribute("start", start);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("display", display);
		model.addAttribute("divPageNum", DIV_PAGE_NUM);

		model.addAttribute("myBookList", myBookList);
		model.addAttribute("urlPath", "/book/myBookList");
	}
	
	/**
	 * 
	 * Desc : 내가 찜한 책 리스트에서 선택한 책들을 DB에서 제거한 후 결과를 HTMLString형태로 반환하는 함수
	 * @Method Name : deleteMyBooks
	 * @param request : 클라이언트에서 요청한 정보 객체
	 * @param myBookListData : JSONString형태의 내가 선택한 삭제할 찜 리스트
	 * @return 제거한 결과를 표시할 HTMLString
	 */
	public String deleteMyBooks(HttpServletRequest request,  
			String myBookListData) {
		StringBuffer resultHtml = new StringBuffer();
		//세션에 저장되어있는 로그인id를 가져옴
		String memberId = (String)request.getSession().getAttribute("bMemberId");
		try {
			//JSONString을 파싱 후, BookDTO 리스트에 파싱한 데이터를 넣고 DB에 삭제를 요청한다.
			JSONParser jp = new JSONParser();
			JSONArray bookList = (JSONArray)jp.parse(myBookListData);
			if(memberId != null) {
				ArrayList<BookDTO> bookDTOList = new ArrayList<BookDTO>();
				BookDTO bookDTO = null;
				JSONObject obj = null;
				for(int i = 0, j = bookList.size(); i < j; i++) {
					obj = (JSONObject)bookList.get(i);
					bookDTO = new BookDTO();
					bookDTO.setB_isbn((String)obj.get("b_isbn"));
					bookDTOList.add(bookDTO);
				}
				
				//DB에 직접적인 삭제를 위해 유틸리티 함수 실행 및 결과 받기
				boolean result = _deleteMyBooks(bookDTOList, memberId);
				
				//삭제 성공 시 성공 결과를, 그 외에는 강제Exception처리 후 실패 결과를 보여준다.
				if(result == true) {
					resultHtml.append("<script>");
					resultHtml.append("alert('삭제에 성공하였습니다');");
					resultHtml.append("location.href='/book/myBookList?id="+memberId+"&start=1'");
					resultHtml.append("</script>");
				} else
					throw new Exception("myBook: 찜 목록 삭제 실패");
			} else
				throw new Exception("myBook: 로그인 상태가 아닙니다.");
		} catch (Exception e) { //실패 결과 처리
			resultHtml.append("<script>");
			resultHtml.append("alert('삭제에 실패하였습니다');");
			resultHtml.append("location.href='/book/myBookList?id="+memberId+"&start=1'");
			resultHtml.append("</script>");
		}
		return resultHtml.toString();
	}
	
	/**
	 * 
	 * Desc : DB에서 선택한 찜 리스트 삭제처리를 위한 유틸리티 함수
	 * @Method Name : _deleteMyBooks
	 * @param myBookList : 삭제 할 찜한 책 정보가 들어있는 BookDTO객체 리스트
	 * @param memberId : 로그인 한 맴버 ID
	 * @return 성공 실패 여부의 boolean값 true/false
	 */
	private boolean _deleteMyBooks(List<BookDTO> myBookList, String memberId) {
		if(myBookList == null)
			return false;
		int myBookListCnt = myBookList.size();
		int curCnt = 0;
		//전체 리스트를 돌면서 순차적으로 데이터를 제거한다.
		while(curCnt < myBookListCnt) {
			BookDTO myBook = myBookList.get(curCnt++);
			try {
				//찜 한 책 하나하나의 데이터를 순차적으로 삭제처리 하기
				//System.out.println("deleteMyBook - m_id: "+memberId+", b_isbn: "+myBook.getB_isbn());
				sqlSession.getMapper(BookMapper.class).deleteMyBook(memberId, myBook.getB_isbn());
			} catch(Exception e) {
				//e.printStackTrace();
				System.out.println("Error My Book: "+myBook.getB_isbn());
			}
		}
		return true;
	}
	
	/**
	 * 
	 * Desc : 내가 선택한 책을 찜 리스트 DB에 추가한 후 결과를 HTMLString형태로 반환하는 함수
	 * @Method Name : addBooks
	 * @param request : 클라이언트에서 요청한 정보 객체
	 * @param bookListData : JSONString형태의 내가 선택한 책 리스트
	 * @return 추가한 결과를 표시할 HTMLString
	 */
	public String addBooks(HttpServletRequest request, String bookListData) {
		StringBuffer resultHtml = new StringBuffer();
		try {
			//세션에 저장되어있는 로그인id를 가져옴
			String memberId = (String)request.getSession().getAttribute("bMemberId");
			if(memberId != null) {
				//JSONString을 파싱 후, BookDTO 리스트에 파싱한 데이터를 넣고 DB에 추가를 요청한다.
				JSONParser jp = new JSONParser();
				JSONArray bookList = (JSONArray)jp.parse(bookListData);
				JSONObject obj = null;
				
				ArrayList<BookDTO> bookDTOList = new ArrayList<BookDTO>();
				BookDTO bookDTO = null;
				
				for(int i = 0, j = bookList.size(); i < j; i++) {
					obj = (JSONObject)bookList.get(i);
					bookDTO = new BookDTO();
					bookDTO.setB_author((String)obj.get("b_author"));
					bookDTO.setB_description((String)obj.get("b_description"));
					bookDTO.setB_discount((String)obj.get("b_discount"));
					bookDTO.setB_id((String)obj.get("b_id"));
					bookDTO.setB_image((String)obj.get("b_image"));
					bookDTO.setB_isbn((String)obj.get("b_isbn"));
					bookDTO.setB_price((String)obj.get("b_price"));
					bookDTO.setB_pubdate((String)obj.get("b_pubdate"));
					bookDTO.setB_publisher((String)obj.get("b_publisher"));
					bookDTO.setB_title((String)obj.get("b_title"));
					bookDTOList.add(bookDTO);
				}
				
				//DB에 직접적인 추가를 위해 유틸리티 함수 실행 및 결과 받기
				_addBooks(bookDTOList, memberId);
				
				//추가 성공 시 성공 결과를, 그 외에는 강제Exception처리 후 실패 결과를 보여준다.
				resultHtml.append("<script id='add_mybook_result_script'>");
				resultHtml.append("var isMoveMyBookList = confirm('찜 목록 추가에 성공하였습니다. 찜 목록 리스트로 이동하시겠습니까?');");
				resultHtml.append("if(isMoveMyBookList)");
				resultHtml.append("location.href='/book/myBookList?id="+memberId+"&start=1';");
				resultHtml.append("else");
				resultHtml.append("$('#add_mybook_result_script').remove()");
				resultHtml.append("</script>");
			} else
				throw new Exception("myBook: 로그인 상태가 아닙니다.");
		} catch (Exception e) { //실패 결과
			//e.printStackTrace();
			resultHtml.append("<script id='add_mybook_result_script'>");
			resultHtml.append("alert('찜 목록 추가에 실패하였습니다.');");
			resultHtml.append("$('#add_mybook_result_script').remove()");
			resultHtml.append("</script>");
		}
		return resultHtml.toString();
	}
	
	/**
	 * 
	 * Desc : DB에 찜 목록 리스트를 추가하기 위한 유틸리티 함수
	 * @Method Name : _addBooks
	 * @param bookList : 추가 할 책 정보가 들어있는 BookDTO객체 리스트
	 * @param memberId : 로그인 한 멤버ID
	 * @return 성공 실패 여부의 boolean값 true/false
	 */
	private boolean _addBooks(List<BookDTO> bookList, String memberId) {
		if(bookList == null)
			return false;
		int bookListCnt = bookList.size();
		int curCnt = 0;
		//전체 리스트를 돌면서 순차적으로 책 목록과 내가 찜한 책 목록에 데이터를 추가한다.
		while(curCnt < bookListCnt) {
			BookDTO book = bookList.get(curCnt++);
			try {
				System.out.println("Insert!! AddBook! - "+book.getB_title()+", "+book.getB_author());
				//책 목록 DB에 데이터 추가 
				sqlSession.getMapper(BookMapper.class).addBook(book);
			} catch(Exception e) { //에러 처리
				e.printStackTrace();
				System.out.println("Error Insert Book: "+book.getB_title());
			} finally {
				//찜 처리가 성공하면 찜한 책 카운트 수를 증가시키는 함수를 실행한다.
				favcntUp(book.getB_id());
			}
			
			try {
				String b_isbn = book.getB_isbn();
				if(isExistMyBook(memberId, b_isbn) == 0) {
					//내가 찜한 책 목록 DB에 데이터 추가 
					sqlSession.getMapper(BookMapper.class).addMyBook(memberId, b_isbn);
				}
			} catch(Exception e) {
				e.printStackTrace();
				System.out.print("Error Insert MyBook: "+book.getB_title());
			}
		}
		return true;
	}
	
	/**
	 * 
	 * Desc : 해당 책의 찜한 개수를 증가시킬 때 사용하는 유틸리티 함수
	 * @Method Name : favcntUp
	 * @param b_id : 해당 책의 bid
	 */
	private void favcntUp(String b_id) {
		System.out.println("B_id "+b_id+" favocnt up");
		sqlSession.getMapper(BookMapper.class).favcntUp(b_id);
	}
	
	/**
	 * 
	 * Desc : 가장 많이 찜한 책 리스트 5개를 가져와서 반환하는 함수
	 * @Method Name : getHotBooks
	 * @return 가장 많이 찜한 책 BookDTO객체 리스트
	 */
	public List<BookDTO> getHotBooks() {
		System.out.println("getHotBooks");
		return sqlSession.getMapper(BookMapper.class).getHotBooks();
	}
	
	/**
	 * 
	 * Desc : 해당 책이 내 찜한 책 리스트에 존재하는 지 확인하는 함수
	 * @Method Name : isExistMyBook
	 * @param m_id : 로그인 한 맴버 ID
	 * @param b_isbn : 책 고유 ISBN번호
	 * @return
	 */
	public int isExistMyBook(String m_id, String b_isbn) {
		int result = sqlSession.getMapper(BookMapper.class).isExistMyBook(m_id, b_isbn);
		if(result>0) {
			System.out.println("이미 myBook에 존재합니다!");
		}
		return result;
	}
}