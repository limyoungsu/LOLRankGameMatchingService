package kr.godz.controller;

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
	public String matchingRank(Model model, @RequestParam(value = "nickName", required = false) String nickName, 
							HttpServletRequest request, @RequestParam(value = "summonerName", required = false) String summonerName,
							@ModelAttribute CommVO commVO) {

		if(request.getSession().getAttribute("nickName") == null || 
				request.getSession().getAttribute("summonerName") == null) {			
			// 1. 최초에 mypage에서 들어오는 것이 정상적인 접근이고, 그 때는 RequestParam으로 데이터 두개를 받음, 후에 세션에 저장
			System.out.println("Save to Session");
			request.getSession().setAttribute("nickName", nickName);
			request.getSession().setAttribute("summonerName", summonerName);						
		} else {
			// 2. 즉, 게시글 작성 후, 리다이렉트 되는 경우에는 이전에 세션에 넣었던 값을 가지고 사용함
			nickName = (String) request.getSession().getAttribute("nickName");
			summonerName = (String) request.getSession().getAttribute("summonerName");
		}

		logger.info("matchingRank call : " + nickName + ", " + summonerName + ", " + 
					commVO.getCurrentPage() + ", " + commVO.getPageSize() + ", " + commVO.getBlockSize());
		
		PagingVO<BoardVO> pagingVO = boardService.selectList(commVO.getCurrentPage(), commVO.getPageSize(), commVO.getBlockSize());
		
		model.addAttribute("nickName", nickName);
		model.addAttribute("summonerName", summonerName);
		model.addAttribute("pagingVO", pagingVO);
		
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
