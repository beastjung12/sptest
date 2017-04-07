<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//로그인 후 사용가능한 페이지에서 로그인이 되었는지 체크
	
 	String l_userid = (String)session.getAttribute("userid");
	//로그인이 안된 경우 에러 처리
	if(l_userid==null || l_userid.isEmpty()){%>
		<script type="text/javascript">
			alert("먼저 로그인하세요");
			location.href='<%=request.getContextPath()%>/index.jsp';
			//=> /herbmall/index.jsp
		</script>
			
<%		return;
	}	
%>








