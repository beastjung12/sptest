<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>commentList.jsp</title>
</head>
<body>
<h1>comment 목록</h1>
<table border="1">
	<tr>
		<th>번호</th>
		<th>아이디</th>
		<th>날짜</th>
		<th>내용</th>
	</tr>	
	<c:forEach var="vo" items="${cmtList }">
		<tr>
			<td>${vo.commentNo }</td>
			<td>${vo.userId }</td>
			<td>${vo.regDate }</td>
			<td>${vo.commentContent }</td>
		</tr>
	</c:forEach>
	
</table>


</body>
</html>







