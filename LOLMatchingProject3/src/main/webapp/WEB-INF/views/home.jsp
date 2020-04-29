<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<title>Home</title>
<style type="text/css">

</style>
<script type="text/javascript">
	$(function() {
		
	});
</script>
</head>
<body>
	<div id="home-main" class="main-container">
		<span class="subtitle">많은 소환사들과 함께 게임해보세요. <br> 여러분들을 필요로 하는 소환사가 있습니다.</span>
		<div id="logo-container"> <img src="./resources/images/LOL_logo.png" alt="LOL_LOGO" /> </div>
		<c:if test="${vo eq null}">
			<div id="btn-container">		
				<button class="btn btn-danger btn-func" id="loginBtn" onclick="location.href='./member/login'">로그인</button> <br />
				<button class="btn btn-danger btn-func" id="joinBtn" onclick="location.href='./member/join'">회원가입</button>
			</div>
		</c:if>
		<c:if test="${vo ne null }">
			<div class="msgbox">			
				<span class="greetingbox">${vo.nickName }님 반갑습니다.</span> <br />
				<a class="member-func" href="#">마이페이지</a> <br />
				<a class="member-func" href="#">정보 수정</a> <br />
				<a class="member-func" href="#">회원 탈퇴</a> <br />
				<c:if test="${vo.userId eq 'lys7120@gmail.com' }">
					<a class="member-func" href="#">관리자모드</a>
				</c:if>
			</div>
			<div id="btn-container">
				<button class="btn btn-danger btn-func" id="logoutBtn" onclick="location.href='./member/logout'">로그아웃</button>
				<button class="btn btn-danger btn-func" id="serviceBtn" onclick="location.href='./matching/home'">서비스 시작</button>
			</div>
		</c:if>
	</div>		
</body>
</html>
