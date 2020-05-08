<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>랭크 게임 매칭 게시판</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/boardCss.css" />

<script type="text/javascript">
	$(function() {

	});
	
	function tierChange(e) {
		$("#division option").remove();
		var unranked = ["언랭"];
		var ranked = ["1", "2", "3", "4", "전체"];
		
		var target = $("#division");
		if(e.value == '언랭') {
			var arr = unranked;
		} else if(e.value == 'none'){
			target.append("<option value=none>-----------------</option>");
			return;
		} else {
			var arr = ranked;						
		}
		for(i in arr) {
			target.append("<option value="+arr[i]+">"+arr[i]+"</option>");
		}
	}
	
	function formCheck() {
		// LOGIC
		// 제목 입력
		var data = $("#title").val();
		if(!data || data.trim().length==0){
			alert('제목은 반드시 입력해야 합니다.');
			$("#title").val(data);
			$("#title").focus();
			return false;
		}
		// content 입력
		data = $("#content").val();
		if(!data || data.trim().length==0){
			alert('내용은 반드시 입력해야 합니다.');
			$("#content").val(data);
			$("#content").focus();
			return false;
		}
		// tier + division validation
		data = $("#tier").val();
		if(data == 'none') {
			alert('티어를 반드시 지정해야합니다.');
			return false;
		}
		return true;
	}
