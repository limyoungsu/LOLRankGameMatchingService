<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function formCheck() {
		var data = $("#password").val();
		var data2 = $("#dup_password").val();
		if((!data || data.trim().length == 0) || (!data2 || data2.trim().length == 0)) {
			alert('비밀번호를 입력해주세요.');
			$("#password").val("");
			$("#dup_password").val("");
			$("#password").focus();
			return false;
		}
		else {
			if(data != data2 ) {
				alert("두 비밀번호가 맞지 않습니다.")
				$("#password").val("");
				$("#dup_password").val("");
				$("#password").focus();
				return false;
			}
		}
		
		return true;
	}
</script>
</head>
<body>
	<div class="main-container" style="padding-top: 175px;">
		<form id="formbox" action="${pageContext.request.contextPath }/member/secessionProcess" method="post" onsubmit="return formCheck();">
			<span class="title">회원 탈퇴</span>
			<input type="hidden" name="userId" value="${userId }"/>
			<div class="input-container">
				<div class="input-name">비밀번호</div>
				<div>
					<!-- value의 userId 는 Controller에서 Cookie의 userId 값을 넘긴 것 -->
					<input type="password" name="password" placeholder="Password" id="password" maxlength="16">	
				</div>
			</div>	
			<div class="input-container">
				<div class="input-name">비밀번호 확인</div>
				<div>
					<!-- value의 userId 는 Controller에서 Cookie의 userId 값을 넘긴 것 -->
					<input type="password" name="dup_password" placeholder="Password 확인" id="dup_password" maxlength="16">	
				</div>
			</div>	
			<div class="err-container">
				<span>
					<c:out value="${errMsg }"></c:out>
				</span>
			</div>
			<div id="btn-container">			
				<button class="btn btn-danger btn-func" type="submit">탈퇴</button>
				<button class="btn btn-danger btn-func" type="button" onclick="location.href='${pageContext.request.contextPath}/'">홈으로</button>
			</div>
		</form>
	</div>
</body>
</html>