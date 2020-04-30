<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Matching Main</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/myPageCss.css" />
</head>
<body>
	<h1>${summonerName }</h1>
	<div id="card-container">	
		<div id="card-content" class="card">
			<img id="card-image" src="../resources/images/LOL_logo.png" class="card-img-top" alt="...">
	  		<div class="card-body">
	    		<nav>
	  				<div class="nav nav-tabs" id="nav-tab" role="tablist">
	    				<a class="nav-item nav-link active" id="season-info" data-toggle="tab" href="#nav-home" role="tab" 
	    					aria-controls="nav-home" aria-selected="true">시즌 2020 전체</a>
	    				<a class="nav-item nav-link" id="solo-info" data-toggle="tab" href="#nav-profile" role="tab" 
	    					aria-controls="nav-profile" aria-selected="false">솔로랭크</a>
	    				<a class="nav-item nav-link" id="free-info" data-toggle="tab" href="#nav-contact" role="tab" 
	    					aria-controls="nav-contact" aria-selected="false">자유랭크</a>
	  				</div>
				</nav>
				<div class="tab-content" id="nav-tabContent">
	  				<div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">시즌 2020 총 정보</div>
				  	<div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">솔랭 총 정보</div>
				  	<div class="tab-pane fade" id="nav-contact" role="tabpanel" aria-labelledby="nav-contact-tab">자랭 총 정보</div>
				</div>
	    	</div>
		</div>
	</div>
</body>
</html>