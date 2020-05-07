package kr.godz.dao;

import kr.godz.vo.BoardVO;

public interface BoardDAO {
	
	// 1. Board Table에 데이터 insert
	public void insertMemo(BoardVO vo);

}
