<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메일 전송 완료</title>
</head>
<body>
	<div id="mail-main" class="main-container">
		<span class="subtitle">메일 전송 완료</span>
		<div class="msgbox">		
			기존 비밀번호가 초기화되었습니다 <br>
			입력한 ID로 메일이 전송되었으니 로그인 해주시기 바랍니다. <br>
		</div>
		<button class="btn btn-danger btn-func" type="button" onclick="location.href='${pageContext.request.contextPath}/'" style="width:200px;">
			홈으로
		</button>
	</div>			
</body>
</html>