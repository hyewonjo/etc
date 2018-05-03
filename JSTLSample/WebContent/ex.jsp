<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:set var="varName" value="varValue" />
	<c:out value="${varName }" /><br/>
	<c:remove var="varName" />
	<c:out value="${varName }" /><br/>
	<c:forEach var="fe" begin="0" end="100" step="5">
	${fe }
	</c:forEach>
</body>
</html>