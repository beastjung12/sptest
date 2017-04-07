package com.hy.herb.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hy.herb.board.model.BoardService;
import com.hy.herb.board.model.BoardVO;

@Controller
public class BoardDetailController {
	private static final Logger logger
	=LoggerFactory.getLogger(BoardDetailController.class);
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/board/countUpdate.do")
	public String countUpdate(
			@RequestParam(value="no", defaultValue="0") int no,
			Model model){
		//1.
		logger.info("조회수 증가 처리, 파라미터 no={}", no);
		if(no==0){
			model.addAttribute("msg", "잘못된 url입니다");
			model.addAttribute("url", "/board/list.do");
			
			return "common/message";
		}
		
		//2.
		int cnt = boardService.updateReadCount(no);
		logger.info("조회수 증가 결과, cnt={}", cnt);
		
		//3.
		return "redirect:/board/detail.do?no="+no;
	}
	
	@RequestMapping("/board/detail.do")
	public String detail(
		@RequestParam(value="no", defaultValue="0") int no, Model model){
		//1.
		logger.info("글 상세보기, 파라미터 no={}", no);
		if(no==0){
			model.addAttribute("msg", "잘못된 url입니다");
			model.addAttribute("url", "/board/list.do");
			
			return "common/message";
		}
		
		//2.
		BoardVO vo = boardService.selectByNo(no);
		logger.info("상세보기 결과, vo={}", vo);
		
		//3.
		model.addAttribute("boardVo", vo);
		
		return "board/detail";
		
	}
	
}








