package com.soldesk.chaekgabang.member;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemberController {
	
	/**
	 * Desc : 맴버 관련 로직 처리를 위한 MemberDAO 객체 변수
	 */
	@Inject
	private MemberDAO memberDAO;
	
	/**
	 * 
	 * Desc : 로그인 후 메인화면으로 진입
	 * @Method Name : home
	 * @return book/main : book/main.jsp 돌아감
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "redirect:/book/main";
	}
	
	/**
	 * 
	 * Desc : admin계정 '회원관리'의 회원 목록을 가져오는 함수
	 * @Method Name : getMemberList
	 * @param model : 모델 객체
	 * @return member/memberList : member/memberList.jsp 실행
	 */
	@RequestMapping(value = "/member/list", method = RequestMethod.GET)
	public String getMemberList(Model model) {
		memberDAO.getMemberList(model);
		return "member/memberList";
	}
	
	/**
	 * 
	 * Desc : admin계정 회원 삭제 기능 함수
	 * @Method Name : memberDelete
	 * @param member : member정보를 넘기는 MemberDTO 객체 변수
	 * @return MemberDAO에서 처리 후 반환된 재요청 주소 String값
	 */
	@RequestMapping(value = "/member/delete", method = RequestMethod.POST)
	public @ResponseBody String memberDelete(MemberDTO member) {
		return memberDAO.deleteMember(member);
	}
	
	/**
	 * 
	 * Desc : 회원 수정 페이지 화면이 나오도록 하는 함수
	 * @Method Name : memberUpdate
	 * @param req : 클라이언트에서 요청한 정보
	 * @param model : 모델 객체
	 * @return /member/updateForm : /member/updateForm.jsp 페이지 이동
	 */
	@RequestMapping(value="/member/update", method = RequestMethod.GET)
	public String memberUpdate(HttpServletRequest req, Model model) {
		memberDAO.getMember(req, model);
		return "/member/updateForm";
	}
	
	/**
	 * 
	 * Desc : 회원 수정 페이지에서 수정 요청을 하면 처리하여 결과를 HTMLString형태로 반환해주는 함수
	 * @Method Name : memberUpdate
	 * @param member : MemberDTO 객체
	 * @return MemberDAO의 updateMember함수에서 처리한 HTMLString형태로 반환
	 */
	@RequestMapping(value="/member/update", method = RequestMethod.POST)
	public @ResponseBody String memberUpdate(MemberDTO member) {
		return memberDAO.updateMember(member);
	}
	
	/**
	 * 
	 * Desc : 회원 비밀번호 변경 페이지가 나오도록 하는 함수
	 * @Method Name : memberUpdatePw
	 * @return /member/updatePwForm : /member/updatePwForm.jsp 페이지로 이동
	 */
	@RequestMapping(value="/member/updatePw", method = RequestMethod.GET)
	public String memberUpdatePw() {
		return "/member/updatePwForm";
	}
	
	/**
	 * 
	 * Desc : 회원 비밀번호 변경 페이지에서 변경요청을 하면 변경 처리하여 결과를 HTMLString형태로 반환해주는 함수
	 * @Method Name : memberUpdatePw
	 * @param newPw : 새로 변경할 PASSWORD
	 * @param m_id : 현재 로그인 한 회원 ID
	 * @param m_password : 현재 로그인 한 회원 PASSWORD
	 * @return MemberDAO의 updatePassword 함수에서 처리한 HTMLString형태로 반환
	 */
	@RequestMapping(value="/member/updatePw", method = RequestMethod.POST)
	public @ResponseBody String memberUpdatePw(@RequestParam("m_newpw") String newPw, 
			@RequestParam("m_id") String m_id, @RequestParam("m_password") String m_password) {
		return memberDAO.updatePassword(newPw, m_id, m_password);
	}
	
	/**
	 * 
	 * Desc : 회원가입을 위한 작성 페이지를 여는 함수
	 * @Method Name : joinMember
	 * @param memberDto : 유효성 검사를 위한 MemberDTO 객체 변수
	 * @return /member/joinForm : /member/joinForm.jsp 실행
	 */
	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String joinMember(MemberDTO memberDto) {
		return "/member/joinForm";
	}
	
	/**
	 * 
	 * Desc : 회원가입 요청을 받아 MemberDAO에서 처리 후 반환된 주소를 재요청 해주는 함수
	 * @Method Name : joinMember
	 * @param memberDto	: joinForm에서 작성된 정보를 담아 넘기는 MemberDTO 객체 변수
	 * @param bindingResult : 유효성 검사 결과 전달 변수
	 * @return MemberDAO에서 요청 처리 후 재요청 주소 String값
	 */
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String joinMember(@Valid MemberDTO memberDto, BindingResult bindingResult) {
		return memberDAO.joinMember(memberDto, bindingResult);
	}
	
	/**
	 * 
	 * Desc : 회원가입 ID 중복 여부를 위한 요청 처리 함수
	 * @Method Name : confirmIdCheck
	 * @param m_id : 회원가입 페이지에서 넘어온 ID값 
	 * @return MemberDAO에서 요청 처리 후 재요청 주소 String값
	 */
	@RequestMapping(value = "/idcheck", method = RequestMethod.POST)
	public @ResponseBody String confirmIdCheck(@RequestParam("id") String m_id){
		return memberDAO.confirmIdCheck(m_id);
	}

	/**
	 * 
	 * Desc : 로그인 페이지를 여는 함수
	 * @Method Name : loginMember
	 * @return member/loginForm : member/loginForm.jsp 실행
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginMember() {
		return "/member/loginForm";
	}
	
	/**
	 * 
	 * Desc : 로그인 페이지에서 작성된 정보를 DAO로 넘겨 실행하기 위한 함수 
	 * @Method Name : loginMember
	 * @param request	: 로그인 성공시 Session 생성을 위한 request 변수
	 * @param memberDto	: 로그인 페이지에서 작성된 값을 넘기는 MemberDTO 객체 변수
	 * @return MemberDAO에서 요청 처리후 반환된 재요청 주소 String값
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginMember(HttpServletRequest request, MemberDTO memberDto) {
		return memberDAO.loginMember(request, memberDto);
	}
	
	/**
	 * 
	 * Desc : 로그아웃시 Session 무효화를 위한 함수
	 * @Method Name : logoutMember
	 * @param request : Session invalidate를 위한 변수
	 * @return MemberDAO에서 요청 처리 후 반환된 재요청 주소 String값
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutMember(HttpServletRequest request) {
		return memberDAO.logoutMember(request);
	}
}
