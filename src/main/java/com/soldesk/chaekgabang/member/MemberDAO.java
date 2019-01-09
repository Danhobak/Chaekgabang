package com.soldesk.chaekgabang.member;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

/**
 * 
 * Desc :  맴버관련 로직 처리를 위한 DAO
 * @Class Name : MemberDAO
 */
@Service
public class MemberDAO {
	
	/**
	 *  Desc : DB와 SQL질의를 위한 SqlSession 객체 변수
	 */
	@Inject
	private SqlSession sqlSession;
	
	
	/**
	 * 
	 * Desc : Controller > getMemberList 회원목록 로직 수행 함수
	 * @Method Name : getMemberList
	 * @param model : DB에서 가져온 List<>를 Model 변수에 저장
	 */
	public void getMemberList(Model model) {
		List<MemberDTO> memberList = sqlSession.getMapper(MemberMapper.class).getMemberList();
		model.addAttribute("memberList", memberList);
	}
	
	/**
	 * 
	 * Desc : 회원 한명의 정보를 가져올 때 사용하여 회원정보를 모델 객체에 저장 처리하는 함수
	 * @Method Name : getMember
	 * @param req : 클라이언트에서 요청한 요청정조
	 * @param model : 모델 객체
	 */
	public void getMember(HttpServletRequest req, Model model) {
		String m_id = (String)req.getSession().getAttribute("bMemberId");
		MemberDTO memberInfo = m_id == null ? null : sqlSession.getMapper(MemberMapper.class).getMember(m_id);
		model.addAttribute("memberInfo", memberInfo);
	}
	
	/**
	 * 
	 * Desc : Controller > updateMember 회원 업데이트 요청 로직 수행 함수
	 * @Method Name : updateMember
	 * @param member : 클라이언트에서 요청한 수정할 회원 정보 MemberDTO 객체
	 * @return DB의 수행 결과를 받아와서 HTMLString형태 가공하여 반환
	 */
	public String updateMember(MemberDTO member) {
		int result = 0;
		StringBuffer resultHtml = new StringBuffer();
		try {
			result = sqlSession.getMapper(MemberMapper.class).updateMember(member);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resultHtml.append("<script>");
		if (result != 1) {
			//System.out.println("에러발생");
			resultHtml.append("alert('회원정보 수정에 실패하였습니다');");
		} else {
			//System.out.println("수정성공");
			resultHtml.append("alert('회원정보 수정에 성공하였습니다');");
		}
		resultHtml.append("location.href='/book/main';");
		resultHtml.append("</script>");
		return resultHtml.toString();
	}
	
	/**
	 * 
	 * Desc : Controller > memberDelete 회원삭제 로직 수행 함수
	 * @Method Name : deleteMember
	 * @param member 삭제될 Member의 정보를 담아 온 변수
	 * @return resultStr : DB의 수행 결과를 담아아 전달  (1: 정상수행, 0: 적용사항 없음, -1: 예외)
	 */
	public String deleteMember(MemberDTO member) {
		String resultStr = "-1";
		int result = sqlSession.getMapper(MemberMapper.class).deleteMember(member);
		resultStr = String.valueOf(result);
		return resultStr;
	}
	
	/**
	 * 
	 * Desc : Controller > joinMember 회원등록 로직 수행 함수
	 * @Method Name : joinMember
	 * @param memberDto	: 회원가입 페이지에서 작성한 값을 담은 MemberDTO 객체 변수
	 * @param bindingResult	: 유효성 검사 결과를 담은 변수
	 * @return 수행 결과에 따라서 True  : member/login.jsp 리턴
	 * 		   			    False : /member/joinForm.jsp 리턴
	 */
	public String joinMember(MemberDTO memberDto, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			System.out.println("에러발생");
			return "/member/joinForm";
		} else {
			sqlSession.getMapper(MemberMapper.class).joinMember(memberDto);
			System.out.println("회원가입성공!!");
			return "redirect:/login";
		}
	}
	
	/**
	 * 
	 * Desc : Controller > memberUpdatePw 회원 비밀번호 변경 로직 수행 함수
	 * @Method Name : updatePassword
	 * @param newPw : 새로 변경할 비밀번호
	 * @param m_id : 현재 로그인 한 회원 ID
	 * @param m_password : 현재 로그인 한 회원 PASSWORD
	 * @return DB의 수행 결과를 받아와서 HTMLString형태 가공하여 반환
	 */
	public String updatePassword(String newPw, String m_id, String m_password) {
		int result = 0;
		StringBuffer resultHtml = new StringBuffer();
		try {
			result = sqlSession.getMapper(MemberMapper.class).updatePassword(newPw, m_id, m_password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resultHtml.append("<script>");
		if (result != 1) {
			//System.out.println("에러발생");
			resultHtml.append("alert('비밀번호 수정에 실패하였습니다');");
		} else {
			//System.out.println("수정성공");
			resultHtml.append("alert('비밀번호 수정에 성공하였습니다');");
		}
		resultHtml.append("location.href='/member/update';");
		resultHtml.append("</script>");
		return resultHtml.toString();
	}
	
	/**
	 * 
	 * Desc : Controller > confirmIdCheck ID중복 검사 로직 수행 함수
	 * @Method Name : confirmIdCheck
	 * @param m_id : 회원가입 페이지에서 작성된 ID 값을 담은 String 변수
	 * @return count(*)로 select된 결과 리턴	(중복 시 : 1, 아닐 시 : 0)
	 */
	public String confirmIdCheck(String m_id) {
		return String.valueOf(sqlSession.getMapper(MemberMapper.class).idCheck(m_id));
	}
	
	/**
	 * 
	 * Desc : Controller > loginMember 로그인 기능 로직 수행 함수
	 * @Method Name : loginMember
	 * @param request	: 클라이언트의 세션 값을 생성하기 위한 Request 변수
	 * @param memberDto : 로그인 페이지에서 작성된 ID, PW를 담은 MemberDTO 객체 변수
	 * @return DB와 로그인정보 대조 후 결과값에 따라서 (로그인 성공 : 1 -> 세션 생성, book/main.jsp 실행 
	 * 									     로그인 실패 : 0 -> member/login.jsp 실행)
	 */
	public String loginMember(HttpServletRequest request, MemberDTO memberDto) {
		int login_ck = sqlSession.getMapper(MemberMapper.class).loginCheck(memberDto);
		if (login_ck > 0) {
			HttpSession session = request.getSession();
			session.setAttribute("bMemberId", memberDto.getM_id());
			return "redirect:/book/main";
		} else {
			return "redirect:/login";
		}
	}
	
	/**
	 * 
	 * Desc : Controller > logoutMember 로그아웃 기능 로직 수행 함수
	 * @Method Name : logoutMember
	 * @param request : 클라이언트의 세션 정보를 담은 Request 변수
	 * @return 세션 attr삭제 및 무효화 후 book/main.jsp 실행
	 */
	public String logoutMember(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute("bMemberId");
		session.invalidate();
		return "redirect:/book/main";
	}
}
