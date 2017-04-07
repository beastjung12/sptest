package com.hy.herb.pd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hy.herb.pd.model.PdDTO;
import com.hy.herb.pd.model.PdService;

@Controller
public class PdListController {
	@Autowired
	private PdService pdService;
	
	public PdListController() {
		System.out.println("생성자:PdListController");
	}
	
	//setter에 의한 종속객체 주입
	/*public void setPdService(PdService pdService) {
		this.pdService = pdService;
		System.out.println("setter 호출:PdListController-setPdService()");
	}*/



	@RequestMapping("/pd/pdList.do")
	public ModelAndView pdList(){
		//상품 목록 조회
		//1. 파라미터 읽어오기
		System.out.println("PdListController-pdList() 호출");
		
		//2. db작업
		List<PdDTO> alist=pdService.selectPdAll();
		System.out.println("상품목록 조회 결과 alist.size()="
				+ alist.size());
		
		//3. 결과, 뷰페이지 저장/리턴
		ModelAndView mav = new ModelAndView();
		mav.addObject("alist", alist);
		mav.setViewName("pd/pdList");
		
		return mav;
		
	}
	
	
}








