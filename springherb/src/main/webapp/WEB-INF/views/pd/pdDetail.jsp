<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
<%
	//PdDTO dto = (PdDTO)request.getAttribute("dto");

	//3. 결과 처리
	//DecimalFormat df = new DecimalFormat("#,###");	
%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>pdDetail.jsp</title>
<script type="text/javascript">
	function del(no){
		if(confirm("삭제하시겠습니까?")){
			location.href="<c:url value='/pd/pdDelete.do?no="+no+"' />";
		}
	}
</script>
</head>
<body>
<h1>상품 상세보기</h1>
<p>${dto.no}번을 클릭하였습니다</p>
<br>
<p>번호 : ${dto.no}</p>
<p>상품명 : ${dto.pdName}</p>
<p>가격 : <fmt:formatNumber value='${dto.price}' pattern="#,###" /> 원</p>
<p>등록일 : ${dto.regdate}</p>
<br>
<a href='<c:url value="/pd/pdList.do"/>'>목록</a> | 
<a href='<c:url value="/pd/pdEdit.do?no=${dto.no}"/>'>수정</a> | 
<a href="#" onclick="del(${dto.no})">삭제</a> 
</body>
</html>














