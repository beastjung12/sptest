<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>zipcode/zipcode.jsp</title>
<link href="<c:url value='/css/mainstyle.css'/>" rel="stylesheet" type="text/css">
<link href="<c:url value='/css/mystyle.css'/>" rel="stylesheet" type="text/css">
<style type="text/css">
	#divTbl table{
		width:430px;
	}
	#divTbl{
		margin-top: 10px;
	}
</style>
<script type="text/javascript" 
	src='<c:url value="/jquery/jquery-3.1.1.min.js" />'></script>

<script type="text/javascript">
	$(document).ready(function(){
		$("#dong").focus();
		
		$("#frmZip").submit(function(){
			if($("#dong").val()==''){
				alert('동을 입력하세요');
				$("#dong").focus();
				return false;
			}		
		});
		
	});



	window.onload= function(){
		frmZip.dong.focus();
	}

	function send(form){
		if(form.dong.value==''){
			alert('동을 입력하세요');
			form.dong.focus();
			return false;
		}
		
		return true;
	}
	
	function setZipcode(zipcode, address){
		//부모창에 우편번호, 주소 셋팅하기
		opener.document.frm1.zipcode.value=zipcode;
		opener.document.frm1.address.value=address;
		
		self.close();
	}
</script>
</head>
<body>
	<h2>우편번호 검색</h2>
	<div>
		<p>찾고 싶으신 주소의 동(읍,면)을 입력하세요</p>
		<form id="frmZip" name="frmZip" method="post" 
			action="<c:url value='/zipcode/zipcode.do'/>">
			<label for="dong">지역명</label>
			<input type="text" name="dong" id="dong">
			<input type="submit" value="찾기">
		</form>
	</div>
	<c:if test="${zipList !=null }">
		<div id="divTbl">
			<table class="box2" 
	summary="우편번호 검색 결과에 관한 표로써, 우편번호,주소에 대한 정보를 제공합니다.">
				<colgroup>
					<col style="width:20%">
					<col style="width:*">
				</colgroup>
				<thead>		
					<tr>
						<th scope="col">우편번호</th>
						<th scope="col">주소</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${empty zipList }">					
						<tr>
							<td colspan="2" class="align_center">
								해당하는 데이터가 없습니다.
							</td>
						</tr>
					</c:if>	
					<c:if test="${!empty zipList }">					
						<!-- 반복 시작 -->
						<c:forEach var="vo" items="${zipList }">
							<c:set var="address"
						value="${vo.sido} ${vo.gugun } ${vo.dong}" />
							<c:set var="bunji" value="" />
							<c:if test="${!empty vo.endbunji }">
								<c:set var="bunji" 
						value="${vo.startbunji } ~ ${vo.endbunji}" />
							</c:if>
							<c:if test="${empty vo.endbunji }">
								<c:set var="bunji" 
								value="${vo.startbunji }" />
							</c:if>
						
							<tr>
								<td><a href="#" 
						onclick="setZipcode('${vo.zipcode}',
						'${address}')">
								${vo.zipcode}</a></td>
								<td>${address} ${bunji}</td>
							</tr>
						</c:forEach>
					</c:if>				
				</tbody>
			</table>
		</div>
	</c:if>
		
</body>
</html>










