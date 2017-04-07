package com.hy.herb.board.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hy.herb.board.model.BoardService;
import com.hy.herb.board.model.BoardVO;
import com.hy.herb.common.PaginationInfo;
import com.hy.herb.common.SearchVO;
import com.hy.herb.common.Utility;

@Controller
public class BoardListController {
	private static final Logger logger
	=LoggerFactory.getLogger(BoardListController.class);
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/board/list.do")
	public String list(@ModelAttribute SearchVO searchVo, Model model){
		//1.
		logger.info("글목록, 파라미터 searchVo={}", searchVo);
		
		//2
		//[1] PaginationInfo 객체 생성 
		//=> firstRecordIndex 를 계산하기 위함
		PaginationInfo pagingInfo = new PaginationInfo();
		pagingInfo.setBlockSize(Utility.BLOCKSIZE);
		pagingInfo.setRecordCountPerPage(Utility.RECORDCOUNT_PERPAGE);
		pagingInfo.setCurrentPage(searchVo.getCurrentPage());
		
		//[2] SearchVO 값 셋팅
		searchVo.setRecordCountPerPage(Utility.RECORDCOUNT_PERPAGE);
		searchVo.setFirstRecordIndex(pagingInfo.getFirstRecordIndex());
		
		
		List<BoardVO> alist =boardService.selectAll(searchVo);
		logger.info("글목록 조회결과, alist.size()={}", alist.size());
		
		int totalRecord =boardService.selectTotalRecord(searchVo);
		logger.info("글목록 조회-전체레코드 개수조회 결과, totalRecord={}",
			totalRecord);
		
		pagingInfo.setTotalRecord(totalRecord);
		
		//3
		//모델에 결과 저장
		model.addAttribute("alist", alist);
		model.addAttribute("pagingInfo", pagingInfo);
		
		//뷰페이지 리턴
		return "board/list";
	}
	
}









