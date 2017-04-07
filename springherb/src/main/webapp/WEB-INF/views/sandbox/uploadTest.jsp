<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>sandbox/uploadTest.jsp</title>
</head>
<body>
	<h1>upload 연습</h1>
	<form name="frm1" method="post" 
		action='<c:url value="/test/uploadTest.do" />'
		enctype="multipart/form-data">
		주소 : <input type="text" name="address"><br>
		파일첨부 : <input type="file" name="upfile"><br><br>
		<input type="submit" value="전송">
	</form>
</body>
</html>




