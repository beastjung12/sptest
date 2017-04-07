<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../inc/top.jsp" %>
<!-- 로그인되었는지 체크 -->
<%@ include file="../login/loginCheck.jsp" %>


<style type="text/css">
	label{
		padding-right: 20px;
	}
</style>
<script type="text/javascript">
	window.onload=function(){
		document.frmOut.pwd.focus();
	}
	
	function send(form){
		if(form.pwd.value==''){
			alert('비밀번호를 입력하세요');
			form.pwd.focus();
			return false;
		}
		return true;
	}
</script>
<article class="simpleForm">
	<form name="frmOut" method="post" action="memberOut_ok.jsp"
		onsubmit="return send(this)">
		<fieldset>
			<legend>회원탈퇴</legend>
			<p class="p">회원탈퇴하시겠습니까</p>
			<div>
				<label for="pwd">비밀번호</label>
				<input type="password" name="pwd" id="pwd">
			</div>
			<div class="align_center">
				<input type="submit" value="회원탈퇴">
				<input type="reset" value="취소">
			</div>
		</fieldset>
	</form>
</article>

<%@ include file="../inc/bottom.jsp" %>



