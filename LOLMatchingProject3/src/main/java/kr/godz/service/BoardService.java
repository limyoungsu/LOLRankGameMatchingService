package kr.godz.service;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.godz.dao.BoardDAO;
import kr.godz.vo.BoardVO;
import kr.godz.vo.PagingVO;

@Service
public class BoardService {
	
	@Autowired
	BoardDAO boardDAO;

	final static private Logger logger = LoggerFactory.getLogger(BoardService.class);
	
	 //1 Memo_board에 data 넣기
	public void insertMemo(BoardVO boardVO) {
		logger.info("insert call : " + boardVO);
		boardDAO.insertMemo(boardVO);
		
		logger.info("insert return");
		return;
	}

	
	// 2. DB의 모든 data를 가져옴 	
	public PagingVO<BoardVO> selectList(int currentPage, int pageSize, int blockSize) {
		logger.info("selectList call : " + currentPage + ", " + pageSize + ", " + blockSize);
		
		PagingVO<BoardVO> pagingVO = null;
		try {
			int totalCnt = boardDAO.selectCnt();
			pagingVO = new PagingVO<BoardVO>(totalCnt, currentPage, pageSize, blockSize);
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			map.put("startNo", pagingVO.getStartNo());
			map.put("endNo", pagingVO.getEndNo());
			pagingVO.setList(boardDAO.selectList(map));

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.info("selectList return : " + pagingVO);
		return pagingVO;
	}
}
