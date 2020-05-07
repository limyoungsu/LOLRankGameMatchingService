package kr.godz.controller;

import java.net.URLEncoder;

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

@Controller
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	final static private Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@RequestMapping(value = "/matching/board/rank")
	public String matchingRank(Model model, @RequestParam(value = "nickName") String nickName, 
											@RequestParam(value = "summonerName") String summonerName) {
		logger.info("matchingRank call : " + nickName + ", " + summonerName);
		
		model.addAttribute("nickName", nickName);
		model.addAttribute("summonerName", summonerName);
		
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
		String nickName = URLEncoder.encode(boardVO.getNickName());
		String summonerName = URLEncoder.encode(boardVO.getSummonerName());
		boardService.insertMemo(boardVO);
		
		logger.info("rankBoardWritePost return");
		return "redirect:/matching/board/rank?nickName="+nickName+"&summonerName="+summonerName;
	}
}
