package kr.godz.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.internal.LinkedTreeMap;

import kr.godz.service.MatchingService;
import kr.godz.service.MemberService;
import kr.godz.vo.SummonerVO;

@Controller
public class MatchingController {
	
	@Autowired
	MemberService memberService;
	@Autowired
	MatchingService matchingService;
	
	static LinkedTreeMap<Long, String[]> championList = new LinkedTreeMap<>();
	final static private Logger logger = LoggerFactory.getLogger(MatchingController.class);
	
	@RequestMapping(value = "/matching/mypage")
	public String matchingHome(Model model, @RequestParam(value="name") String summonerName) {
		logger.info("matchingHome call : " + summonerName);
		
		// 주기능 페이지 home에서 챔피언 리스트를 초기화
		matchingService.InitChampionList(championList);
		// 소환사 이름을 통한 소환사 정보를 가져와서 jsp로 넘김
		SummonerVO vo = matchingService.getSummonerInfo(summonerName);
		model.addAttribute("summonerVO", vo);
		model.addAttribute("championList", championList);
		logger.info("matchingHome return : " + vo);
		return "/matching/mypage";
	}
}
