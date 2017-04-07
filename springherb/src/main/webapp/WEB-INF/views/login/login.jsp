<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../inc/top.jsp" %>
<%
	//1.
	String ckVal="";
	Cookie[] ckArr = request.getCookies();
	if(ckArr!=null){
		for(int i=0;i<ckArr.length;i++){
			String ckName = ckArr[i].getName();
			if(ckName.equals("ck_userid")){
				ckVal=ckArr[i].getValue();
				break;
			}
		}//for
	}
%>

<script type="text/javascript">
	function send(form){
		if(form.userid.value==''){
			alert('아이디를 입력하세요');
			form.userid.focus();
			return false;
		}else if(form.pwd.value==''){
			alert('비밀번호를 입력하세요');
			form.pwd.focus();
			return false;
		}
		
		return true;
	}
</script>

<article class="simpleForm">
	<form name="frmLogin" method="post" action="login_ok.jsp"
		onsubmit="return send(this)">
		<fieldset>
			<legend>로그인</legend>
			<div>
				<label for="userid" class="label">아이디</label>
				<input type="text" name="userid" id="userid" value="<%=ckVal%>">
			</div>
			<div>
				<label for="pwd" class="label">비밀번호</label>
				<input type="password" name="pwd" id="pwd">
			</div>
			<div class="align_center">
				<input type="submit" value="로그인">
				<input type="checkbox" name="chkSaveId" id="chkId" 
					<%if(ckVal!=null && !ckVal.isEmpty()){ %>
						checked="checked"
					<%} %>
				>
				<label for="chkId">아이디 저장하기</label>
			</div>
			
		</fieldset>
	</form>
	
</article>


<%@ include file="../inc/bottom.jsp" %>















