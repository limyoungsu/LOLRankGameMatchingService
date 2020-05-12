<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<c:if test="${function eq 'modify' }">
<title>정보 수정</title>
</c:if>
<c:if test="${function eq 'join' }">
<title>회원가입</title>
</c:if>
<style type="text/css">

</style>
<!-- Vendor JS-->
<script src="${pageContext.request.contextPath }/resources/vendor/datepicker/moment.min.js"></script>
<script src="${pageContext.request.contextPath }/resources/vendor/datepicker/daterangepicker.js"></script>

<!-- Vendor CSS-->
<link href="${pageContext.request.contextPath }/resources/vendor/datepicker/daterangepicker.css" rel="stylesheet" media="all">

<script type="text/javascript">
	$(function(){
		// daterangepicker설정 변경
		$('input[name="birth"]').daterangepicker({
			singleDatePicker: true,
			showDropdowns: true,
			locale: {
				format: 'YYYY/MM/DD'
			}
		});
	});
	// 입력값이 올바른지 검사하기 위해
	function formCheck() {
		// LOGIC
		if($("#idCheckValidation").css('color') != 'rgb(0, 255, 127)'){
			alert('중복된 아이디입니다.');
			$("#userId").val("");
			$("#userId").focus();
			return false;
		}
		// id 입력
		var data = $("#userId").val();
		if(!data || data.trim().length==0){
			alert('아이디는 반드시 입력해야 합니다.');
			$("#userId").val(data);
			$("#userId").focus();
			return false;
		}
		// id 메일주소 포맷 확인
		if(!validateEmail(data)){
			alert('아이디가 이메일 형식이 아닙니다.');
			$("#userId").val(data);
			$("#userId").focus();
			return false;
		}
		
		// 비밀번호 반드시 입력
		var data = $("#password").val();
		if(!data || data.trim().length==0){
			alert('비밀번호는 반드시 입력해야 합니다.');
			$("#password").val("");
			$("#password").focus();
			return false;
		}
		var data = $("#password-re").val();
		if(!data || data.trim().length==0){
			alert('비밀번호 확인은 반드시 입력해야 합니다.');
			$("#password-re").val("");
			$("#password-re").focus();
			return false;
		}
		
		// password는 8자리 ~ 14자리로 구성 : 영대/소문자+숫자+특수문자 포함하게끔 변경
		
		
		// password == password-re 이어야 함
		if($("#password").val() != $("#password-re").val()){
			alert('비밀번호가 일치하지 않습니다.');
			$("#password").val("");
			$("#password-re").val("");
			$("#password").focus();
			return false;
		}
		// 사용자 이름 반드시 입력
		var data = $("#userName").val();
		if(!data || data.trim().length==0){
			alert('사용자 이름은 반드시 입력해야 합니다.');
			$("#userName").val("");
			$("#userName").focus();
			return false;
		}
		
		// 사용자 이름은 2자리 ~ 10자리로 구성
		
		
		// 별명 반드시 입력
		var data = $("#nickName").val();
		if(!data || data.trim().length==0){
			alert('사용자 별명은 반드시 입력해야 합니다.');
			$("#nickName").val("");
			$("#nickName").focus();
			return false;
		}
		
		// 별명은 4자리~12자리로 구성
		
		
		// 소환사 이름 반드시 입력
		var data = $("#summonerName").val();
		if(!data || data.trim().length==0){
			alert('소환사 이름은 반드시 입력해야 합니다.');
			$("#summonerName").val("");
			$("#summonerName").focus();
			return false;
		}
		
		// 생년월일 유효성 : 현재 날짜보다 빨라야함
		var data = $("#birth").val();
		var year = data.substr(0, 4);
		var month = data.substr(5, 2);
		var dayOfMonth = data.substr(8, 2);
		var today = new Date();
		var currYear = today.getFullYear();
		var currMonth = today.getMonth() + 1;
		var currDate = today.getDate();

		if(year > currYear) {
			alert("생년월일이 유효하지 않습니다.");
			$("#birth").val(currYear+"/"+currMonth+"/"+currDate);
			return false;
		} else if(year == currYear) {
			if(month > currMonth){
				alert("생년월일이 유효하지 않습니다.");
				$("#birth").val(currYear+"/"+currMonth+"/"+currDate);
				return false;
			} else if(month == currMonth) {
				if(dayOfMonth > currDate) {
					alert("생년월일이 유효하지 않습니다.");
					$("#birth").val(currYear+"/"+currMonth+"/"+currDate);
					return false;
				}
			}
		}
		
		// 휴대폰 번호 반드시 입력
		var data = $("#phone").val();
		if(!data || data.trim().length==0){
			alert('전화번호는 반드시 입력해야 합니다.');
			$("#phone").val("");
			$("#phone").focus();
			return false;
		}
		
		// 휴대폰 번호 포맷 확인
		if(!validatePhone(data)){
			alert('전화번호 형식은 000-0000-0000입니다.');
			$("#phone").val("");
			$("#phone").focus();
			return false;
		}
		
		return true;
	}
	
	// 각 유효성 확인
	function idCheck(value, minLen, url) {
		var selector = url + "Validation";
		if(value.length > minLen) {
			$.ajax(url, {
				type : 'GET',
				data : {'id' : value},
				dataType : 'json',
				error : function() {
					console.log("Fail");
				},
				success : function(data) {
					// 반환값이 1이면 성공, 아니면 실패
					if(data == 1)
						$('#' + selector).css('color','crimson').html("사용할 수 없습니다.");
					else 
						$('#' + selector).css('color','springgreen').html("사용할 수 있습니다.");
				}
			});
		} else {
			$('#' + selector).html("");
		}
	}
 	
	// 소환사명 체크
	function summonerNameCheck() {
		var value = $("#summonerName").val();
		value = encodeURI(value);
		if(value.length > 0) {
			$.ajax("summonerNameCheck", {
				type : 'GET',
				data : {'summonerName' : value},
				dataType: 'json',
				error : function() {
					console.log("Fail");
				},
				success : function(res) {
					if(res == 1) {
						alert("이미 가입된 소환사명입니다.");
						$('#summonerNameCheckValidation').css('color','crimson').html("사용할 수 없습니다.");
					} 
					else if(res == 2) {
						alert("RIOT에 존재하지 않는 소환사명입니다.")
						$('#summonerNameCheckValidation').css('color','crimson').html("사용할 수 없습니다.");
					}					
					else {
						alert("사용 가능한 소환사명입니다.")
						$('#summonerNameCheckValidation').css('color','springgreen').html("사용할 수 있습니다.");
					} 						
				}
			});
		}
	}
	
	// email 주소 format
	function validateEmail(email) {
		var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
		return re.test(email);
	}
	
	// 전화번호 format
	function validatePhone(phone) {
		var re = /^\d{2,3}-\d{3,4}-\d{4}$/;
		return re.test(phone);
	}
	
