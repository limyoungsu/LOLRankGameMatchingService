package kr.godz.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.godz.service.MemberService;
import kr.godz.vo.MemberVO;

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
	public String home(Model model, HttpServletRequest request) {
		logger.info("home call");
		
		MemberVO vo = null;
		String userId = getPrincipal();
		if(!userId.equals("anonymousUser")) {
			vo = memberService.selectByUserId(userId);
			
			// vo에서 nickName과 summonerName을 세션에 저장해서 계속 사용
			request.getSession().setAttribute("nickName", vo.getNickName());
			request.getSession().setAttribute("summonerName", vo.getSummonerName());
		}
		model.addAttribute("vo", vo);
		
		logger.info("home return : " + vo);
		return "home";
	}
	
	@RequestMapping(value = "/member/join")
	public String join() {
		logger.info("join call");
		return "member/join";
	}
	
	@RequestMapping(value="/member/idCheck")
	@ResponseBody
	public String idCheck(@RequestParam String id) {
		System.out.println(id + "******************* idCheck **************************");
		String result = memberService.idCheck(id);
		return result;
	}
	
	@RequestMapping(value="/member/nickNameCheck")
	@ResponseBody
	public String nickNameCheck(@RequestParam String id) {
		System.out.println(id + "******************* nickNameCheck **************************");
		String result = memberService.nickNameCheck(id);
		return result;
	}
	
	@RequestMapping(value="/member/summonerNameCheck")
	@ResponseBody
	public String summonerNameCheck(@RequestParam String summonerName) {
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
	
	@RequestMapping(value = "/member/login/accessDenied", method = RequestMethod.GET)
    public String accessDeniedPage(Model model) {
        model.addAttribute("userId", getPrincipal());
        return "accessDenied";
    }
	
	@RequestMapping(value = "/member/secession", method = RequestMethod.POST)
	public String memberSecession(Model model, @RequestParam(value = "userId") String userId, @RequestParam(value = "error", required = false) String error) {
		model.addAttribute("userId", userId);
		if(error != null) {
			if(error.equals("mismatchError")) {
				model.addAttribute("errMsg", "아이디와 비밀번호를 다시 확인해주세요.");				
			}
		}
		return "member/secession";
	}
	
	@RequestMapping(value = "/member/secession-working", method = RequestMethod.GET)
	public String memberSecessionProcessGet() {
		logger.info("memberSecessionProcessGet call");
		logger.info("memberSecessionProcessGet return");
		return "redirect:/";
	}
	
	@RequestMapping(value = "/member/secession-working", method = RequestMethod.POST)
	public String memberSecessionProcessPost(@RequestParam(value = "password") String password, @RequestParam(value = "userId") String userId, 
										HttpServletRequest request, Model model) {
		boolean res = memberService.secession(userId, password);
		if(res) {
			request.getSession().invalidate();
			return "redirect:/";			
		} else {
			model.addAttribute("userId", userId);
			return "forward:/member/secession?error=mismatchError";
		}
	}
	
	private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}
