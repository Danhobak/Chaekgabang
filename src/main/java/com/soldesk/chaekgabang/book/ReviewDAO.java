package com.soldesk.chaekgabang.book;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

/**
 * 
 * Desc : 리뷰 관련 로직 처리를 위한 Data Access Object
 * @Class Name : ReviewDAO
 */
@Service
public class ReviewDAO {
	
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
	 * Desc : 해당 책에 대한 리뷰 리스트를 가져온 후 JSONString형태로 반환하는 함수
	 * @Method Name : getReviewList
	 * @param start : 리뷰 시작 리스트 번호
	 * @param b_isbn : 책 고유 ISBN번호
	 * @return JSONString형태의 책 리뷰 리스트
	 */
	@SuppressWarnings("unchecked")
	public String getReviewList(int start, String b_isbn) {
		JSONObject jsonObj = new JSONObject();
		try {
			//DB에서 리뷰 리스트를 가져온 후 JSON객체에 값으로 넣어준다.
			JSONArray reviewArr = new JSONArray();
			JSONObject obj = null;
			List<ReviewDTO> reviewList = sqlSession.getMapper(BookMapper.class).getReviewList(b_isbn);
			
			ReviewDTO review = null;
			
			long display = DIV_PAGE_NUM;//한 페이지당 보여주는 리스트 갯수
			long total = reviewList.size();//총 페이지 번호
			long totalPage = (long)Math.ceil(total / (double)display); //총 페이지 수
			long stdNum = (long) ((start - 1) / DIV_PAGE_NUM / display); //이전 페이지, 다음 페이지 버튼 처리를 위한 기준 수

			jsonObj.put("stdNum", stdNum);
			jsonObj.put("start", start);
			jsonObj.put("totalPage", totalPage);
			jsonObj.put("display", display);
			jsonObj.put("divPageNum", DIV_PAGE_NUM);
			
			for(int i = 0, j = reviewList.size(); i < j; i++) {
				review = reviewList.get(i);
				obj = new JSONObject();
				obj.put("title", review.getBr_title());
				obj.put("content", review.getBr_content());
				obj.put("userid", review.getBr_refuser());
				obj.put("regdate", review.getBr_regdate().toString());
				reviewArr.add(obj);
			}
			jsonObj.put("items", reviewArr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObj.toJSONString();
	}
	
	/**
	 * 
	 * Desc : 책 리뷰 작성 처리 후 결과를 JSONString형태로 반환하는 함수
	 * @Method Name : writeReview
	 * @param review : 작성 할 리뷰의 정보가 들어있는 ReviewDTO객체
	 * @return JSONString형태의 책 리뷰 작성 결과
	 */
	@SuppressWarnings("unchecked")
	public String writeReview(ReviewDTO review) {
		JSONObject obj = new JSONObject();
		try {
			//이미 작성 한 리뷰가 있는지 확인한 후 없다면 작성 요청 후 성공 처리, 아니면 실패 처리 
			int reviewOkResult = sqlSession.getMapper(BookMapper.class).reviewOk(review);
			if(reviewOkResult >= 1) {
				throw new Exception("중복된 작성자가 리뷰글 작성 시도");
			} else {
				if(_addReview(review) == 1)
					obj.put("result", "success");
			}
		} catch(Exception e) {
			//e.printStackTrace();
			obj.put("result", "fail");
		}
		return obj.toJSONString();
	}
	
	/**
	 * 
	 * Desc : 책 리뷰 DB에 해당 리뷰를 저장하기 위한 유틸리티 함수
	 * @Method Name : addReview
	 * @param review : 작성 할 리뷰의 정보가 들어있는 ReviewDTO객체
	 * @return 요청 처리 후 결과 값 (1:성공, 그외:실패)
	 */
	private int _addReview(ReviewDTO review) {
		//System.out.println("addReview [review id : "+review.getBr_id()+" ]");
		return sqlSession.getMapper(BookMapper.class).addReview(review);
	}
	
	/**
	 * 
	 * Desc : 책 리뷰 삭제 처리 후 결과를 JSONString형태로 반환하는 함수
	 * @Method Name : deleteReview
	 * @param m_id : 로그인한 멤버 ID
	 * @param b_isbn : 책의 고유 ISBN번호
	 * @return JSONString형태의 책 리뷰 삭제 결과
	 */
	@SuppressWarnings("unchecked")
	public String deleteReview(String m_id, String b_isbn) {
		JSONObject obj = new JSONObject();
		try {
			int result = sqlSession.getMapper(BookMapper.class).deleteReview(m_id, b_isbn);
			if(result == 1) {
				obj.put("result", "success");
			} else
				throw new Exception("리뷰 삭제 실패");
		} catch(Exception e) {
			obj.put("result", "fail");
		}
		return obj.toJSONString();
	}

	/**
	 * 
	 * Desc : 책 리뷰 수정 처리 후 결과를 JSONString형태로 반환하는 함수
	 * @Method Name : changeReview
	 * @param review : 수정 할 리뷰의 정보가 들어있는 ReviewDTO객체
	 * @return JSONString형태의 책 리뷰 수정 결과
	 */
	@SuppressWarnings("unchecked")
	public String changeReview(ReviewDTO review) {
		JSONObject obj = new JSONObject();
		try {
			int result = sqlSession.getMapper(BookMapper.class).changeReview(review);
			if(result == 1) {
				obj.put("result", "success");
			} else
				throw new Exception("리뷰 수정 실패");
		} catch(Exception e) {
			obj.put("result", "fail");
		}
		return obj.toJSONString();
	}
}
