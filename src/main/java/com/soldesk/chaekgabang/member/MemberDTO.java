package com.soldesk.chaekgabang.member;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Entity;
					
/**
 * 
 * Desc : DB의 MEMBER TABLE과 매핑될 필드를 담은 Class 
 * @Class Name : MemberDTO
 */
@Entity
public class MemberDTO {
	// 테이블의 primary key로 매핑됨
	@Id
	@Pattern(regexp = "\\S{4,8}", message = "공백없이 4~8자를 입력하세요.")
	private String m_id;
	
	@Column
	@NotNull
	// 정규화체크(공백이없는 4~12자리 문자)
	@Pattern(regexp = "\\S{4,12}", message = "패스워드는 4자~12자로 입력해주세요.")
	private String m_password;
	
	@Column
	@NotNull 
	@Pattern(regexp="^[_0-9a-zA-Z-]+@[0-9a-zA-Z_-]+\\.[0-9a-zA-Z]+", message="메일형식을 준수하여 작성해주세요.")
	private String m_email;
	
	@Column
	@NotNull
	// 정규화체크(공백이없는 2~6자리 문자)
	@Pattern(regexp = "\\S{2,6}", message = "이름은 2~6자로 입력해주세요.")
	private String m_name;
	
	@Column
	@NotNull
	@Pattern(regexp = "\\d{1,3}", message = "나이는 숫자만 입력가능합니다.")
	// int형태는 size체크 불가
	private String m_age;
	
	@Column
	@NotNull
	@Pattern(regexp = "\\d{10,11}", message = "'-'없이 숫자만 입력해주세요.")
	private String m_tel;
	
	//Getters & Setters
	public String getM_id() {
		return m_id;
	}

	public void setM_id(String m_id) {
		this.m_id = m_id;
	}

	public String getM_password() {
		return m_password;
	}

	public void setM_password(String m_password) {
		this.m_password = m_password;
	}

	public String getM_email() {
		return m_email;
	}

	public void setM_email(String m_email) {
		this.m_email = m_email;
	}

	public String getM_name() {
		return m_name;
	}

	public void setM_name(String m_name) {
		this.m_name = m_name;
	}

	public String getM_age() {
		return m_age;
	}

	public void setM_age(String m_age) {
		this.m_age = m_age;
	}

	public String getM_tel() {
		return m_tel;
	}

	public void setM_tel(String m_tel) {
		this.m_tel = m_tel;
	}
}