</script>
</head>
<body>
	<div id="join-main" class="main-container">
		<c:if test="${function eq 'modify' }">
			<span class="title">정보 수정</span>
			<form id="formbox" action="${pageContext.request.contextPath }/member/modify-working" method="post" onsubmit="return formCheck();">
		</c:if>
		<c:if test="${function eq 'join' }">
			<span class="title">JOIN</span>
			<form id="formbox" action="${pageContext.request.contextPath }/member/joinComplete" method="post" onsubmit="return formCheck();">
		</c:if>			
			<div class="input-container">
				<div class="input-name">계정 이름</div>
				<div>
					<c:if test="${function eq 'modify' }">
						<input type="text" name="userId" id="userId" placeholder="계정 이름  : 메일주소" maxlength="20" readonly="readonly" value="${vo.userId }"/> 			
						<div class="validation-container">
							<span id="idCheckValidation" style="color:rgb(0, 255, 127);"></span> 
						</div>
					</c:if>
					<c:if test="${function eq 'join' }">
						<input type="text" name="userId" id="userId" placeholder="계정 이름  : 메일주소" maxlength="20" onkeyup="idCheck(this.value, '10', 'idCheck');"/> 			
						<div class="validation-container">
							<span id="idCheckValidation"></span> 
						</div>
					</c:if>
				</div>
			</div>
			
			<div class="input-container">
				<div class="input-name">비밀번호</div>
				<div>
					<input type="password" name="password" maxlength="14" id="password" placeholder="비밀번호 : 8-14자리"/>		
				</div>
			</div>
			
			<div class="input-container">
				<div class="input-name">비밀번호 확인</div>
				<div>
					<input type="password" name="password-re" maxlength="14" id="password-re" placeholder="비밀번호 확인"/>		
				</div>
			</div>
			
			<div class="input-container">
				<div class="input-name">사용자 이름</div>
				<div>
					<c:if test="${function eq 'modify' }">
						<input type="text" name="userName" maxlength="20" id="userName" value="${vo.userName }" readonly="readonly"/>
					</c:if>
					<c:if test="${function eq 'join' }">
						<input type="text" name="userName" maxlength="20" id="userName" placeholder="사용자 이름"/>
					</c:if>
				</div>
			</div>
			
			<div class="input-container">
				<div class="input-name">별명</div>
				<div>
					<input type="text" name="nickName" maxlength="20" id="nickName" placeholder="닉네임" onkeyup="idCheck(this.value, '2', 'nickNameCheck');"/>
				</div>
				<div class="validation-container">
					<span id="nickNameCheckValidation"></span> 
				</div>
			</div>
			
			<div class="input-container">
				<div class="input-name">소환사 이름</div>
				<div>
					<c:if test="${function eq 'modify' }">
						<input style="width: 195px;" type="text" name="summonerName" maxlength="20" id="summonerName" value="${vo.summonerName }" readonly="readonly"/> 			
						<input style="width: 50px;" type="button" value="인증">
					</c:if>
					<c:if test="${function eq 'join' }">
						<input style="width: 195px;" type="text" name="summonerName" maxlength="20" id="summonerName" placeholder="LOL 소환사 이름"/> 			
						<input style="width: 50px;" type="button" value="인증" onclick="summonerNameCheck();">
					</c:if>
				</div>
				<div class="validation-container">
					<span id="summonerNameCheckValidation"></span> 
				</div>
			</div>
			
			<div class="input-container">
				<div>			
					<c:if test="${function eq 'join' }">
						<div class="input-name">생년월일</div>
						<input class="input--style-2 js-datepicker" type="text" name="birth" id="birth" placeholder="생년월일" readonly="readonly"> 
					</c:if>
				</div>
			</div>
			
			<div class="input-container">
				<div class="input-name">휴대폰 번호</div>
				<div>
					<input type="text" name="phone" id="phone" maxlength="13" placeholder="전화번호 입력 : 000-0000-0000"> 
				</div>
			</div>
			
			<div id="btn-container">
				<c:if test="${function eq 'modify' }">
					<button class="btn btn-danger btn-func" type="submit">수정하기</button>
				</c:if>
				<c:if test="${function eq 'join' }">
					<button class="btn btn-danger btn-func" type="submit">회원가입</button>
				</c:if>		
				<!-- form안에서 input type을 button으로 하면 action 주소로 안가게 할 수 있음. -->
				<button class="btn btn-danger btn-func" type="button" onclick="location.href='${pageContext.request.contextPath}/'">홈으로</button>
			</div>
		</form>
	</div>		
</body>
</html>