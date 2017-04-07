package com.hy.herb.pd.model;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

//자동으로 빈 등록
@Repository
public class OraclePdDAO extends SqlSessionDaoSupport implements PdDAO{
	
	
	private String namespace = "com.mybatis.mapper.pd";

	public int insertPd(PdDTO dto){		
		System.out.println("dao 메서드 => 실행 전 dto="+dto);
		
		int cnt=getSqlSession().insert(namespace+".insertPd", dto);
								// (id,   parameter)
			
		System.out.println("insert 실행 후 dto="+ dto);		
		
		return cnt;
	}
	
	public List<PdDTO> selectPdAll(){
		//상품 전체 조회
		List<PdDTO> alist= getSqlSession().selectList(namespace+".pdSelectAll");
				
		return alist;
	}
	
	
	public PdDTO selectByNo(int no){
		//no에 해당하는 상품 조회
		PdDTO dto=getSqlSession().selectOne(namespace+".pdSelectByNo", no);
		
		return dto;
	}
	
	public int updatePd(PdDTO dto){
		//상품 수정
		int cnt=getSqlSession().update(namespace+".pdUpdate", dto);
		
		return cnt;
	}
	
	
	public int deletePd(int no){
		//상품 삭제
		int cnt=getSqlSession().delete(namespace+".pdDelete", no);
			
		return cnt;
	}
	
	public List<CommentVO> selectCmt(){
		List<CommentVO> alist=getSqlSession().selectList(namespace+".cmtSelect");
		
		return alist;
	}
	
	
}










