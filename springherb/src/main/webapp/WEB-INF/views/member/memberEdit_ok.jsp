<%@page import="java.sql.SQLException"%>
<%@page import="com.herbmall.member.model.MemberService"%>
<%@page import="com.herbmall.member.model.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 로그인되었는지 체크 -->
<%@ include file="../login/loginCheck.jsp" %>
    
<%
	//memberEdit.jsp 에서 [수정]버튼을 클릭하면 post방식으로 submit
	//=> 파라미터 읽어서 member 테이블에 update
	//단 비밀번호가 일치할때만 수정처리
	
	//1.
	request.setCharacterEncoding("utf-8");
	
	String userid = (String)session.getAttribute("userid");
	
	String pwd = request.getParameter("pwd");
	String zipcode = request.getParameter("zipcode");
	String address = request.getParameter("address");
	String addressDetail = request.getParameter("addressDetail");
	String hp1 = request.getParameter("hp1");
	String hp2 = request.getParameter("hp2");
	String hp3 = request.getParameter("hp3");
	String email1 = request.getParameter("email1");
	String email2 = request.getParameter("email2");
	String email3 = request.getParameter("email3");
	
	//2.
	//휴대폰 처리
	String hp="";
	if(hp2!=null && !hp2.isEmpty() && hp3!=null && !hp3.isEmpty()){
		hp=hp1+"-"+hp2+"-"+hp3;
	}
	
	//이메일 처리
	String email="";
	if(email1!=null && !email1.isEmpty()){
		if(!email2.equals("etc")){
			email=email1+"@"+email2;
		}else{
			//직접입력인 경우 - email3와 연결
			if(email3!=null && !email3.isEmpty()){
				email=email1+"@"+email3;
			}
		}
	}
	
	MemberVO vo = new MemberVO();
	vo.setAddress(address);
	vo.setAddressDetail(addressDetail);
	vo.setEmail(email);
	vo.setHp(hp);
	vo.setPwd(pwd);
	vo.setUserid(userid);
	vo.setZipcode(zipcode);
	
	MemberService service = new MemberService();
	try{
		//비밀번호 체크
		int result = service.loginCheck(userid, pwd);
		String msg="", url="memberEdit.jsp";
		if(result==MemberService.LOGIN_OK){
			//수정처리
			int cnt = service.updateMember(vo);
			
			//3.
			if(cnt>0){
				msg="회원정보 수정되었습니다";
			}else{
				msg="회원정보 수정 실패";				
			}
		}else if(result==MemberService.PWD_DISAGREE){
			msg="비밀번호가 일치하지 않습니다";	
		}else{
			msg="비밀번호 체크 실패";
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("url", url); %>
		
		<jsp:forward page="../common/message.jsp"></jsp:forward>
			
<%	}catch(SQLException e){
		e.printStackTrace();
	}
	
%>





