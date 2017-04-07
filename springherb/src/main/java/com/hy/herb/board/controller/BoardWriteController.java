package com.hy.herb.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hy.herb.board.model.BoardService;
import com.hy.herb.board.model.BoardVO;

@Controller
public class BoardWriteController {
	private static final Logger logger 
		= LoggerFactory.getLogger(BoardWriteController.class);
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value="/board/write.do", method=RequestMethod.GET)
	public String write_get(){
		logger.info("글쓰기 화면 보여주기");
		
		return "board/write";
	}

	@RequestMapping(value="/board/write.do", method=RequestMethod.POST)
	public String write_post(@ModelAttribute BoardVO boardVo){
		//1
		logger.info("글쓰기 처리, 파라미터 boardVo={}", boardVo);
		
		//2
		int cnt = boardService.insertBoard(boardVo);
		logger.info("글쓰기 결과, cnt={}", cnt);
		
		//3
		return "redirect:/board/list.do";
	}
	
	
}










