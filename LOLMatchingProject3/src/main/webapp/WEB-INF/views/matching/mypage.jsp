<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Matching Main</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/myPageCss.css" />
<script type="text/javascript">
	$(function() {
	
	});
</script>
</head>
<body>
	<h3>${svo.name } Lv.${svo.summonerLevel }</h3>
	<div id="card-container">
		<img id="card-image" src="http://ddragon.leagueoflegends.com/cdn/img/champion/splash/${championList.get(scvo.championId)[0] }_0.jpg" class="card-img-top" alt="...">
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
			
			<div class="tab-pane fade show active container" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
				<!-- 리팩토링 필요 -->
				<div class="row">
					<c:set value="${svo.levo.get(solorank).tier }" var="tier"></c:set>
			  		<div id="card-tier" class="col">
						<div id="card-text" class="card-body">
				  			<c:if test="${svo.levo.get(solorank) eq null }">
								<img src="../resources/images/emblem/UNRANKED.png" alt="TIER_IMAGE" />
								<span class="tier-rank">UNRANKED</span> <br />
				  			</c:if>
				  			<c:if test="${svo.levo.get(solorank) ne null}">		  			
								<img src="../resources/images/emblem/${tier }.png" alt="TIER_IMAGE" />
					  			<span class="tier-rank">${tier } ${svo.levo.get(solorank).rank }</span> <br />
					  			<span class="tier-point">${svo.levo.get(solorank).leaguePoints }LP</span> 
					  			<c:if test="${svo.levo.get(solorank).leaguePoints eq 100}">
					  				<span style="color:red;">승급전</span>
					  			</c:if>
					  			<br />	
					  			<span class="win-lose">${svo.levo.get(solorank).wins }승 ${svo.levo.get(solorank).losses }패 </span><br />
					  			<span class="win-rate">승률 <fmt:formatNumber value="${svo.levo.get(solorank).winRate }" pattern="#"/>% </span><br />
				  			</c:if>
						</div>			  				
			  		</div>
			  		<div id="card-lane" class="col">
			  			<c:if test="${soloGames.matches eq null }">
			  				기록이 없습니다.
			  			</c:if>
			  			<c:if test="${soloGames.matches ne null }">			  			
				  			<c:set value="${soloGames.matches }" var="matches"></c:set>
				  			${fn:length(matches)} 게임의 선호도 <br />
				  			<c:forEach var="lane_name" items="${soloGames.roleCntList }" varStatus="i" begin="0" end="1">
								<c:set value="${soloGames.roleCntList.get(i.index)}" var="lane"></c:set>
					  			<div class="lane-img">			  
					  				<c:if test="${tier eq null }">
							  			<img src="../resources/images/lane/IRON-${lane}.png" alt="LANE_IMG" /> 
					  				</c:if>			
					  				<c:if test="${tier ne null }">					  				
							  			<img src="../resources/images/lane/${tier}-${lane}.png" alt="LANE_IMG" /> 
					  				</c:if>			
						  			${soloGames.roleCnt.get(lane) } 게임 <br />
					  			</div>
				  			</c:forEach>
				  			신뢰도 ${soloGames.reliability }%
			  			</c:if>
			  		</div>
			  		<div id="card-detail" class="col-6">
			  			<c:if test="${soloGames.matches eq null }">
			  				기록이 없습니다.
			  			</c:if>
			  			<c:if test="${soloGames.matches ne null }">			  				
							<c:forEach var="champ_id" items="${soloGames.championCntList }" varStatus="i" begin="0" end="2">
								<div class="detail-champ">
									<c:set value="${championList.get(champ_id)[0]}" var="champName"></c:set>
						  			<img src="http://ddragon.leagueoflegends.com/cdn/10.9.1/img/champion/${champName }.png" alt="MOST_CHAMPION" />
						  			<span class="detail-champName"><c:out value="${championList.get(champ_id)[1]}"></c:out></span>
								</div>
							</c:forEach>
			  			</c:if>
			  		</div>
				</div>
				<!-- 리팩토링 필요 -->
			</div>
			
			
		    <div class="tab-pane fade container" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
		    	
		    </div>
		</div>
	</div>
</body>
</html>