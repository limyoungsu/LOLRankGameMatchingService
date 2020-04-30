<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<meta charset="UTF-8">
<title><sitemesh:write property='title' /></title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/matchingPage.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" />
<link href="https://fonts.googleapis.com/css2?family=Do+Hyeon&display=swap" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(function() {
		
	});
</script>
<sitemesh:write property='head' />
</head>
<body>
	<div id="top-container" class="container-box">
		<div id="top-content" class="content-box row">
			<div class="col-sm">
				<a href="${pageContext.request.contextPath }/matching/mypage">LOGO</a>
			</div>
			<div class="col-sm">
				<div class="dropdown">
					<button class="btn btn-outline-light dropdown-toggle" type="button" id="infoBtn" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
				    	UserInfo
				  	</button>
				  	<div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
				    	<a class="dropdown-item" href="${pageContext.request.contextPath }/matching/mypage">마이페이지</a>
				    	<a class="dropdown-item" href="${pageContext.request.contextPath }/member/logout">로그아웃</a>
				  	</div>
				</div>
			</div>
		</div>
	</div>
	<div id="top2-container" class="container-box">
		<div id="top2-content" class="content-box row">
			<div class="col-sm">
				<a href="${pageContext.request.contextPath }/matching/solorank">솔로랭크</a>
			</div>
			<div class="col-sm">
				<a href="${pageContext.request.contextPath }/matching/freerank">자유랭크</a>
			</div>
			<div class="col-sm">
			
			</div>
			<div class="col-sm">
			
			</div>
		</div>
	</div>
	<div id="body-container" class="container-box">
		<div id="body-content" class="content-box">
			<sitemesh:write property="body"/>
		</div>
	</div>
	<div id="footer-container" class="container-box">
		<div id="footer-content" class="content-box">
			<lolza class="GG"></lolza> <br />
			© 2020-2022 LOLZA.GG. LOLZA.GG isn’t endorsed by Riot Games and doesn’t reflect the views or opinions of Riot Games or anyone officially involved in producing or managing League of Legends. League of Legends and Riot Games are trademarks or registered trademarks of Riot Games, Inc. League of Legends © Riot Games, Inc.
		</div>
	</div>
</body>
</html>