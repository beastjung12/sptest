package com.hy.herb.pd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hy.herb.pd.model.PdDTO;
import com.hy.herb.pd.model.PdService;

@Controller
public class PdDetailController {
	@Autowired
	private PdService pdService;
	
	public PdDetailController() {
		System.out.println("생성자 : PdDetailController");
	}
	
	//setter에 의한 종속객체 주입
	/*public void setPdService(PdService pdService) {
		this.pdService = pdService;
		System.out.println("setter 호출:PdDetailController-setPdService()");
	}*/

	@RequestMapping("/pd/pdDetail.do")
	public ModelAndView pdDetail(
			@RequestParam(value="no", defaultValue="0") int no){
		//상품 상세보기
		//1.
		System.out.println("PdDetailController-pdDetail() 호출, "
			+ "매개변수 no="+no);
		
		ModelAndView mav = new ModelAndView();
		if(no==0){
			mav.addObject("msg", "잘못된 url입니다");
			mav.addObject("url", "/pd/pdList.do");
			mav.setViewName("common/message");
			
			return mav; 
		}
		
		//2.
		PdDTO dto=pdService.selectByNo(no);
		System.out.println("상품상세보기 결과, dto="+dto);
		
		//3.
		mav.addObject("dto", dto);
		mav.setViewName("pd/pdDetail");
		
		return mav;		
	}
	
}






