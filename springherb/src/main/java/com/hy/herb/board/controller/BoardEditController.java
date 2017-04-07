package com.hy.herb.board.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.hy.herb.board.model.BoardService;
import com.hy.herb.board.model.BoardVO;

@Controller
@RequestMapping("/board/edit.do")
public class BoardEditController {
	private static final Logger logger
	=LoggerFactory.getLogger(BoardDetailController.class);
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String edit_get(
		@RequestParam(value="no", defaultValue="0") int no, Model model){
		//1
		logger.info("글수정 화면 보여주기, 파라미터 no={}", no);
		if(no==0){
			model.addAttribute("msg", "잘못된 url입니다");
			model.addAttribute("url", "/board/list.do");
			
			return "common/message";
		}
		
		//2
		BoardVO vo =boardService.selectByNo(no);
		logger.info("글수정화면-조회 결과 vo={}", vo);
		
		//3
		model.addAttribute("vo", vo);
		
		return "board/edit";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public	String edit_post(@ModelAttribute BoardVO vo, Model model){
		//1.
		logger.info("글 수정 처리, 파라미터 vo={}", vo);
		
		//2.
		String msg="", url="/board/edit.do?no="+vo.getNo();
		//비밀번호 체크 후
		if(boardService.checkPwd(vo.getNo(), vo.getPwd())){		
			//비밀번호가 일치하면 update 처리
			int cnt = boardService.updateBoard(vo);
			logger.info("글 수정 결과 cnt={}", cnt);
			
			if(cnt>0){
				msg="글 수정 성공";
				url="/board/detail.do?no="+vo.getNo();
			}else{
				msg="글 수정 실패";
			}
		}else{
			msg="비밀번호가 일치하지 않습니다";			
		}
		
		//3.
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
}









