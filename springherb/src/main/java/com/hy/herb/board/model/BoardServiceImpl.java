package com.hy.herb.board.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hy.herb.common.SearchVO;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardDAO boardDao;
			
	public int insertBoard(BoardVO vo){
		return boardDao.insertBoard(vo);
	}
	
	public List<BoardVO> selectAll(SearchVO searchVo){
		return boardDao.selectAll(searchVo);
	}
	
	public int updateReadCount(int no){
		return boardDao.updateReadCount(no);
	}
	
	public BoardVO selectByNo(int no){
		return boardDao.selectByNo(no);
	}
	
	public int updateBoard(BoardVO vo){
		return boardDao.updateBoard(vo);
	}
	
	public boolean checkPwd(int no, String pwd){
		String dbPwd =boardDao.selectPwd(no);
		
		if(pwd.equals(dbPwd)){
			return true;
		}else{
			return false;
		}
	}
	
	public int deleteBoard(int no){
		return boardDao.deleteBoard(no);
	}

	@Override
	public int selectTotalRecord(SearchVO searchVo) {
		return boardDao.selectTotalRecord(searchVo);
	}
	
	/*
	public List<BoardVO> selectMainNotice() throws SQLException{
		return boardDao.selectMainNotice();
	}*/
}
