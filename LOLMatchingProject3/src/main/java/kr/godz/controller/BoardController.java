package kr.godz.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.godz.service.BoardService;
import kr.godz.vo.BoardVO;
import kr.godz.vo.CommVO;
import kr.godz.vo.PagingVO;

@Controller
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	final static private Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@RequestMapping(value = "/matching/board/rank")
	public String matchingRank(@ModelAttribute CommVO commVO, Model model, HttpServletRequest request) {
		String nickName = (String) request.getSession().getAttribute("nickName");
		String summonerName = (String) request.getSession().getAttribute("summonerName");

		logger.info("matchingRank call : " + nickName + ", " + summonerName + ", " + 
					commVO.getCurrentPage() + ", " + commVO.getPageSize() + ", " + commVO.getBlockSize());
		
		PagingVO<BoardVO> pagingVO = boardService.selectList(commVO.getCurrentPage(), commVO.getPageSize(), commVO.getBlockSize());
		
		model.addAttribute("nickName", nickName);
		model.addAttribute("summonerName", summonerName);
		model.addAttribute("pagingVO", pagingVO);
		model.addAttribute("newLine", "\n");
		model.addAttribute("br", "</br>");
		model.addAttribute("todayTime", new Date().getTime());
		
		logger.info("matchingRank return");
		return "/matching/board";
	}
	
	
	@RequestMapping(value = "/matching/board/writeProcess", method = RequestMethod.GET)
	public String rankBoardWriteGet() {
		logger.info("rankBoardWriteGet call");
		return "redirect:/matching/board/rank";
	}
	
	
	@RequestMapping(value = "/matching/board/writeProcess", method = RequestMethod.POST)
	public String rankBoardWritePost(@ModelAttribute BoardVO boardVO) {
		logger.info("rankBoardWritePost call : " + boardVO);
		boardService.insertMemo(boardVO);
		
		logger.info("rankBoardWritePost return");
		return "redirect:/matching/board/rank";
	}
}
