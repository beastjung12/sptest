package com.hy.herb.pd.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PdServiceImpl implements PdService{
	@Autowired
	private PdDAO pdDao;
	
	public PdServiceImpl() {
		//pdDao=new OraclePdDAO();
		System.out.println("생성자 : PdServiceImpl");
	}
	
	//setter에 의한 종속객체 주입
	/*public void setPdDao(PdDAO pdDao) {
		this.pdDao = pdDao;
		System.out.println("setter 호출: PdServiceImpl-setPdDao()");
	}*/

	//어노테이션에 의한 트랜잭션 처리
	@Transactional
	public int insertPd(PdDTO dto){
		return pdDao.insertPd(dto);
	}
	
	public List<PdDTO> selectPdAll(){
		return pdDao.selectPdAll();				
	}
	
	public PdDTO selectByNo(int no){
		return pdDao.selectByNo(no);
	}
	
	public int updatePd(PdDTO dto){
		return pdDao.updatePd(dto);
	}
	
	public int deletePd(int no){
		return pdDao.deletePd(no);
	}

	
	@Override
	public List<CommentVO> selectCmt() {		
		return pdDao.selectCmt();
	}
	
	
}








