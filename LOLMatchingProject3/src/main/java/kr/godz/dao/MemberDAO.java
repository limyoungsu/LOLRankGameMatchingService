package kr.godz.dao;

import kr.godz.vo.MemberVO;

public interface MemberDAO {
	// DB에 사용자 정보 저장
	public void insert(MemberVO vo);
	
	// DB에 사용자 권한 저장
	public void insertRole(String userId);
	
	// userId를 통해 정보가져오기
	public MemberVO selectByUserId(String userId);
	
	// nickName을 통해 정보가져오기
	public MemberVO selectByNickName(String nickName);
	
	// summonerName을 통해 정보가져오기
	public MemberVO selectBySummonerName(String summonerName);

	// 메일 인증 후 useType을 1(가용상태)로 변경
	public void updateUseType(String userId);

	// 계정 삭제
	public void delete(String userId);

	// 계정에 대한 권한 삭제
	public void deleteRole(String userId);

}
