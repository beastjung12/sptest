<%@page import="com.herbmall.member.model.MemberVO"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.herbmall.member.model.MemberService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../inc/top.jsp" %>

<!-- 로그인되었는지 체크 -->
<%@ include file="../login/loginCheck.jsp" %>

<%
	//1.
 	String userid = (String)session.getAttribute("userid");
		
	//2.
	MemberService service = new MemberService();
	MemberVO vo=null;
	try{
		vo = service.selectByUserid(userid);	
	}catch(SQLException e){
		e.printStackTrace();
	}
	
	//3.
	String zipcode=vo.getZipcode();
	String address=vo.getAddress();
	String addressDetail=vo.getAddressDetail();
	
	if(zipcode==null) zipcode="";
	if(address==null) address="";
	if(addressDetail==null) addressDetail="";
	
	//휴대폰 처리
	String hp=vo.getHp();
	String hp1="", hp2="", hp3="";
	//010-100-3000
	if(hp!=null && !hp.isEmpty()){
		String[] hpArr = hp.split("-");  
		hp1=hpArr[0];  //010
		hp2=hpArr[1];  //100
		hp3=hpArr[2];  //3000		
	}
	
	//이메일 처리
	//이메일 목록
	String[] emailList={"naver.com","hanmail.net","nate.com","gmail.com"}; 
	boolean isEtc=false;  //직접입력이면 true
	int count=0;
	
	String email=vo.getEmail();
	String email1="", email2="";
	//h@nate.com
	//k@herbmall.com
	if(email!=null && !email.isEmpty()){
		String[] emailArr = email.split("@");
		email1=emailArr[0];  //h			k
		email2=emailArr[1];	 //nate.com		herbmall.com
		
		//이메일 - 직접입력인 경우 처리
		for(int i=0;i<emailList.length;i++){
			if(email2.equals(emailList[i])){
				count++;
				break;
			}
		}//for
		
		if(count==0){  //직접입력
			isEtc=true;
		}//if
	}
	
	
%>
<script type="text/javascript">
	function send(form){
		if(!form.pwd.value){
			alert('비밀번호를 입력하세요');
			form.pwd.focus();
			return false;
		}else if(form.pwd.value!=form.pwd2.value){
			alert('비밀번호가 일치하지 않습니다');
			form.pwd2.focus();
			return false;
		}
		
		return true;
	}
	
	function emailCheck(){
		if(document.frm1.email2.value=='etc'){
			//직접입력인 경우 - 텍스트박스 보여주기
			frm1.email3.style.visibility='visible';
			frm1.email3.value='';
			frm1.email3.focus();
		}else{
			frm1.email3.style.visibility='hidden';
		}
	}
	
	function getZipcode(){		
		window.open('../zipcode/zipcode.jsp','zip',
			'width=450,height=500,left=10,top=10,location=yes,resizable=yes');	
	}
		
	
</script>

<style type="text/css">
	.width_80{
		width:80px;
	}
	.width_350{
		width:350px;
	}	
</style>
<article>
<div class="divForm">
<form name="frm1" method="post" action="memberEdit_ok.jsp" onsubmit="return send(this)">
<fieldset>
	<legend>회원 정보 수정</legend>
    <div>        
        <label for="name">성명</label>
        <span><%=vo.getName() %></span>
    </div>
    <div>
        <label for="userid">회원ID</label>
        <span><%=userid %></span>
    </div>
    <div>
        <label for="pwd">비밀번호</label>
        <input type="Password" name="pwd" id="pwd">
    </div>
    <div>
        <label for="pwd2">비밀번호 확인</label>
        <input type="Password" name="pwd2" id="pwd2">
    </div>
    <div>
        <label for="zipcode">주소</label>
        <input type="text" name="zipcode" id="zipcode" ReadOnly  
        	title="우편번호" class="width_80" value="<%=zipcode%>">
        <input type="Button" value="우편번호 찾기" id="btnZipcode" 
        	title="새창열림" onclick='getZipcode()'><br />
        <span class="sp1">&nbsp;</span>
        <input type="text" name="address" ReadOnly title="주소"  
        	class="width_350"  value="<%=address%>"><br />
        <span class="sp1">&nbsp;</span>
        <input type="text" name="addressDetail" title="상세주소"  
        	class="width_350" value="<%=addressDetail%>">
    </div>
    <div>
        <label for="hp1">핸드폰</label>&nbsp;
        <select name="hp1" id="hp1" title="휴대폰 앞자리">
            <option value="010"
            	<%if(hp1.equals("010")){ %>
            		selected="selected"
            	<%} %>
            >010</option>
            <option value="011"
            	<%if(hp1.equals("011")){ %>
            		selected="selected"
            	<%} %>
            >011</option>
            <option value="016"
            	<%if(hp1.equals("016")){ %>
            		selected="selected"
            	<%} %>
            >016</option>
            <option value="017"
            	<%if(hp1.equals("017")){ %>
            		selected="selected"
            	<%} %>
            >017</option>
            <option value="018"
            	<%if(hp1.equals("018")){ %>
            		selected="selected"
            	<%} %>
            >018</option>
            <option value="019"
            	<%if(hp1.equals("019")){ %>
            		selected="selected"
            	<%} %>
            >019</option>
       	</select>
        -
        <input type="text" name="hp2" id="hp2" maxlength="4" title="휴대폰 가운데자리"
        	class="width_80" value="<%=hp2%>">-
        <input type="text" name="hp3" id="hp3" maxlength="4" title="휴대폰 뒷자리"
        	class="width_80" value="<%=hp3%>">
    </div>
    <div>
        <label for="email1">이메일 주소</label>
        <input type="text" name="email1"  id="email1" title="이메일주소 앞자리"
         value="<%=email1%>">@
        <select name="email2" id="email2"  title="이메일주소 뒷자리"
        	onchange='emailCheck()'>
        	<%for(int i=0;i<emailList.length;i++){ %>
	        	<option value="<%=emailList[i] %>"
	            	<%if(email2.equals(emailList[i])){ %>
	            		selected="selected"
	            	<%} %>
	            ><%=emailList[i] %></option>
            <%}//for %>
            
            <%-- <option value="naver.com"
            	<%if(email2.equals("naver.com")){ %>
            		selected="selected"
            	<%} %>
            >naver.com</option>
            <option value="hanmail.net"
            	<%if(email2.equals("hanmail.net")){ %>
            		selected="selected"
            	<%} %>
            >hanmail.net</option>
            <option value="nate.com"
            	<%if(email2.equals("nate.com")){ %>
            		selected="selected"
            	<%} %>
            >nate.com</option>
            <option value="gmail.com"
            	<%if(email2.equals("gmail.com")){ %>
            		selected="selected"
            	<%} %>
            >gmail.com</option> --%>
            
            <option value="etc"
            	<%if(isEtc){ %>
            		selected="selected"
            	<%} %>
            >직접입력</option>
        </select>
        <input type="text" name="email3" id="email3" title="직접입력인 경우 이메일주소 뒷자리"
        	<%if(isEtc){ //직접입력 %>
        		style="visibility:visible;"
        		value="<%=email2 %>"
        	<%}else{ %>
        		style="visibility:hidden"
        	<%} %>
        	>
    </div>
    <div class="center">
         <input type="submit" id="wr_submit" value="수정">
    </div>
</fieldset>

        
</form>
</div>
</article>

<%@ include file="../inc/bottom.jsp"%>











