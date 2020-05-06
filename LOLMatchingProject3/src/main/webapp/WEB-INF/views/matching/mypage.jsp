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
		initRateColor($("#tier-rank-winrate-solo"));			
		initRateColor($("#tier-rank-winrate-flex"));			
		for(var i = 1; i <= 3; i++) {			
			initRateColor($("#record-winrate-solo"+i));
			initRateColor($("#record-winrate-flex"+i));
			initKdaColor($("#record-kda-solo"+i));
			initKdaColor($("#record-kda-flex"+i));
		}
	});
	
	function isNumeric(data) {
		return !isNaN(Number(data));
	}

	function initRateColor(id) {
		if(id.html() == undefined) {
			return;
		}
		var data = id.html().trim();
		var startIdx = 0;
		for(var i = 0; i < data.length; i++) {
			if(isNumeric(data.charAt(i))) {
				startIdx = i;
				break;
			}
		}
		var endIdx = data.indexOf('%');
	
		var rate = data.substring(startIdx, endIdx).trim();
		if(rate >= 55 && rate < 60) {
			id.css("color", "dodgerblue");
		} else if (rate >= 60) {
			id.css("color", "red");			
		}
		return;
	}
	
	function initKdaColor(id) {
		if(id.html() == undefined) {
			return;
		}
		var data = id.html().trim();
		var kda = data.substr(3);
		if(kda >= 3 && kda < 3.5) {
			id.css("color", "dodgerblue");
		} else if (kda >= 3.5) {
			id.css("color", "red");			
		}
		return;
	}
