package com.soldesk.chaekgabang.member;

import java.util.List;

/**
 * 
 * Desc : MemberMapper.xml과 매핑될 함수를 추상화 한 Class 
 * @Class Name : MemberMapper
 */
public interface MemberMapper {

	public List<MemberDTO> getMemberList();
	
	public MemberDTO getMember(String m_id);

	public int deleteMember(MemberDTO member);

	public void joinMember(MemberDTO memberDto);
	
	public int updateMember(MemberDTO memberDto);
	
	public int updatePassword(String newPw, String m_id, String m_password);

	public int idCheck(String m_id);

	public int loginCheck(MemberDTO memberDto);
}
