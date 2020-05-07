<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>랭크 게임 매칭 게시판</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/boardCss.css" />

<script type="text/javascript">
	$(function() {
		
	});
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
							<form id="writeForm" action="${pageContext.request.contextPath }/matching/board/writeProcess" method="POST">
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
							      		<select id="tier" name="tier" class="form-control">
							        		<option value="unranked" selected="selected">언랭</option>
							        		<option value="iron">아이언</option>
							        		<option value="bronze">브론즈</option>
							        		<option value="silver">실버</option>
							        		<option value="gold">골드</option>
							        		<option value="platinum">플레티넘</option>
							        		<option value="diamond">다이아</option>
							      		</select>
							    	</div>
							    	<div class="form-group col-md-6">
							      		<label for="division">단계</label>
							      		<select id="division" name="division" class="form-control">
							        		<option value="unranked" selected="selected">언랭-언랭 티어만 선택</option>
							        		<option value="1">I</option>
							        		<option value="2">II</option>
							        		<option value="3">III</option>
							        		<option value="4">IV</option>
							        		<option value="all">전체</option>
							      		</select>
							    	</div>
							  	</div>
							  	<div class="form-row">
							    	<div class="form-group col-md-6">
							      		<label for="queueType">랭크 타입</label>
							      		<select id="queueType" name="queueType" class="form-control">
							        		<option value="solo" selected="selected">솔로 랭크</option>
							        		<option value="flex">자유 랭크</option>
							      		</select>
							    	</div>
							    	<div class="form-group col-md-6">
							      		<label for="lane">포지션</label>
							      		<select id="lane" name="lane" class="form-control">
							        		<option value="all" selected="selected">상관없음</option>
							        		<option value="top">탑</option>
							        		<option value="mid">미드</option>
							        		<option value="jungle">정글</option>
							        		<option value="support">서포터</option>
							        		<option value="bot">원딜</option>
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

	
	<!-- Paginaton -->
</body>
</html>