</script>
</head>
<body>
	<!-- Search/Write Accordion Menu -->
	<div id="accor-container">
		<div id="accor-content">		
			<p>
				<a id="searchBtn" class="btn btn-outline-dark" data-toggle="collapse" href="#accor-search" role="button" 
					aria-expanded="false" aria-controls="accor-search">
					검색
				</a>
				<button id="writeBtn" class="btn btn-outline-dark" type="button" data-toggle="collapse" data-target="#accor-write" 
					aria-expanded="false" aria-controls="accor-write">
					등록
				</button>
			</p>
			<div class="row">
				<div id="accor-search-container">
			    	<div class="collapse multi-collapse" id="accor-search">
			      		<div class="card card-body">
			      			
			      		</div>
			    	</div>
				</div>
				<div id="accor-write-container">
					<div class="collapse multi-collapse" id="accor-write">
						<div class="card card-body">
							<form id="writeForm" action="${pageContext.request.contextPath }/matching/board/writeProcess" method="POST" onsubmit="return formCheck();">
								<div class="form-row">
									<div class="form-group col-md-6">
							      		<label for="nickName">닉네임</label>
							      		<input type="text" class="form-control" id="nickName" name="nickName" value="${nickName }" readonly="readonly">
							    	</div>
							    	<div class="form-group col-md-6">
							      		<label for="summonerName">소환사 이름</label>
							      		<input type="text" class="form-control" id="summonerName" name="summonerName" value="${summonerName }" readonly="readonly">
							    	</div>
							  	</div>
							  	<div class="form-group">
							    	<label for="title">제목</label>
							    	<input type="text" class="form-control" id="title" name="title" placeholder="글 제목">
							  	</div>
							  	<div class="form-group">
							    	<label for="content">내용</label>
							    	<textarea class="form-control" id="content" name="content" rows="3"></textarea>
							  	</div>
							  	<div class="form-row">
							    	<div class="form-group col-md-6">
							      		<label for="tier">티어</label>
							      		<select id="tier" name="tier" class="form-control" onchange="tierChange(this);">
							        		<option value="none" selected="selected">-----------------</option>
							        		<option value="언랭">언랭</option>
							        		<option value="아이언">아이언</option>
							        		<option value="브론즈">브론즈</option>
							        		<option value="실버">실버</option>
							        		<option value="골드">골드</option>
							        		<option value="플레티넘">플레티넘</option>
							        		<option value="다이아">다이아</option>
							      		</select>
							    	</div>
							    	<div class="form-group col-md-6">
							      		<label for="division">단계</label>
							      		<select id="division" name="division" class="form-control">							      			
							        		<option value="none" selected="selected">-----------------</option>
							      		</select>
							    	</div>
							  	</div>
							  	<div class="form-row">
							    	<div class="form-group col-md-6">
							      		<label for="queueType">랭크 타입</label>
							      		<select id="queueType" name="queueType" class="form-control">
							        		<option value="솔랭" selected="selected">솔로 랭크</option>
							        		<option value="자랭">자유 랭크</option>
							      		</select>
							    	</div>
							    	<div class="form-group col-md-6">
							      		<label for="lane">포지션</label>
							      		<select id="lane" name="lane" class="form-control">
							        		<option value="무관" selected="selected">상관없음</option>
							        		<option value="탑">탑</option>
							        		<option value="미드">미드</option>
							        		<option value="정글">정글</option>
							        		<option value="서포터">서포터</option>
							        		<option value="원딜">원딜</option>
							      		</select>
							    	</div>
							  	</div>
							  	<div class="form-row">
							    	<div class="form-group col-md-6">
							      		<label for="expectedTime">플레이 시간 대</label>
							      		<select id="expectedTime" name="expectedTime" class="form-control">
							        		<option value="all" selected="selected">상관없음</option>
							        		<option value="time1">00시-06시</option>
							        		<option value="time2">06시-12시</option>
							        		<option value="time3">12시-18시</option>
							        		<option value="time4">18시-00시</option>
							      		</select>
							    	</div>
							    	<div class="form-group col-md-6">
							      		<label for="isVoice">보이스 사용 여부</label>
							      		<select id="isVoice" name="isVoice" class="form-control">
							        		<option value="all" selected="selected">상관없음</option>
							        		<option value="necessary">보이스 필수</option>
							      		</select>
							    	</div>
							  	</div>
							  	<button id="submitBtn" type="submit" class="btn btn-outline-danger">등록</button>
							</form>
				      	</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- Board List -->
	<div id="board-accor-container">
		<div id="board-accor-content">		
			<c:if test="${pagingVO.totalCount eq 0 }">
				게시글이 존재하지 않습니다.
			</c:if>
			<c:if test="${pagingVO.totalCount gt 0 }">
				<div id="accordionBoard">
					<c:forEach var="pvo" items="${pagingVO.list }" varStatus="i">
						<!-- 게시글 하나 -->
						<div class="card">
							<div class="card-header" id="heading${i.count }">
								<div id="board-">
									<div id="timeZone">
										<!-- 날짜 정보를 담은 정보를 해당 포맷팅으로 -->
										<fmt:formatDate var="boardFormatDate" value="${pvo.regDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
										<!-- 포맷팅한 데이터를 java의 Date type으로 바꿈 -->
										<fmt:parseDate var="boardParseDate" value="${boardFormatDate }" pattern="yyyy-MM-dd HH:mm:ss"/>
										<!-- Date객체의 getTime 내장 메소드로 millisec단위로 변환 -->
										<fmt:parseNumber var="boardTime" value="${boardParseDate.time }" integerOnly="true"/>
										<c:set var="diffTime" value="${(todayTime - boardTime) }"></c:set>
										<c:if test="${diffTime lt 60000 }">										
											<fmt:formatNumber var="diffTime" value="${diffTime / 1000}" pattern="#"/>
											${diffTime }초 전
										</c:if>
										<c:if test="${diffTime ge 60000 and diffTime lt 3600000}">										
											<fmt:formatNumber var="diffTime" value="${diffTime / 60000}" pattern="#"/>
											${diffTime }분 전
										</c:if>
										<c:if test="${diffTime ge 3600000 }">										
											<fmt:formatNumber var="diffTime" value="${diffTime / 3600000}" pattern="#"/>
											${diffTime }시간 전
										</c:if>
									</div>
								
								</div>
		          				<div>${pvo.nickName }</div>
								<div>${pvo.summonerName }</div>
								<div>
									<c:out value="${pvo.title }"></c:out>
								</div>
								<div>
									<!--  
									개행 쓸거면 여기 사용
									<c:set value="${pvo.content }" var="content"></c:set>
									<c:set value="${fn:replace(content, '<', '&nbsp;') }"  var="content" ></c:set>
									<c:set value='${fn:replace(content, newLine, br) }' var="content"></c:set>
									${content }
									-->
									<!-- 개행 안쓸거면 이걸로 사용 -->
									<c:out value="${pvo.content }"></c:out>																		
								</div>
								<div>${pvo.tier }</div>
								<div>${pvo.division }</div>
								<div>${pvo.lane }</div>
								<div>${pvo.queueType }</div>
								<div>${pvo.expectedTime }</div>
								<div>${pvo.isVoice }</div>
								<h2 class="mb-0">
									<button class="btn btn-link" type="button" data-toggle="collapse" data-target="#collapse${i.count }" 
									aria-expanded="true" aria-controls="collapse${i.count }">
										상세보기
						        	</button>
						      	</h2>
						    </div>
						    
							<!-- 글쓴이 전적 -->
						    <div id="collapse${i.count }" class="collapse" aria-labelledby="heading${i.count }" data-parent="#accordionBoard">
								<div class="card-body">
									전적 보여줄 예정
						      	</div>
							</div>
						</div>
					</c:forEach>
				</div>
				<!-- Paginatoon -->
				<div id="board-pagination">
					${pagingVO.pageListPost }
				</div>
			</c:if>
		</div>
	</div>
</body>
</html>