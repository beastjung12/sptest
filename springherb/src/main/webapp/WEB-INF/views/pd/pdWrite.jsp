<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>pd/pdWrite.jsp</title>
</head>
<body>
<h1>상품 등록</h1>
<form name="frmWrite" method="post" 
	action='<c:url value="/pd/pdWrite.do" />' >
	상품명 : <input type="text" name="pdName"><br>
	가격 : <input type="text" name="price"><br><br>
	<input type="submit" value="등록">
	<input type="reset" value="취소">
</form>
<br>
<a href='<c:url value="/pd/pdList.do"/>'>목록</a>

</body>
</html>











