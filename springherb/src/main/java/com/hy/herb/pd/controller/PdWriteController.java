package com.hy.herb.pd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hy.herb.pd.model.PdDTO;
import com.hy.herb.pd.model.PdService;

@Controller
public class PdWriteController {
	@Autowired
	private PdService pdService;
	
	public PdWriteController() {
		System.out.println("생성자: PdWriteController");
	}
	
	//setter에 의한 종속객체 주입
	/*public void setPdService(PdService pdService) {
		this.pdService = pdService;
		System.out.println("setter 호출 :PdWriteController-setPdService()");
	}*/

	
	@RequestMapping(value="/pd/pdWrite.do", 
			method=RequestMethod.GET)
	public ModelAndView write_get(){
		System.out.println("PdWriteController-write_get() 호출");
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("pd/pdWrite");
		
		return mav;
	}
	
	
	
	@RequestMapping(value="/pd/pdWrite.do", 
			method=RequestMethod.POST)	
	public ModelAndView write_post(@ModelAttribute PdDTO dto){
		//상품등록 처리
		System.out.println("write_post() 호출, 매개변수 dto="+dto);
		
		int cnt=pdService.insertPd(dto);
		System.out.println("상품등록 결과 cnt="+cnt);
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/pd/pdList.do");
		
		return mav;
	}
	
	
}











