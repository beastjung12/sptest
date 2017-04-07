package com.hy.herb.reboard.model;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import com.hy.herb.common.SearchVO;

@Repository
public class ReBoardDAOMybatis extends SqlSessionDaoSupport 
	implements ReBoardDAO{
	
	private String namespace="config.mybatis.mapper.oracle.reboard";
	
	public int insertReBoard(ReBoardVO vo){
		int cnt 
			= getSqlSession().insert(namespace+".reBoardInsert", vo);
		return cnt;
	}
		
	public List<ReBoardVO> selectAll(SearchVO searchVo){
		//글 전체 조회/검색
		List<ReBoardVO> alist
		=getSqlSession().selectList(namespace+".selectReBoard", searchVo);
		
		return alist;
	}
	
	public int updateReadCount(int no){
		int cnt
		=getSqlSession().update(namespace+".updateReadCount", no);
		
		return cnt;
	}

	public ReBoardVO selectByNo(int no){
		ReBoardVO vo
		=getSqlSession().selectOne(namespace+".selectByNo", no);
		return vo;
	}
	
	
	public int updateReBoard(ReBoardVO vo){
		//게시판 글 수정
		
		return getSqlSession().update(namespace+".reBoardUpdate", vo);		
	}
	
	public String selectPwd(int no){
		//비밀번호가 일치하는지  체크
		String pwd 
			= getSqlSession().selectOne(namespace+".selectPwd", no);
		return pwd;
	}
	
	public int deleteReBoard(Map<String, String> map){
		//글 삭제		
		return getSqlSession().delete(namespace+".reBoardDelete", map);
	}

	@Override
	public int selectTotalRecord(SearchVO searchVo) {
		return getSqlSession().selectOne(namespace
				+".selectTotalRecord", searchVo);
	}

	@Override
	public int updateDownCount(int no) {
		return getSqlSession().update(namespace+".updateDownCount", 
				no);
	}

	@Override
	public int updateSortNo(ReBoardVO vo) {
		return getSqlSession().update(namespace+".updateSortNo",vo);
	}

	@Override
	public int reply(ReBoardVO vo) {
		return getSqlSession().insert(namespace+".replyReboard",vo);
	}
	
	
	/*
	public List<ReBoardVO> selectMainNotice() throws SQLException{
		//최근 공지사항 6건 조회
		select *
		from(
		select no,title from reBoard
		order by no desc
		)
		where rownum<=6
		
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		List<ReBoardVO> alist=new ArrayList<ReBoardVO>();
		try{
			conn=pool.getConnection();
			
			String sql="select *"
				+" from("
				+" select no,title from reBoard"
				+" order by no desc"
				+" )"
				+" where rownum<=6";
			ps=conn.prepareStatement(sql);
			
			rs = ps.executeQuery();
			while(rs.next()){
				ReBoardVO vo = new ReBoardVO();
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
