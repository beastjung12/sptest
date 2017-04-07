<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>common/message.jsp</title>
</head>
<body>
	<%
		//String msg = (String)request.getAttribute("msg");
		//String url = (String)request.getAttribute("url"); //=>/pd/pdDetail.do
		
		//String ctxPath = request.getContextPath();  //=> /mymvc_board
		//url = ctxPath+url; // =>  /mymvc_board + /pd/pdDetail.do
						   // =>  /mymvc_board/pd/pdDetail.do
	%>
	
	<script type="text/javascript">
		//msg가 있을 때만 메세지 보여주기
		<c:if test="${!empty msg}">		
			alert("${msg}");	
		</c:if>
		
		location.href="<c:url value='${url}' />";
	</script>
</body>
</html>













