<%@page import="java.sql.SQLException"%>
<%@page import="com.herbmall.member.model.MemberService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>checkUserid.jsp</title>
<link href="../css/mainstyle.css" rel="stylesheet" type="text/css">
<link href="../css/mystyle.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function setUserid(userid){
		opener.document.frm1.userid.value=userid;
		opener.document.frm1.chkId.value='Y';
		
		self.close();
	}
</script>
</head>
<body>
<%
	//register.jsp에서 [중복확인]버튼을 클릭하면 새창 열림-get
	//=> 아이디 중복검사 - 아이디를 조회해서 이미 존재하는지 여부 확인
	//=> http://localhost:9090/herbmall/member/checkUserid.jsp?userid=hong
	//1.
	String userid = request.getParameter("userid");
	
	//2.
	int result=0;
	if(userid!=null && !userid.isEmpty()){
		MemberService service = new MemberService();
		
		try{
			result = service.duplicateUserid(userid);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	//3.
	

%>
	<h2>아이디 중복검사</h2>
	<div>
		<form name="frmId" method="post" action="checkUserid.jsp">
			<label for="userid">아이디</label>
			<input type="text" name="userid" id="userid" value="<%=userid%>">
			<input type="submit" value="아이디 확인">
			<%if(result==MemberService.EXIST_ID){ //이미 아이디 존재%>
				<p>이미 등록된 아이디입니다. 다른 아이디를 입력하세요</p>
			<%}else if(result==MemberService.NONE_EXIST_ID){ //아이디 존재하지 않음%>
				<input type="button" value="사용하기" 
					onclick="setUserid('<%=userid%>')">
				<p>사용가능한 아이디입니다. [사용하기]버튼을 클릭하세요</p>
			<%} %>
		</form>
	</div>

</body>
</html>













