package kr.godz.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.godz.service.MatchingService;
import kr.godz.service.MemberService;
import kr.godz.vo.BoardVO;
import kr.godz.vo.SummonerVO;

@Controller
public class MatchingController {
	
	@Autowired
	MatchingService matchingService;
	
	static Map<String, String[]> championList = new HashMap<>();
	@SuppressWarnings("serial")
	static Map<String, String> laneInfo = new HashMap<String, String>() {{
											put("TOP", "탑");
											put("MID", "미드");
											put("JUNGLE", "정글");
											put("SUPPORT", "서포터");
											put("BOT", "원딜");
										}};
	final static private Logger logger = LoggerFactory.getLogger(MatchingController.class);
	
	@RequestMapping(value = "/matching/mypage")
	public String matchingHome(Model model, HttpServletRequest request) {
		logger.info("matchingHome call");
		
		String nickName = (String) request.getSession().getAttribute("nickName");
		String summonerName = (String) request.getSession().getAttribute("summonerName");
		
		// 주기능 페이지 home에서 챔피언 리스트를 초기화
		matchingService.InitChampionList(championList);
		
		// 소환사 이름을 통한 소환사 정보를 가져와서 jsp로 넘김
		SummonerVO vo = matchingService.getSummonerInfo(summonerName);
		
		model.addAttribute("nickName", nickName);
		model.addAttribute("summonerName", vo.getName());
		model.addAttribute("svo", vo);
		model.addAttribute("championList", championList);
		model.addAttribute("laneInfo", laneInfo);
		
		logger.info("matchingHome return : " + vo);
		return "/matching/mypage";
	}
}
