package com.hy.herb.pd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hy.herb.pd.model.PdDTO;
import com.hy.herb.pd.model.PdService;

@Controller
@RequestMapping("/pd/pdEdit.do")
public class PdEditController {
	@Autowired
	private PdService pdService;
	
	public PdEditController() {
		System.out.println("생성자:PdEditController");
	}
	
	//setter에의해 주입받기
	/*public void setPdService(PdService pdService) {
		this.pdService = pdService;
		System.out.println("setter호출: PdEditController-setPdService()");
	}*/

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView pdEdit_get(
			@RequestParam(value="no", defaultValue="0") int no){
		//1.
		System.out.println("pdEdit_get()호출, 매개변수 no="+no);
		
		ModelAndView mav = new ModelAndView();
		if(no==0){
			mav.addObject("msg", "잘못된 url입니다");
			mav.addObject("url", "/pd/pdList.do");
			mav.setViewName("common/message");
			
			return mav; 
		}
		
		//2
		PdDTO dto=pdService.selectByNo(no);
		System.out.println("상품수정 화면-조회 결과 dto="+dto);
		
		//3
		mav.addObject("pdDto", dto);
		mav.setViewName("pd/pdEdit");
		
		return mav;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView pdEdit_post(@ModelAttribute PdDTO dto){
		//1
		System.out.println("pdEdit_post()호출, 매개변수 dto="+dto);
		
		//2
		int cnt=pdService.updatePd(dto);
		System.out.println("상품 수정 결과 cnt="+cnt);
		
		//3
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/pd/pdDetail.do?no="+dto.getNo());
		
		return mav;
	}
	
		
}





