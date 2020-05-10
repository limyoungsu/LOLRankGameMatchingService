<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>LOGIN PAGE</title>
<script type="text/javascript">
	$(function() {
		
	});
	function formCheck() {
		// LOGIC
		// id 입력
		var data = $("#userId").val();
		if(!data || data.trim().length==0){
			alert('아이디는 반드시 입력해야 합니다.');
			$("#userId").val(data);
			$("#userId").focus();
			return false;
		}
		// id 메일주소 포맷 확인
		if(!validateEmail(data)){
			alert('아이디가 이메일 형식이 아닙니다.');
			$("#userId").val(data);
			$("#userId").focus();
			return false;
		}
		
		// 비밀번호 반드시 입력
		var data = $("#password").val();
		if(!data || data.trim().length==0){
			alert('비밀번호는 반드시 입력해야 합니다.');
			$("#password").val("");
			$("#password").focus();
			return false;
		}
		return true;
	}
	
	// email 주소 format
	function validateEmail(email) {
		var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
		return re.test(email);
	}
</script>
</head>
<body>
	<div id="login-main" class="main-container">
		<c:url var="securityLoginUrl" value="/member/loginProcess" />	
		<!-- action을 Controller에서 받는 게 아니라 Security가 받도록 해야함. Security login-process-url값으로 설정 -->
		<form id="formbox" action="${securityLoginUrl }" method="post" onsubmit="return formCheck();">
			<span class="title">Login</span> <br />
			<div class="input-container">
				<div class="input-name">계정 이름</div>
				<div>
					<!-- value의 userId 는 Controller에서 Cookie의 userId 값을 넘긴 것 -->
					<input type="text" name="userId" placeholder="User ID" id="userId" value="${userId }" maxlength="20">		
				</div>
			</div>	
			
			<div class="input-container">
				<div class="input-name">비밀번호</div>
				<div>
					<!-- value의 userId 는 Controller에서 Cookie의 userId 값을 넘긴 것 -->
					<input type="password" name="password" placeholder="Password" id="password" maxlength="16">	
				</div>
			</div>	
			
			<div class="err-container">
				<span>
					<c:out value="${errMsg }"></c:out>
				</span>
			</div>
			
			<div class="input-container">
				<div>
					<!-- 만약 Controller에서 보낸값이 있다면 체크 유지 -->
					<input style="width:10pt" type="checkbox" name="remember" value="save" ${not empty userId ? "checked='checked'":"" }> 
					<label for="ckb1" style="font-size: 10pt;">아이디저장 </label> <br />
				</div>
			</div>	
			
    		<!-- <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> -->
			<a class="member-func" href="${pageContext.request.contextPath }/member/join"> 회원가입 </a> <br />
			<a class="member-func" href="${pageContext.request.contextPath }/member/idSearch"> 아이디 찾기 </a> <br />
			<a class="member-func" href="${pageContext.request.contextPath }/member/passwordSearch"> 비밀번호 찾기 </a> <br />
	
			<div id="btn-container">			
				<button class="btn btn-danger btn-func" type="submit">로그인</button>
				<button class="btn btn-danger btn-func" type="button" onclick="location.href='${pageContext.request.contextPath}/'">홈으로</button>
			</div>
		</form>
	</div>
</body>
</html>