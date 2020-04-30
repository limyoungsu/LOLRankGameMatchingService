<%@ page contentType="text/html; charset=UTF-8"%>
<html>
<head>
<meta charset="UTF-8">
<title><sitemesh:write property='title' /></title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/matchingPage.css" />
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
<link href="https://fonts.googleapis.com/css2?family=Do+Hyeon&display=swap" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
<sitemesh:write property='head' />
</head>
<body>
	<div id="top-container">
		<div id="top-content">
			<a href="${pageContext.request.contextPath }/matching/home">LOGO</a>
		</div>
	</div>
	<div id="top2-container">
		<div id="top2-content">
			<div class="row">
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
	</div>
	<div id="body-container">
		<div id="body-content">
			<sitemesh:write property="body"/>
		</div>
	</div>
</body>
</html>