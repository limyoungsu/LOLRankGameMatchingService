package kr.godz.auth.core;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


public class GodzLoginSuccessHandler implements AuthenticationSuccessHandler {

	private static final Logger logger = LoggerFactory.getLogger(GodzLoginSuccessHandler.class);
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		logger.info("onAuthenticationSuccess call");
		
		String remember = request.getParameter("remember");
		String userId = request.getParameter("userId");
		
//		System.out.println("******************************* remember value : " + remember + "*******************************");
//		System.out.println("******************************* userId value : " + userId + "*******************************");
		
		if(remember != null && remember.equals("save")) {
			// ID 저장 체크했으면 쿠키에 저장
			Cookie cookie = new Cookie("userId", userId);
			cookie.setMaxAge(60*60*24*7);	// 쿠키 일주일 동안 저장
			response.addCookie(cookie);
		} 
		else {
			// ID 저장 체크 안했으면 쿠키 삭제
			Cookie cookie = new Cookie("userId", "");
			cookie.setMaxAge(0); 
			response.addCookie(cookie);
		}
		
		response.sendRedirect(request.getContextPath() + "/");
		return;
	}
	
}