</script>
</head>
<body>
	<div id="header-container">
		<div class="header-title row">		
			<div class="header-img col-2">
				<img src="http://ddragon.leagueoflegends.com/cdn/10.9.1/img/profileicon/${svo.profileIconId }.png" alt="ProfileIcon" />
				<div id="title-level">
					${svo.summonerLevel }
				</div>
			</div>
			<div class="col-8">
				<h3>${svo.name }</h3>
				마지막 랭크 게임 시간 : ${svo.lastGameTime }
			</div>
			
		</div>
	</div>
	<div id="card-container">
		<img src="http://ddragon.leagueoflegends.com/cdn/img/champion/splash/${championList.get(svo.scvo.championId)[0] }_0.jpg" class="card-img-top" alt="...">
		<nav>
			<div class="nav nav-tabs" id="nav-tab" role="tablist">
				<a class="nav-item nav-link active match-info" data-toggle="tab" href="#nav-home" role="tab" 
					aria-controls="nav-home" aria-selected="true">솔로랭크</a>
				<a class="nav-item nav-link match-info" data-toggle="tab" href="#nav-profile" role="tab" 
		      	    aria-controls="nav-profile" aria-selected="false" >자유랭크</a>
			</div>
		</nav>
		<div class="tab-content" id="nav-tabContent">
			<c:set value="RANKED_SOLO_5x5" var="solorank"></c:set>
			<c:set value="RANKED_FLEX_SR" var="flexrank"></c:set>
			<c:set value="10" var= "championCount"></c:set>
			<div class="tab-pane fade show active container" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
				<!-- 탭 Pane 솔랭 -->
				<div class="row">
					<c:set value="${svo.levo.get(solorank).tier }" var="tier"></c:set>
			  		<div id="card-tier" class="col">
			  			<c:if test="${svo.levo.get(solorank) eq null }">
			  				<div class="tier-content">				  				
								<img src="../resources/images/emblem/UNRANKED.png" alt="TIER_IMAGE" />
			  				</div>
			  				<div class="tier-content">				  				
								<div class="tier-rank-title">UNRANKED</div>
			  				</div>
			  			</c:if>
			  			<c:if test="${svo.levo.get(solorank) ne null}">	
			  				<div class="tier-content">				  				
								<img src="../resources/images/emblem/${tier }.png" alt="TIER_IMAGE" />
			  				</div>
			  				<div class="tier-content">				  				
					  			<div class="tier-rank-title">${tier } ${svo.levo.get(solorank).rank }</div>
					  			<div class="tier-rank-detail">
					  				<div id="tier-rank-lp">					  				
							  			<span>${svo.levo.get(solorank).leaguePoints }LP</span> 
							  			<c:if test="${svo.levo.get(solorank).leaguePoints eq 100}">
							  				<span style="color:red;">승급전</span>
							  			</c:if>
					  				</div>		
						  			<span>${svo.levo.get(solorank).wins }승 ${svo.levo.get(solorank).losses }패 </span><br />
						  			<span id="tier-rank-winrate-solo">승률 <fmt:formatNumber value="${svo.levo.get(solorank).winRate }" pattern="#"/>% </span><br />
					  			</div>
			  				</div>	  			
			  			</c:if>	  				
			  		</div>
			  		<div id="card-lane" class="col">
			  			<c:if test="${svo.soloGames.matches eq null }">
			  				기록이 없습니다.
			  			</c:if>
			  			<c:if test="${svo.soloGames.matches ne null }">			  			
				  			<c:set value="${svo.soloGames.matches }" var="matches"></c:set>
				  			<div class="lane-title">				  			
					  			최근 ${fn:length(matches)} 게임 역할 <br />
				  			</div>
				  			<c:forEach var="lane_name" items="${svo.soloGames.roleCntList }" varStatus="i" begin="0" end="1">
								<c:set value="${svo.soloGames.roleCntList.get(i.index)}" var="lane"></c:set>
					  			<div class="lane-content">			  
					  				<c:if test="${tier eq null }">
							  			<img src="../resources/images/lane/IRON-${lane}.png" alt="LANE_IMG" /> 
					  				</c:if>			
					  				<c:if test="${tier ne null }">					  				
							  			<img src="../resources/images/lane/${tier}-${lane}.png" alt="LANE_IMG" /> 
					  				</c:if>
					  				<br />
					  				${laneInfo.get(lane) } <fmt:formatNumber value="${(svo.soloGames.roleCnt.get(lane) / fn:length(matches)) * 100}" pattern="#"/>%  <br />
						  			${svo.soloGames.roleCnt.get(lane) } 게임
						  			<br />
					  			</div>
				  			</c:forEach>
				  			<div class="lane-reliability">				  			
					  			신뢰도 ${svo.soloGames.reliability }%
				  			</div>
			  			</c:if>
			  		</div>
			  		<div id="card-detail" class="col-6">
			  			<c:if test="${svo.soloGames.matches eq null }">
			  				기록이 없습니다.
			  			</c:if>
			  			<c:if test="${svo.soloGames.matches ne null }">
			  				<div class="detail-title">
			  					최근
				  				<c:if test="${fn:length(matches) lt championCount }">			  				
				  					${fn:length(matches)}		  				
				  				</c:if>
				  				<c:if test="${fn:length(matches) ge championCount }">			  				
				  					${championCount }		  				
				  				</c:if>
				  				게임의 챔피언 TOP 3
			  				</div>
							<c:forEach var="champ_id" items="${svo.soloGames.championCntList }" varStatus="i" begin="0" end="2">
								<c:if test="${champ_id ne null }">								
					  				<div class="row detail-content">
										<div class="detail-content-champ col-4">
											<c:set value="${championList.get(champ_id)[0]}" var="champName"></c:set>
								  			<img src="http://ddragon.leagueoflegends.com/cdn/10.9.1/img/champion/${champName }.png" alt="MOST_CHAMPION" />
										</div>
							  			<div class="detail-content-champInfo col-8">
							  				<c:set value="${svo.soloGames.championRecordMap.get(champ_id) }" var="champ_record"></c:set>
							  				<div class="champInfo-name">						  				
								  				${championList.get(champ_id)[1]}
							  				</div>
							  				<div class="champInfo-record">
							  					<span id="record-winrate-solo${i.count }">
									  				<fmt:formatNumber value="${ (champ_record.totalWin / champ_record.playedCnt) * 100}" pattern="#" />%
							  					</span>
								  				(${champ_record.totalWin }승 / ${champ_record.playedCnt - champ_record.totalWin }패)
								  				<span id="record-kda-solo${i.count }">
									  				KDA <fmt:formatNumber value="${ (champ_record.totalKills + champ_record.totalAssists) / champ_record.totalDeaths }" pattern="#.##" />
								  				</span>
								  				<br />
								  				평균 딜량 <fmt:formatNumber value="${champ_record.totalDamageDealt / champ_record.playedCnt }" pattern="#" />  
								  				<br />
								  				평균 게임 시간 <fmt:formatNumber value="${(champ_record.totalGameDuration / champ_record.playedCnt) / 60 }" pattern="#" /> 분대
							  				</div>
							  			</div>
					  				</div>
								</c:if>
							</c:forEach>
			  			</c:if>
			  		</div>
				</div>
				<!-- 탭 Pane 솔랭 끝 -->
			</div>
			
			<!--  <div class="tab-pane fade show active container" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab"> -->
			<!-- 탭 Pane 자랭 -->
		    <div class="tab-pane fade container" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
		    	<div class="row">
					<c:set value="${svo.levo.get(flexrank).tier }" var="tier"></c:set>
			  		<div id="card-tier" class="col">
			  			<c:if test="${svo.levo.get(flexrank) eq null }">
			  				<div class="tier-content">				  				
								<img src="../resources/images/emblem/UNRANKED.png" alt="TIER_IMAGE" />
			  				</div>
			  				<div class="tier-content">				  				
								<div class="tier-rank-title">UNRANKED</div>
			  				</div>
			  			</c:if>
			  			<c:if test="${svo.levo.get(flexrank) ne null}">	
			  				<div class="tier-content">				  				
								<img src="../resources/images/emblem/${tier }.png" alt="TIER_IMAGE" />
			  				</div>
			  				<div class="tier-content">				  				
					  			<div class="tier-rank-title">${tier } ${svo.levo.get(flexrank).rank }</div>
					  			<div class="tier-rank-detail">
					  				<div id="tier-rank-lp">					  				
							  			<span>${svo.levo.get(flexrank).leaguePoints }LP</span> 
							  			<c:if test="${svo.levo.get(flexrank).leaguePoints eq 100}">
							  				<span style="color:red;">승급전</span>
							  			</c:if>
					  				</div>		
						  			<span>${svo.levo.get(flexrank).wins }승 ${svo.levo.get(flexrank).losses }패 </span><br />
						  			<span id="tier-rank-winrate-flex">승률 <fmt:formatNumber value="${svo.levo.get(flexrank).winRate }" pattern="#"/>% </span><br />
					  			</div>
			  				</div>	  			
			  			</c:if>	  				
			  		</div>
			  		<div id="card-lane" class="col">
			  			<c:if test="${svo.flexGames.matches eq null }">
			  				기록이 없습니다.
			  			</c:if>
			  			<c:if test="${svo.flexGames.matches ne null }">			  			
				  			<c:set value="${svo.flexGames.matches }" var="matches"></c:set>
				  			<div class="lane-title">				  			
					  			최근 ${fn:length(matches)} 게임 역할 <br />
				  			</div>
				  			<c:forEach var="lane_name" items="${svo.flexGames.roleCntList }" varStatus="i" begin="0" end="1">
								<c:set value="${svo.flexGames.roleCntList.get(i.index)}" var="lane"></c:set>
					  			<div class="lane-content">			  
					  				<c:if test="${tier eq null }">
							  			<img src="../resources/images/lane/IRON-${lane}.png" alt="LANE_IMG" /> 
					  				</c:if>			
					  				<c:if test="${tier ne null }">					  				
							  			<img src="../resources/images/lane/${tier}-${lane}.png" alt="LANE_IMG" /> 
					  				</c:if>
					  				<br />
					  				${laneInfo.get(lane) } <fmt:formatNumber value="${(svo.flexGames.roleCnt.get(lane) / fn:length(matches)) * 100}" pattern="#"/>%  <br />
						  			${svo.flexGames.roleCnt.get(lane) } 게임
						  			<br />
					  			</div>
				  			</c:forEach>
				  			<div class="lane-reliability">				  			
					  			신뢰도 ${svo.flexGames.reliability }%
				  			</div>
			  			</c:if>
			  		</div>
			  		<div id="card-detail" class="col-6">
			  			<c:if test="${svo.flexGames.matches eq null }">
			  				기록이 없습니다.
			  			</c:if>
			  			<c:if test="${svo.flexGames.matches ne null }">
			  				<div class="detail-title">
			  					최근
				  				<c:if test="${fn:length(matches) lt championCount }">			  				
				  					${fn:length(matches)}		  				
				  				</c:if>
				  				<c:if test="${fn:length(matches) ge championCount }">			  				
				  					${championCount }		  				
				  				</c:if>
				  				게임의 챔피언 TOP 3
			  				</div>
							<c:forEach var="champ_id" items="${svo.flexGames.championCntList }" varStatus="i" begin="0" end="2">
								<c:if test="${champ_id ne null }">
					  				<div class="row detail-content">
										<div class="detail-content-champ col-4">
											<c:set value="${championList.get(champ_id)[0]}" var="champName"></c:set>
								  			<img src="http://ddragon.leagueoflegends.com/cdn/10.9.1/img/champion/${champName }.png" alt="MOST_CHAMPION" />
										</div>
							  			<div class="detail-content-champInfo col-8">
							  				<c:set value="${svo.flexGames.championRecordMap.get(champ_id) }" var="champ_record"></c:set>
							  				<div class="champInfo-name">						  				
								  				${championList.get(champ_id)[1]}
							  				</div>
							  				<div class="champInfo-record">
							  					<span id="record-winrate-flex${i.count }">
									  				<fmt:formatNumber value="${ (champ_record.totalWin / champ_record.playedCnt) * 100}" pattern="#" />%
							  					</span>
								  				(${champ_record.totalWin }승 / ${champ_record.playedCnt - champ_record.totalWin }패)
								  				<span id="record-kda-flex${i.count }">
									  				KDA <fmt:formatNumber value="${ (champ_record.totalKills + champ_record.totalAssists) / champ_record.totalDeaths }" pattern="#.##" />
								  				</span>
								  				<br />
								  				평균 딜량 <fmt:formatNumber value="${champ_record.totalDamageDealt / champ_record.playedCnt }" pattern="#" />  
								  				<br />
								  				평균 게임 시간 <fmt:formatNumber value="${(champ_record.totalGameDuration / champ_record.playedCnt) / 60 }" pattern="#" /> 분대
							  				</div>
							  			</div>
					  				</div>
								</c:if>			
							</c:forEach>
			  			</c:if>
			  		</div>
				</div>
		    </div>
		    <!-- 탭 Pane 자랭 -->
		</div>
	</div>
</body>
</html>