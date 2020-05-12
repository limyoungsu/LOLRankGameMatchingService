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
<c:if test="${function eq 'secession' }">
<title>회원 탈퇴</title>
</c:if>
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
		<c:if test="${function eq 'secession' }">
			<form id="formbox" action="${pageContext.request.contextPath }/member/secession-working" method="post" onsubmit="return formCheck();">				
			<span class="title">회원 탈퇴</span>
		</c:if>
		<c:if test="${function eq 'modify' }">
			<form id="formbox" action="${pageContext.request.contextPath }/member/modifyform" method="post" onsubmit="return formCheck();">		
			<span class="title">정보 수정</span>
		</c:if>
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
				<c:if test="${function eq 'secession' }">
					<button class="btn btn-danger btn-func" type="submit">탈퇴</button>
				</c:if>	
				<c:if test="${function eq 'modify' }">
					<button class="btn btn-danger btn-func" type="submit">확인</button>
				</c:if>	
				<button class="btn btn-danger btn-func" type="button" onclick="location.href='${pageContext.request.contextPath}/'">홈으로</button>
			</div>
		</form>
	</div>
</body>
</html>