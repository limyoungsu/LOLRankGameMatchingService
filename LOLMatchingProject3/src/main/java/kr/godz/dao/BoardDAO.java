package kr.godz.dao;

import java.util.HashMap;
import java.util.List;

import kr.godz.vo.BoardVO;

public interface BoardDAO {
	
	// 1. Board Table에 데이터 insert
	public void insertMemo(BoardVO vo);

	// 2. 데이터의 총 갯수
	public int selectCnt(BoardVO vo);
	
	// 3. 페이지네이션
	public List<BoardVO> selectList(HashMap<String, String> map);
}
