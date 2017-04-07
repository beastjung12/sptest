<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//로그아웃 처리
	//=> 세션 정보 제거
	session.invalidate();
	
	request.setAttribute("msg", "로그아웃되었습니다.");
	request.setAttribute("url", "../index.jsp");	
%>
<jsp:forward page="../common/message.jsp"></jsp:forward>

