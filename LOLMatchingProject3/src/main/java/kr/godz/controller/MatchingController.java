package kr.godz.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.godz.service.MemberService;
import kr.godz.vo.MemberVO;

@Controller
public class MatchingController {
	
	@Autowired
	MemberService memberService;
	
	final static private Logger logger = LoggerFactory.getLogger(MatchingController.class);
	
	@RequestMapping(value = "/matching/mypage")
	public String matchingHome(Model model, @RequestParam(value="userId") String userId) {
		logger.info("matchingHome call : " + userId);
		
		MemberVO dbvo = memberService.selectByUserId(userId);
		String summonerName = dbvo.getSummonerName();
		model.addAttribute("summonerName", summonerName);
		
		return "/matching/mypage";
	}
}
