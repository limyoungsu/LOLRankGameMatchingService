<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 찾기</title>
<script type="text/javascript">
	function formCheck() {
		// LOGIC
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
		return true;
	}
	// email 주소 format
	function validateEmail(email) {
		var re = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
		return re.test(email);
	}
</script>
</head>
<body>
	<div class="main-container" style="padding-top: 200px;">
		<form id="formbox" action="${pageContext.request.contextPath }/member/sendpw" method="post" onsubmit="return formCheck();">		
			<span class="title">비밀번호 찾기</span>
			<div class="input-container">
				<div class="input-name">계정 이름</div>
				<div>
					<!-- value의 userId 는 Controller에서 Cookie의 userId 값을 넘긴 것 -->
					<input type="text" name="userId" placeholder="User ID" id="userId" value="${userId }" maxlength="20">		
				</div>
			</div>	
			<div class="err-container">
				<span>
					<c:out value="${errMsg }"></c:out>
				</span>
			</div>
			<div id="btn-container">		
				<button class="btn btn-danger btn-func" type="submit">찾기</button>
				<button class="btn btn-danger btn-func" type="button" onclick="location.href='${pageContext.request.contextPath}/'">홈으로</button>
			</div>
		</form>
	</div>
</body>
</html>