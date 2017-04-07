<%@page import="com.herbmall.member.model.MemberVO"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.herbmall.member.model.MemberService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//login.jsp에서 [로그인]버튼을 클릭하면 post방식으로 submit됨
	//=> 아이디, 비밀번호에 해당하는 사용자가 있는지 확인(로그인 처리)
	//=> 로그인 성공한 경우 [1] 세션에 로그인 정보 저장 [2] 쿠키에 아이디 저장
	
	//1.
	request.setCharacterEncoding("utf-8");
	String userid = request.getParameter("userid");
	String pwd = request.getParameter("pwd");
	String chkSaveId = request.getParameter("chkSaveId");
	
	//2.
	MemberService service = new MemberService();
	try{
		int result = service.loginCheck(userid, pwd);
		
		//3.
		String msg="", url="login.jsp";
		if(result==MemberService.LOGIN_OK){  //로그인 성공
			MemberVO vo = service.selectByUserid(userid);
			msg=vo.getName() + "님, 로그인되었습니다";
			url="../index.jsp";
			
			//[1] session에 저장
			session.setAttribute("userid", userid);
			session.setAttribute("userName", vo.getName());
			session.setAttribute("authCode", vo.getAuthCode());
						
			//[2] cookie에 저장
			//checkbox를 체크하지 않으면 null, 체크하면 on 이 넘어옴
			
			Cookie ck = new Cookie("ck_userid", userid);
			if(chkSaveId!=null){  //아이디 저장하기를 체크한 경우
				ck.setPath("/"); //쿠키 경로 지정
				ck.setMaxAge(1000*24*60*60);  //쿠키 유효기간 - 1000일
				response.addCookie(ck);
			}else{
				ck.setPath("/"); //쿠키 경로 지정
				ck.setMaxAge(0);  //쿠키 유효기간 : 0 => 쿠키 삭제됨
				response.addCookie(ck);
			}
		}else if(result==MemberService.PWD_DISAGREE){
			msg="비밀번호가 일치하지 않습니다.";			
		}else if(result==MemberService.ID_NONE){
			msg="해당 아이디가 존재하지 않습니다.";			
		}else{
			msg="로그인 처리 실패";			
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("url", url); %>
		<jsp:forward page="../common/message.jsp"></jsp:forward>
			
<%	}catch(SQLException e){
		e.printStackTrace();
	}
	
	

%>









