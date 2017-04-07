<%@page import="com.herbmall.member.model.MemberVO"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="memDao" class="com.herbmall.member.model.MemberDAO" 
	scope="session"></jsp:useBean>    
<%
	//register.jsp에서 [회원가입]버튼을 클릭하면 post방식으로 submit
	//=> 요청 파라미터 읽어서 member테이블에 insert
	
	//1.
	request.setCharacterEncoding("utf-8");
	String name = request.getParameter("name");
	String userid = request.getParameter("userid");
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
	vo.setName(name);
	vo.setPwd(pwd);
	vo.setUserid(userid);
	vo.setZipcode(zipcode);
	
	try{
		int cnt = memDao.insertMember(vo);
		
		//3.
		String msg="", url="";
		if(cnt>0){
			msg="회원가입되었습니다";
			url="../index.jsp";
		}else{
			msg="회원가입 실패";
			url="register.jsp";
		}
		
		request.setAttribute("msg", msg);
		request.setAttribute("url", url); %>
		
		<jsp:forward page="../common/message.jsp"></jsp:forward>
			
<%	}catch(SQLException e){
		e.printStackTrace();
	}
	
%>








