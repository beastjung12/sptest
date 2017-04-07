package com.hy.herb.member.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hy.herb.member.model.MemberService;
import com.hy.herb.member.model.MemberVO;

@Controller
@RequestMapping("/member")
public class MemberController {
	private static final Logger logger
	=LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping("/agreement.do")
	public String agreement(){
		//1
		logger.info("회원약관 화면 보여주기");
		
		//2
		
		//3
		return "member/agreement";
	}
	
	@RequestMapping("/register.do")
	public void register(){
		logger.info("회원가입 화면 보여주기");
	}
	
	@RequestMapping("/join.do")
	public String join(@ModelAttribute MemberVO memberVo,
		@RequestParam(value="email3" ,required=false) String email3,
		Model model){
		//1
		logger.info("회원가입 처리, 파라미터 vo={}", memberVo);
		
		//2
		//휴대폰 입력하지 않은 경우 처리
		String hp2=memberVo.getHp2();
		String hp3=memberVo.getHp3();
		if(hp2==null || hp2.isEmpty() || hp3==null || hp3.isEmpty()){
			memberVo.setHp1("");
			memberVo.setHp2("");
			memberVo.setHp3("");
		}
		
		//이메일 입력하지 않은 경우 처리
		String email1=memberVo.getEmail1();
				
		if(email1==null || email1.isEmpty()){
			memberVo.setEmail2(""); 
		}else{
			//직접입력인 경우 
			if(memberVo.getEmail2().equals("etc")){
				if(email3 !=null && !email3.isEmpty()){
					memberVo.setEmail2(email3);
				}else{
					memberVo.setEmail1("");
					memberVo.setEmail2("");
				}
			}
		}
		
		int cnt = memberService.memberInsert(memberVo);
		String msg="", url="";
		if(cnt>0){
			msg="회원가입되었습니다";
			url="/index.do";
		}else{
			msg="회원가입 실패";
			url="/member/register.do";
		}
		
		//3
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
	
}









