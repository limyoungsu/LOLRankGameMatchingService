package kr.godz.auth.core;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class GodzLoginFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// exception을 찍으면 다른 정보가 넘어옴. 이 정보들로 로그인 페이지에 errMsg를 띄워주면 되겠다.
		// 비번이 틀린 경우 : Bad credentials
		// useType이 1이 아닌 경우(불가용상태) : User is disabled
		// System.out.println(exception + "*********************************************");
		
		if(exception.getMessage().equals("Bad credentials")) {
			response.sendRedirect(request.getContextPath() + "/member/login?error=mismatchError");
			return;
		} else if(exception.getMessage().equals("User is disabled")) {
			response.sendRedirect(request.getContextPath() + "/member/login?error=disabledError");
			return;
		}
		response.sendRedirect(request.getContextPath() + "/member/login?error=undefined");
		
		return;
	}

}
