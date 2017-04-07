package com.hy.herb.pd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hy.herb.pd.model.PdService;

@Controller
public class PdDeleteController {
	@Autowired
	private PdService pdService;
	
	public PdDeleteController() {
		System.out.println("생성자:PdDeleteController");
	}
	
	//setter에 의한 종속객체 주입	
	/*public void setPdService(PdService pdService) {
		this.pdService = pdService;
		System.out.println("setter호출 : PdDeleteController-setPdService()");
	}*/

	@RequestMapping("/pd/pdDelete.do")
	public ModelAndView pdDelete(
			@RequestParam(value="no", defaultValue="0") int no){
		//1
		System.out.println("pdDelete()호출- 매개변수 no="+no);
		
		ModelAndView mav = new ModelAndView();
		if(no==0){
			mav.addObject("msg", "잘못된 url입니다");
			mav.addObject("url", "/pd/pdList.do");			
			mav.setViewName("common/message");
			
			return mav;
		}
		
		//2
		String msg="", url="/pd/pdDetail.do?no="+no;
		int cnt=pdService.deletePd(no);
		System.out.println("상품 삭제 결과 cnt="+cnt);
		
		if(cnt>0){
			msg="상품삭제 성공";
			url="/pd/pdList.do";						
		}else{
			msg="상품삭제 실패";
		}
	
		//3
		mav.addObject("msg", msg);
		mav.addObject("url", url);
		mav.setViewName("common/message");
		
		return mav;
	}
	
	
}






