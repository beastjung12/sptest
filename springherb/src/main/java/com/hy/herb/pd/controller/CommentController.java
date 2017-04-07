package com.hy.herb.pd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hy.herb.pd.model.CommentVO;
import com.hy.herb.pd.model.PdService;

@Controller
public class CommentController {
	@Autowired
	private PdService pdService;
	
	//setter-종속객체 주입	
	/*public void setPdService(PdService pdService) {
		this.pdService = pdService;
	}*/

	@RequestMapping("/pd/commentList.do")
	public ModelAndView list(){
		//comments2 테이블 전체 조회
		//1.
		System.out.println("list() 호출");
		
		//2.
		List<CommentVO> alist = pdService.selectCmt();
		System.out.println("comment 조회 결과 alist.size()="
				+alist.size());
		
		//3.
		ModelAndView mav= new ModelAndView();
		mav.addObject("cmtList", alist);
		mav.setViewName("pd/commentList");
		
		return mav;
	}
	
}











