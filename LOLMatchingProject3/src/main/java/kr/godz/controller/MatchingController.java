package kr.godz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MatchingController {
	@RequestMapping(value = "/matching/mypage")
	public String matchingHome() {
		return "/matching/mypage";
	}
}
