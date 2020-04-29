package kr.godz.member.controller;

import java.security.Principal;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.godz.member.service.MemberService;
import kr.godz.member.vo.MemberVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class MemberController {
	
	@Autowired
	MemberService memberService;
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model, Principal principal) {
		logger.info("home call : " + principal);
		MemberVO vo = null;
		if(principal != null) {			
			String userId = principal.getName();
			if(userId != null) {
				vo = memberService.selectByUserId(userId);
			}
		}
		model.addAttribute("vo", vo);
		return "home";
	}
	
	@RequestMapping(value = "/member/join")
	public String join() {
		logger.info("join call");
		return "member/join";
	}
	
	@RequestMapping(value="/member/idCheck")
	@ResponseBody
	public String idCheck(@RequestParam String userId) {
		System.out.println(userId + "******************* idCheck **************************");
		String result = memberService.idCheck(userId);
		return result;
	}
	
	@RequestMapping(value="/member/nickNameCheck")
	@ResponseBody
	public String nickNameCheck(@RequestParam String nickName) {
		System.out.println(nickName + "******************* nickNameCheck **************************");
		String result = memberService.nickNameCheck(nickName);
		return result;
	}
	
	@RequestMapping(value="/member/summonerNameCheck")
	@ResponseBody
	public String summonerNameCheck(@RequestParam String summonerName) {
		System.out.println(summonerName + "******************* summonerNameCheck **************************");
		String result = memberService.summonerNameCheck(summonerName);
		return result;
	}
	
	@RequestMapping(value = "/member/joinComplete", method = RequestMethod.GET)
	public String joinGet() {
		// GET 방식의 접근은 login으로 리다이렉트
		logger.info("joinGet call");
		return "redirect:/";
	}
	
	@RequestMapping(value = "/member/joinComplete", method = RequestMethod.POST)
	public String joinPost(@ModelAttribute MemberVO vo) {
		logger.info("joinPost call : " + vo);
		// memberService에서 메일 보내고 메일 코드가 맞으면 계정 사용 가능
		memberService.insert(vo);
		return "redirect:/member/mailSend";
	}
	
	@RequestMapping(value = "/member/mailSend")
	public String mailSend() {
		return "mailSend";
	}
	
	@RequestMapping(value = "/member/confirm")
	public String conform(@RequestParam String userId, @RequestParam String emailToken) {
		logger.info("conform call : " + userId + ", " + emailToken);
		// 메일 인증을 하면 계정의 useType을 1로 바꾼다 ; userId가 가진 emailToken과 쿼리스트링 emailToken이 맞으면
		// useType 0 : 미인증 상태(불가)
		// useType 1 : 인증 상태(가능)
		memberService.updateUseType(userId, emailToken);
		return "redirect:/member/login";
	}
	
	@RequestMapping(value = "/member/login")
	public String login(HttpServletRequest req, Model model, @RequestParam(value = "error", required = false) String error) {
		logger.info("login call");
		// 여기서 쿠키를 읽어 userId를 찾아 값을 넘기자(만약 있다면)
		Cookie[] cookies = req.getCookies();
		
		// request에서 읽은 쿠키에 값이 존재한다면
		if(cookies != null && cookies.length > 0) {
			// 저장된 쿠키들을 읽어 userId의 이름이 있나 확인
			for(Cookie c : cookies) {
				if(c.getName().equals("userId")) {
					// 있으면 model에 저장
					model.addAttribute("userId", c.getValue());
					break;
				}
			}
		}
		
		if(error != null) {
			if(error.equals("mismatchError")) {
				model.addAttribute("errMsg", "아이디와 비밀번호를 다시 확인해주세요.");				
			} 
			else if(error.equals("disabledError")) {
				model.addAttribute("errMsg", "사용 불가능한 계정입니다.");				
			}
			else {
				model.addAttribute("errMsg", "알 수 없는 이유로 로그인이 불가능합니다.");
			}
		}
		return "member/login";
	}
	
//	@RequestMapping(value = "/member/loginProcess")
//	public String loginError(@RequestParam(required = false) String error, Principal principal, HttpServletRequest req) {
//		logger.info("loginError call : " + error + ", " + principal);	// error 값이 안뜸, 로그인 실패니 principal은 null
//		if(error != null) {
//			logger.info("loginCompletePost fail : ID, PW 매칭 실패.");
//			req.setAttribute("errMsg", "아이디와 비밀번호를 다시 확인해주세요.");
//		} else {
//			
//		}
//		return "forward:/member/login";
//	}
	

//	@RequestMapping(value = "/member/loginProcess")
//	public String loginCompletePost(@RequestParam(required = false) String remember, @RequestParam(required = false) String error, 
//									Model model, HttpServletRequest req, HttpServletResponse res) {
//		logger.info("loginCompletePost call : " + error + ", " + remember);
//		
//		// 로그인 정보가 맞는지 확인 (DB 접근)
//		MemberVO resultVo = memberService.loginValidation(vo.getUserId(), vo.getPassword());
//		
//		if(error != null) {
//			// id, pw가 매칭되는 계정이 없으면
//			logger.info("loginCompletePost fail : ID, PW 매칭 실패.");
//			req.setAttribute("errMsg", "아이디와 비밀번호를 다시 확인해주세요.");
//			return "forward:/member/login";
//		}
//		else {
//			if(resultVo.getUseType() != 1) {
//				// id, pw는 맞지만 useType이 1이 아닌 것
//				logger.info("loginCompletePost fail : 계정 useType 불가용 상태.");
//				req.setAttribute("errMsg", "계정이 사용이 불가능한 상태입니다.");
//				return "forward:/member/login";
//			}
//			req.getSession().setAttribute("vo", resultVo);
//			if(remember != null && remember.equals("save")) {
//				// ID 저장 체크했으면 쿠키에 저장
//				Cookie cookie = new Cookie("userId", resultVo.getUserId());
//				cookie.setMaxAge(60*60*24*7);	// 쿠키 일주일 동안 저장
//				res.addCookie(cookie);
//			} 
//			else {
//				// ID 저장 체크 안했으면 쿠키 삭제
//				Cookie cookie = new Cookie("userId", "");
//				cookie.setMaxAge(0); 
//				res.addCookie(cookie);
//			}
//		}
//		logger.info("loginCompletePost return");
//		return "redirect:/";
//	}
	
//	@RequestMapping(value = "/member/logout")
//	public String logout(HttpServletRequest req) {
//		// 세션 어트리뷰트 삭제
//		req.getSession().removeAttribute("vo");
//		return "redirect:/";
//	}
}
