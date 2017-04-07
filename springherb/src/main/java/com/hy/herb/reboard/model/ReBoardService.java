package com.hy.herb.reboard.model;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hy.herb.common.SearchVO;

public interface ReBoardService {
	public int insertReBoard(ReBoardVO vo);
	public List<ReBoardVO> selectAll(SearchVO searchVo);	
	public int updateReadCount(int no);	
	public ReBoardVO selectByNo(int no);	
	public int updateReBoard(ReBoardVO vo);
	public boolean checkPwd(int no, String pwd);	
	public int deleteReBoard(Map<String, String> map);	
	public int selectTotalRecord(SearchVO searchVo);
	public int updateDownCount(int no);
	public int reply(ReBoardVO vo);
	
	
	public List<Map<String, Object>> fileUpload(HttpServletRequest request);
	public String getUploadPath(HttpServletRequest request);
	
	
	
	/*
	public List<ReBoardVO> selectMainNotice();*/
	

}
