package com.hy.herb.pd.model;

import java.util.List;

public interface PdService {	
	public int insertPd(PdDTO dto);	
	public List<PdDTO> selectPdAll();
	public PdDTO selectByNo(int no);	
	public int updatePd(PdDTO dto);	
	public int deletePd(int no);
		
	public List<CommentVO> selectCmt();
	
}
