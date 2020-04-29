package kr.godz.member.vo;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@XmlRootElement
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO {
	private int idx;				// 고유번호
	private String 	userId;			// Login ID
	private String 	password;		// Login PW
	private String 	userName;		// 실명
	private String 	nickName;		// 커뮤니티에서 사용할 Name
	private String 	summonerName;	// LOL 소환사명
	private Date   	birth;		
	private String 	phone;			// xxx-xxxx-xxxx 형식
	private Date   	regDate;		// 계정 생성일
	private int		useType;		// 0 : 사용가능,  1: 휴면계정,  2 : 정지
	private String	emailToken;		// 이메일 인증시 같이 보내는 고유키
}
