package kr.godz.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.godz.dao.BoardDAO;
import kr.godz.vo.BoardVO;

@Service
public class BoardService {
	
	@Autowired
	BoardDAO boardDAO;

	final static private Logger logger = LoggerFactory.getLogger(BoardService.class);
	
	public void insertMemo(BoardVO boardVO) {
		logger.info("insert call : " + boardVO);
		boardDAO.insertMemo(boardVO);
		
		logger.info("insert return");
		return;
	}
}
