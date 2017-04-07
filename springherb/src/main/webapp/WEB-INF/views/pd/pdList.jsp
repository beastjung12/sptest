<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<%
	//뷰페이지 - Controller에서 저장한 결과를 읽어와서 화면 처리
	/* List<PdDTO> alist = (List<PdDTO>)request.getAttribute("pdList");

	DecimalFormat df = new DecimalFormat("#,###");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  */
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>상품 목록</h1>
	<table border="1" width="500">
		<tr>
			<th>번호</th>
			<th>상품명</th>
			<th>가격</th>
			<th>등록일</th>
		</tr>
		<!--반복 시작 -->
	<% //for(int i=0;i<alist.size();i++){
	   //		PdDTO dto=alist.get(i);
	%>
	<c:forEach var="dto" items="${alist}">
		<tr>
			<td>${dto.no}</td>	
			<td><a href
		='<c:url value="/pd/pdDetail.do?no=${dto.no}"/>'>
			${dto.pdName}</a></td>	
			<td style="text-align: right;">
			<fmt:formatNumber value='${dto.price}' pattern="#,###" /> 원</td>	
			<td><fmt:formatDate value="${dto.regdate}" pattern="yyyy-MM-dd"/></td>
				
		</tr>
	</c:forEach>	
	<!-- 반복 끝 -->
</table>
<br>
<a href='<c:url value="/pd/pdWrite.do"/>'>상품등록</a>

</body>
</html>






