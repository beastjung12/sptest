<%@page import="java.util.List"%>
<%@page import="java.sql.SQLException"%>
<%@page import="com.herbmall.board.model.BoardDAO"%>
<%@page import="com.herbmall.board.model.BoardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	BoardDAO dao = new BoardDAO();
	List<BoardVO> alist=null;
	try{
		alist=dao.selectMainNotice();
	}catch(SQLException e){
		e.printStackTrace();
	}
%>

<style type="text/css">
	.divNotice{
		width:310px;
	}
	
	.divNotice div table{
		width:300px;
	}
	
	.divNotice div span{
		padding:0 0 0 160px;
	}
	.divNotice div #img1{
		width:310px;
		height:6px
	}
</style>

<div class="divNotice">
	<div>
		<img src="<%=request.getContextPath() %>/images/notice2.JPG" 
			alt="공지사항 이미지">
		<span>
			<a href="<%=request.getContextPath() %>/board/list.jsp">
				<img src="<%=request.getContextPath() %>/images/more.JPG"
					alt="more 이미지">
			</a>
		</span>
	</div>
	<div>
		<img id="img1" src="<%=request.getContextPath() %>/images/Line.JPG" >
	</div>
	<div>
		<table summary="최근 공지사항 6건을 보여주는 표입니다.">
			<tbody>
				<%if(alist==null || alist.isEmpty()){ %>
					<tr>
						<td class="align_center">
							최근 공지사항이 존재하지 않습니다.
						</td>
					</tr>
				<%}else{ %>
					<!-- 반복 시작 -->
					<%for(BoardVO vo : alist){ %>
					<tr>
						<td>
						<img 
						src="<%=request.getContextPath() %>/images/dot5.JPG">
						<a href
		="<%=request.getContextPath()%>/board/detail.jsp?no=<%=vo.getNo()%>">
						<%=vo.getTitle() %></a></td>				
					</tr>
					<!-- 반복 끝 -->
					<%}//for %>
				<%}//if %>
			</tbody>
		</table>
	</div>
</div>




