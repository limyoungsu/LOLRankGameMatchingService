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
	public String matchingRank(@ModelAttribute CommVO commVO,  @ModelAttribute BoardVO boardVO, Model model, HttpServletRequest request) {
		String nickName = (String) request.getSession().getAttribute("nickName");
		String summonerName = (String) request.getSession().getAttribute("summonerName");

		logger.info("matchingRank call : " + nickName + ", " + summonerName + ", " + boardVO + ", " +  
					commVO.getCurrentPage() + ", " + commVO.getPageSize() + ", " + commVO.getBlockSize());
		
		System.out.println(boardVO.getTier() == null);
		
		PagingVO<BoardVO> pagingVO = null;
		// 검색을 했다면 boardVO의 tier 값은 무조건 null이 아님
		if(boardVO.getTier() == null) {
			// 검색이 아닌 모든 게시판 출력
			pagingVO = boardService.selectList(commVO.getCurrentPage(), commVO.getPageSize(), commVO.getBlockSize(), null);			
		} else {
			// 검색 결과 출력
			pagingVO = boardService.selectList(commVO.getCurrentPage(), commVO.getPageSize(), commVO.getBlockSize(), boardVO);						
		}
		
		model.addAttribute("nickName", nickName);
		model.addAttribute("summonerName", summonerName);
		model.addAttribute("pagingVO", pagingVO);
		model.addAttribute("boardVO", boardVO);
		model.addAttribute("newLine", "\n");
		model.addAttribute("br", "</br>");
		model.addAttribute("todayTime", new Date().getTime());
		
		logger.info("matchingRank return");
		return "/matching/board";
	}
	
	
	@RequestMapping(value = "/matching/board/writeProcess", method = RequestMethod.GET)
	public String rankBoardWriteGet() {
		logger.info("rankBoardWriteGet call");
		logger.info("rankBoardWriteGet return");
		return "redirect:/matching/board/rank";
	}
	
	
	@RequestMapping(value = "/matching/board/writeProcess", method = RequestMethod.POST)
	public String rankBoardWritePost(@ModelAttribute BoardVO boardVO) {
		logger.info("rankBoardWritePost call : " + boardVO);
		boardService.insertMemo(boardVO);
		
		logger.info("rankBoardWritePost return");
		return "redirect:/matching/board/rank";
	}
	
	
//	@RequestMapping(value = "/matching/board/searchProcess")
//	public String boardSearchGet(Model model, @ModelAttribute CommVO commVO,  @ModelAttribute BoardVO boardVO) {
//		logger.info("boardSearchGet call : " + boardVO);
//		PagingVO<BoardVO> pagingVO = boardService.selectSearchedList(commVO.getCurrentPage(), commVO.getPageSize(), commVO.getBlockSize(), boardVO);
//		model.addAttribute("pagingVO", pagingVO);
//		
//		logger.info("boardSearchGet return");
//		return "/matching/board";
//	}
}
