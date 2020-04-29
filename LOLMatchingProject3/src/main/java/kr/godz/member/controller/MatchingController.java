package kr.godz.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MatchingController {
	@RequestMapping(value = "/matching/home")
	public String matchingHome() {
		return "matchingHome";
	}
}
