<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%
	//PdDTO dto = (PdDTO)request.getAttribute("pdDto");
%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>pdEdit.jsp</title>
<script type="text/javascript">
	function send(form){
		if(form.pdName.value==""){
			alert("상품명을 입력하세요");
			form.pdName.focus();
			return false;
		}else if(form.price.value.length<1){
			alert("가격을 입력하세요");
			form.price.focus();
			return false;			
		}
		
		return true;		
	}
</script>
</head>
<body>
<h1>상품 수정</h1>
<form name="frmWrite" method="post" 
	action='<c:url value="/pd/pdEdit.do" />' 
	onsubmit="return send(this)">
	<!-- update시 no가 필요하므로 hidden field에 넣어서 보낸다 -->
	<input type="hidden" name="no" value="${pdDto.no}">
	
	상품명 : <input type="text" name="pdName" value="${pdDto.pdName}"><br>
	가격 : <input type="text" name="price" value="${pdDto.price}"><br><br>
	<input type="submit" value="수정">
	<input type="reset" value="취소">
</form>
<br>
<a href='<c:url value="/pd/pdList.do" />'>목록</a>

</body>
</html>









