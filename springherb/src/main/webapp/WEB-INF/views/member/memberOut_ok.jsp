<%@page import="java.sql.SQLException"%>
<%@page import="com.herbmall.member.model.MemberService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 로그인되었는지 체크 -->
<%@ include file="../login/loginCheck.jsp" %>

<%
	//memberOut.jsp에서 [회원탈퇴]버튼을 클릭하면 post방식으로 submit
	//=> [1] 아이디에 해당하는 회원 정보 탈퇴 처리-outdate update
	//단 비밀번호가 일치해야 탈퇴처리
	//=> [2] 세션 정보 삭제, 쿠키 삭제
	
	//1.
	request.setCharacterEncoding("utf-8");
	
	String userid = (String)session.getAttribute("userid");
	String pwd=request.getParameter("pwd");
	
	//2.
	MemberService service = new MemberService();
	try{
		//비밀번호 체크
		int result = service.loginCheck(userid, pwd);
		
		String msg="", url="memberOut.jsp";
		if(result==MemberService.LOGIN_OK){  //비밀번호 일치하는 경우
			//회원탈퇴 처리
		 	int cnt = service.withdrawMember(userid);
			if(cnt>0){
				msg="회원탈퇴 처리되었습니다";
				url="../index.jsp";
				
				//세션 제거
				session.invalidate();
				
				//쿠키 삭제
				Cookie ck = new Cookie("ck_userid", userid);
				ck.setPath("/");
				ck.setMaxAge(0);
				response.addCookie(ck);
			}else{
				msg="회원탈퇴 실패";
			}
		}else if(result==MemberService.PWD_DISAGREE){ //비밀번호 불일치
			msg="비밀번호가 일치하지 않습니다";
		}else{
			msg="비밀번호 체크 실패";
		}
		
		//3.
		request.setAttribute("msg", msg);
		request.setAttribute("url", url); %>
		
		<jsp:forward page="../common/message.jsp"></jsp:forward>	
<%	}catch(SQLException e){
		e.printStackTrace();		
	}
	
	
%>








