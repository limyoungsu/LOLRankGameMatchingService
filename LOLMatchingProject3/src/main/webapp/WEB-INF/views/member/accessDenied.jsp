<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8"/>
	<title>접근금지</title>
</head>
<body>
	Dear <strong>${userId}</strong>, You are not authorized to access this page
    <a href="<c:url value="/logout" />">Logout</a>
</body>
</html>
