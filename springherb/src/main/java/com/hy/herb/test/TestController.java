package com.hy.herb.test;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping("/test")
public class TestController {
	private static final Logger logger
		=LoggerFactory.getLogger(TestController.class);
	
	@RequestMapping(value="/uploadTest.do", 
			method=RequestMethod.GET)
	public String upload_get(){
		logger.info("업로드 화면 보여주기");
		
		return "sandbox/uploadTest";
	}
	
	@RequestMapping(value="/uploadTest.do", method=RequestMethod.POST)
	public String upload_post(MultipartHttpServletRequest multiRequest,
			Model model){
		String address = multiRequest.getParameter("address");
		
		MultipartFile tempFile = multiRequest.getFile("upfile");		
		//파일이 업로드 된 경우만 처리
		String fileName="";
		long fileSize=0;
		if(!tempFile.isEmpty()){
			fileName = tempFile.getOriginalFilename();
			fileSize=tempFile.getSize();
			
			//업로드 처리하기
			String upPath 
= "D:\\lecture\\workspace_list\\sp_ws\\springherb\\src\\main\\webapp\\pds_upload";
			File file = new File(upPath, fileName);
			try {
				tempFile.transferTo(file);
				logger.info("업로드 경로:{}, 파일명:{} 업로드 완료",
						upPath, fileName);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		model.addAttribute("fileName", fileName);
		model.addAttribute("fileSize", fileSize);
		model.addAttribute("address", address);
		
		return "sandbox/result";		
	}
	
	
	
}








