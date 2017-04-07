package com.hy.herb.board.model;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.hy.herb.common.SearchVO;

@Repository
public class BoardDAOMybatis extends SqlSessionDaoSupport 
	implements BoardDAO{
	
	private String namespace="config.mybatis.mapper.oracle.board";
	
	public int insertBoard(BoardVO vo){
		int cnt 
			= getSqlSession().insert(namespace+".boardInsert", vo);
		return cnt;
	}
		
	public List<BoardVO> selectAll(SearchVO searchVo){
		//글 전체 조회/검색
		List<BoardVO> alist
		=getSqlSession().selectList(namespace+".selectBoard", searchVo);
		
		return alist;
	}
	
	public int updateReadCount(int no){
		int cnt
		=getSqlSession().update(namespace+".updateReadCount", no);
		
		return cnt;
	}

	public BoardVO selectByNo(int no){
		BoardVO vo
		=getSqlSession().selectOne(namespace+".selectByNo", no);
		return vo;
	}
	
	
	public int updateBoard(BoardVO vo){
		//게시판 글 수정
		
		return getSqlSession().update(namespace+".boardUpdate", vo);		
	}
	
	public String selectPwd(int no){
		//비밀번호가 일치하는지  체크
		String pwd 
			= getSqlSession().selectOne(namespace+".selectPwd", no);
		return pwd;
	}
	
	public int deleteBoard(int no){
		//글 삭제
		
		return getSqlSession().delete(namespace+".boardDelete", no);
	}

	@Override
	public int selectTotalRecord(SearchVO searchVo) {
		return getSqlSession().selectOne(namespace
				+".selectTotalRecord", searchVo);
	}
	
	
	/*
	public List<BoardVO> selectMainNotice() throws SQLException{
		//최근 공지사항 6건 조회
		select *
		from(
		select no,title from board
		order by no desc
		)
		where rownum<=6
		
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<BoardVO> alist=new ArrayList<BoardVO>();
		try{
			conn=pool.getConnection();
			
			String sql="select *"
				+" from("
				+" select no,title from board"
				+" order by no desc"
				+" )"
				+" where rownum<=6";
			ps=conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()){
				BoardVO vo = new BoardVO();
				vo.setNo(rs.getInt("no"));
				vo.setTitle(rs.getString("title"));
				
				alist.add(vo);
			}
			System.out.println("최근 공지사항 조회 결과 alist.size()="+alist.size());
			
		}finally{
			pool.dbClose(rs, ps, conn);			
		}
		return alist;
	}*/
}
