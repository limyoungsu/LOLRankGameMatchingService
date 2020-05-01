<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Matching Main</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/myPageCss.css" />
</head>
<body>
	<h3>${summonerVO.name } Lv.${summonerVO.summonerLevel }</h3>
	<div id="card-container">
		<img id="card-image" src="http://ddragon.leagueoflegends.com/cdn/img/champion/splash/${championList.get(summonerVO.scvo.championId)[0] }_0.jpg" class="card-img-top" alt="...">
		<nav>
			<div class="nav nav-tabs" id="nav-tab" role="tablist">
				<a class="nav-item nav-link active match-info" data-toggle="tab" href="#nav-home" role="tab" 
					aria-controls="nav-home" aria-selected="true">솔로랭크</a>
				<a class="nav-item nav-link match-info" data-toggle="tab" href="#nav-profile" role="tab" 
		      	    aria-controls="nav-profile" aria-selected="false">자유랭크</a>
			</div>
		</nav>
		<div class="tab-content" id="nav-tabContent">
			<c:set value="RANKED_SOLO_5x5" var="solorank"></c:set>
			<c:set value="RANKED_FLEX_SR" var="flexrank"></c:set>
			<div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
				<div id="card-content" class="card">
			  		<div id="card-tier">
			  			<c:if test="${summonerVO.levo.get(solorank) eq null }">
							<img src="../resources/images/emblem/UNRANKED.png" alt="TIER_IMAGE" />			  				
			  			</c:if>
			  			<c:if test="${summonerVO.levo.get(solorank) ne null}">		  			
							<img src="../resources/images/emblem/${summonerVO.levo.get(solorank).tier }.png" alt="TIER_IMAGE" />
			  			</c:if>
			  		</div>
			  		<div id="card-text" class="card-body">
			  			${summonerVO.levo.get(solorank).tier } ${summonerVO.levo.get(solorank).rank } ${summonerVO.levo.get(solorank).leaguePoints }LP <br />	
			  			${summonerVO.levo.get(solorank).wins }승 ${summonerVO.levo.get(solorank).losses }패 <br />
			  			승률 <fmt:formatNumber value="${summonerVO.levo.get(solorank).winRate }" pattern="#.##"/>% <br />
			    	</div>
				</div>
			</div>
		    <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
		    	<div id="card-content" class="card">
			  		<div id="card-tier">
			  			<c:if test="${summonerVO.levo.get(flexrank) eq null }">
							<img src="../resources/images/emblem/UNRANKED.png" alt="TIER_IMAGE" />			  				
			  			</c:if>
			  			<c:if test="${summonerVO.levo.get(flexrank) ne null}">		  			
							<img src="../resources/images/emblem/${summonerVO.levo.get(flexrank).tier }.png" alt="TIER_IMAGE" />
			  			</c:if>
			  		</div>
			  		<div id="card-text" class="card-body">
			  			${summonerVO.levo.get(flexrank).tier } ${summonerVO.levo.get(flexrank).rank } ${summonerVO.levo.get(flexrank).leaguePoints }LP <br />
			  			${summonerVO.levo.get(flexrank).wins }승 ${summonerVO.levo.get(flexrank).losses }패 <br />
			  			승률 <fmt:formatNumber value="${summonerVO.levo.get(flexrank).winRate }" pattern="#.##"/>% <br />
			    	</div>
				</div>
		    </div>
		</div>
	</div>
</body>
</html>