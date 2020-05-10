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
				<a class="member-func" data-toggle="modal" data-target="#exampleModalScrollable">
					회원 탈퇴
				</a>
				<!-- Modal -->
				<div class="modal fade" id="exampleModalScrollable" tabindex="-1" role="dialog" aria-labelledby="exampleModalScrollableTitle" aria-hidden="true">
					<div class="modal-dialog modal-dialog-scrollable" role="document">
						<div class="modal-content">
							<div class="modal-header">
				       			<h5 class="modal-title" id="exampleModalScrollableTitle">회원 탈퇴</h5>
				        		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
				          			<span aria-hidden="true">&times;</span>
				        		</button>
				      		</div>
				      		<div class="modal-body">
				        		정말로 탈퇴하시겠습니까?
				      		</div>
				      		<div class="modal-footer">
				        		<button type="button" class="btn btn-danger" 
				        			onclick="post_to_url('${pageContext.request.contextPath}/member/secession', {'userId' : '${vo.userId }'})">탈퇴</button>
				        		<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
				      		</div>
				    	</div>
				    </div>
				</div>
				<br />
				<c:if test="${vo.userId eq 'lys7120@gmail.com' }">
					<a class="member-func" href="#">관리자모드</a>
				</c:if>
			</div>
			<div id="btn-container">
				<button class="btn btn-danger btn-func" id="logoutBtn" 
					onclick="location.href='${pageContext.request.contextPath }/member/logout'">로그아웃</button>
				<button class="btn btn-danger btn-func" id="serviceBtn" 
					onclick="location.href='${pageContext.request.contextPath }/matching/mypage'">서비스 시작</button>
			</div>
		</c:if>
	</div>		
</body>
</html>
