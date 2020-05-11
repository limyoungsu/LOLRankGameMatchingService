package kr.godz.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	public PagingVO<BoardVO> selectList(int currentPage, int pageSize, int blockSize, BoardVO boardVO) {
		logger.info("selectList call : " + currentPage + ", " + pageSize + ", " + blockSize + ", " + boardVO);
		
		int totalCnt = boardDAO.selectCnt(boardVO);
		System.out.println(totalCnt + " 개");
		PagingVO<BoardVO> pagingVO = new PagingVO<BoardVO>(totalCnt, currentPage, pageSize, blockSize);
		HashMap<String, String> map = new HashMap<>();
		map.put("startNo", pagingVO.getStartNo()+"");
		map.put("endNo", pagingVO.getEndNo()+"");
		if(boardVO != null) {
			map.put("tier", boardVO.getTier());
			map.put("division", boardVO.getDivision());
			map.put("queueType", boardVO.getQueueType());
			map.put("lane", boardVO.getLane());
			map.put("expectedTime", boardVO.getExpectedTime());
			map.put("isVoice", boardVO.getIsVoice());
		}
		
		pagingVO.setList(boardDAO.selectList(map));
		
		logger.info("selectList return : " + pagingVO);
		return pagingVO;
	}
}
