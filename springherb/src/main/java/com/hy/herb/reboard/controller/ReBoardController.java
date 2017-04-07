package com.hy.herb.reboard.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hy.herb.common.PaginationInfo;
import com.hy.herb.common.SearchVO;
import com.hy.herb.common.Utility;
import com.hy.herb.reboard.model.ReBoardService;
import com.hy.herb.reboard.model.ReBoardVO;

@Controller
@RequestMapping("/reBoard")
public class ReBoardController {
	private static final Logger logger 
	= LoggerFactory.getLogger(ReBoardController.class);

	@Autowired
	private ReBoardService reBoardService;

	@RequestMapping(value="/write.do", method=RequestMethod.GET)
	public String write_get(){
		logger.info("글쓰기 화면 보여주기");

		return "reBoard/write";
	}

	@RequestMapping(value="/write.do", method=RequestMethod.POST)
	public String write_post(HttpServletRequest request
			,@ModelAttribute ReBoardVO reBoardVo){
		//1
		logger.info("글쓰기 처리, 파라미터 reBoardVo={}", reBoardVo);

		//파일 업로드 처리
		List<Map<String, Object>> fileList 
		= reBoardService.fileUpload(request);

		String fileName="", originalFileName="";
		long fileSize=0;
		if(!fileList.isEmpty()){
			for(Map<String, Object> map : fileList){
				fileName = (String) map.get("fileName");
				fileSize = (Long) map.get("fileSize");
				originalFileName = (String) map.get("originalFileName");
			}//for
		}//if

		reBoardVo.setFileName(fileName);
		reBoardVo.setFileSize(fileSize);
		reBoardVo.setOriginalFileName(originalFileName);

		//2
		int cnt = reBoardService.insertReBoard(reBoardVo);
		logger.info("글쓰기 결과, cnt={}", cnt);

		//3
		return "redirect:/reBoard/list.do";
	}

	@RequestMapping("/list.do")
	public String list(@ModelAttribute SearchVO searchVo, Model model){
		//1.
		logger.info("글목록, 파라미터 searchVo={}", searchVo);

		//2
		//[1] PaginationInfo 객체 생성 
		//=> firstRecordIndex 를 계산하기 위함
		PaginationInfo pagingInfo = new PaginationInfo();
		pagingInfo.setBlockSize(Utility.BLOCKSIZE);
		pagingInfo.setRecordCountPerPage(Utility.RECORDCOUNT_PERPAGE);
		pagingInfo.setCurrentPage(searchVo.getCurrentPage());

		//[2] SearchVO 값 셋팅
		searchVo.setRecordCountPerPage(Utility.RECORDCOUNT_PERPAGE);
		searchVo.setFirstRecordIndex(pagingInfo.getFirstRecordIndex());


		List<ReBoardVO> alist =reBoardService.selectAll(searchVo);
		logger.info("글목록 조회결과, alist.size()={}", alist.size());

		int totalRecord =reBoardService.selectTotalRecord(searchVo);
		logger.info("글목록 조회-전체레코드 개수조회 결과, totalRecord={}",
				totalRecord);

		pagingInfo.setTotalRecord(totalRecord);

		//3
		//모델에 결과 저장
		model.addAttribute("alist", alist);
		model.addAttribute("pagingInfo", pagingInfo);

		//뷰페이지 리턴
		return "reBoard/list";
	}

	@RequestMapping("/countUpdate.do")
	public String countUpdate(
			@RequestParam(value="no", defaultValue="0") int no,
			Model model){
		//1.
		logger.info("조회수 증가 처리, 파라미터 no={}", no);
		if(no==0){
			model.addAttribute("msg", "잘못된 url입니다");
			model.addAttribute("url", "/reBoard/list.do");

			return "common/message";
		}

		//2.
		int cnt = reBoardService.updateReadCount(no);
		logger.info("조회수 증가 결과, cnt={}", cnt);

		//3.
		return "redirect:/reBoard/detail.do?no="+no;
	}

	@RequestMapping("/detail.do")
	public String detail(
			@RequestParam(value="no", defaultValue="0") int no, 
			HttpServletRequest request,
			Model model){
		//1.
		logger.info("글 상세보기, 파라미터 no={}", no);
		if(no==0){
			model.addAttribute("msg", "잘못된 url입니다");
			model.addAttribute("url", "/reBoard/list.do");

			return "common/message";
		}

		//2.
		ReBoardVO vo = reBoardService.selectByNo(no);
		logger.info("상세보기 결과, vo={}", vo);

		//파일이 첨부된 경우 파일 정보 구하기
		String fileInfo="", downInfo="";
		String fileName=vo.getFileName();
		if(fileName!= null && !fileName.isEmpty()){
			float fileSize = vo.getFileSize()/1000f;

			fileInfo
			="<img src='"+request.getContextPath()
			+"/images/file.gif' alt='파일이미지'> "
			+ vo.getOriginalFileName()+" ("+fileSize + "KB)";
			downInfo="다운 : " + vo.getDownCount();
		}

		//3.
		model.addAttribute("reBoardVo", vo);
		model.addAttribute("fileInfo", fileInfo);
		model.addAttribute("downInfo", downInfo);

		return "reBoard/detail";

	}

	@RequestMapping(value="/edit.do", method=RequestMethod.GET)
	public String edit_get(
			@RequestParam(value="no", defaultValue="0") int no, Model model){
		//1
		logger.info("글수정 화면 보여주기, 파라미터 no={}", no);
		if(no==0){
			model.addAttribute("msg", "잘못된 url입니다");
			model.addAttribute("url", "/reBoard/list.do");

			return "common/message";
		}

		//2
		ReBoardVO vo =reBoardService.selectByNo(no);
		logger.info("글수정화면-조회 결과 vo={}", vo);

		//3
		model.addAttribute("vo", vo);

		return "reBoard/edit";
	}

	@RequestMapping(value="/edit.do",method=RequestMethod.POST)
	public String edit_post(@ModelAttribute ReBoardVO vo,
			@RequestParam String oldFileName,
			HttpServletRequest request,	Model model){
		//1.
		logger.info("글 수정 처리, 파라미터 vo={}", vo);

		String msg="", url="/reBoard/edit.do?no="+vo.getNo();
		//비밀번호 체크 후
		if(reBoardService.checkPwd(vo.getNo(), vo.getPwd())){
			//업로드 처리
			List<Map<String, Object>> fileList 
			=reBoardService.fileUpload(request);

			//파일이 새로 업로드 된 경우
			String fileName="", originalFileName="";
			long fileSize=0;
			if(!fileList.isEmpty()){
				//=> [1] 업로드된 파일의 정보를 읽어서 vo에 셋팅
				for(Map<String, Object> map : fileList){
					fileName=(String) map.get("fileName");
					fileSize=(Long) map.get("fileSize");
					originalFileName=(String) map.get("originalFileName");
				}//for

				//=> [2] 기존 파일이 있다면 삭제
				if(oldFileName!=null && !oldFileName.isEmpty()){
					String upPath = reBoardService.getUploadPath(request);
					File oldFile = new File(upPath, oldFileName);
					if(oldFile.exists()){
						boolean bool =oldFile.delete();
						logger.info("기존 파일 삭제 여부:{}", bool);
					}
				}
			}//if

			vo.setFileName(fileName);
			vo.setFileSize(fileSize);
			vo.setOriginalFileName(originalFileName);

			//2.
			//비밀번호가 일치하면 update 처리
			int cnt = reBoardService.updateReBoard(vo);
			logger.info("글 수정 결과 cnt={}", cnt);

			if(cnt>0){
				msg="글 수정 성공";
				url="/reBoard/detail.do?no="+vo.getNo();
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

	@RequestMapping(value="/delete.do", method=RequestMethod.GET)
	public String delete_get(
			@RequestParam(value="no", defaultValue="0") int no,
			Model model){
		//1
		logger.info("글 삭제 화면, 파라미터 no={}",no);

		if(no==0){
			model.addAttribute("msg", "잘못된 url입니다");
			model.addAttribute("url", "/reBoard/list.do");

			return "common/message";
		}

		//2		
		//3
		return "reBoard/delete";
	}

	@RequestMapping(value="/delete.do", method=RequestMethod.POST)
	public String delete_post(@RequestParam int no, 
			@RequestParam String pwd, HttpServletRequest request, 
			Model model){
		//1
		logger.info("글 삭제 처리, 파라미터 no={}, pwd={}", no, pwd);
				
		//2
		String msg="", url="/reBoard/delete.do?no="+no;
		if(reBoardService.checkPwd(no, pwd)){
			ReBoardVO vo =reBoardService.selectByNo(no);
			
			Map<String, String> map=new HashMap<String, String>();
			map.put("step", String.valueOf(vo.getStep()));						
			map.put("no", no+"");
			map.put("groupNo", Integer.toString(vo.getGroupNo()));
			
			int cnt = reBoardService.deleteReBoard(map);			
			logger.info("글삭제 cnt={}", cnt);
			
			msg="글 삭제 성공";
			url="/reBoard/list.do";
			
			//기존 파일이 존재하면 파일삭제 처리
			String fileName = vo.getFileName();
			if(fileName!=null && !fileName.isEmpty()){
				String upPath = reBoardService.getUploadPath(request);
				File oldFile = new File(upPath, fileName);
				if(oldFile.exists()){
					boolean bool=oldFile.delete();
					logger.info("글삭제-파일삭제 여부:{}", bool);
				}
			}
		}else{
			msg="비밀번호가 일치하지 않습니다.";
		}

		//3
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);

		return "common/message";		
	}

	@RequestMapping("/download.do")
	public ModelAndView download(@RequestParam int no, 
			@RequestParam String fileName, HttpServletRequest request){
		//1.
		logger.info("다운로드 수 증가 처리, 파라미터 no={}, fileName={}",
				no, fileName);

		//db- 다운로드 수 증가-update
		//2.
		int cnt = reBoardService.updateDownCount(no);
		logger.info("다운로드수 증가 결과, cnt={}", cnt);

		//3.		
		//다운로드할 파일을 파일 객체로 만들어서 맵에 저장한 후
		//다운로드처리할 뷰로 포워드
		String upPath=reBoardService.getUploadPath(request);
		File file = new File(upPath,fileName);

		Map<String, Object> fileMap
		=new HashMap<String, Object>();
		fileMap.put("downFile", file);

		/* ModelAndView(String viewName, map model)*/
		ModelAndView mav
		= new ModelAndView("reBoardDownloadView", fileMap);

		return mav;
	}

	@RequestMapping(value="/reply.do", method=RequestMethod.GET)
	public String reply_get(
			@RequestParam(value="no", defaultValue="0") int no,
			Model model){
		//1
		logger.info("답변하기 화면보여주기, 파라미터 no={}", no);
		
		if(no==0){
			model.addAttribute("msg", "잘못된 url입니다");
			model.addAttribute("url", "/reBoard/list.do");
			
			return "common/message";
		}
		
		//2
		ReBoardVO vo =reBoardService.selectByNo(no);
		logger.info("답변하기 화면-조회 결과, vo={}", vo);
		
		//3
		model.addAttribute("vo", vo);
		
		return "reBoard/reply";
	}
	
	@RequestMapping(value="/reply.do", method=RequestMethod.POST)
	public String reply_post(@ModelAttribute ReBoardVO vo,
			Model model){
		//1.
		logger.info("답변달기 처리, 파라미터 vo={}", vo);
		
		//2.
		int cnt = reBoardService.reply(vo);
		logger.info("답변달기 결과, cnt={}", cnt);
		
		String msg="", url="";
		if(cnt>0){
			msg="답변하기 성공!";
			url="/reBoard/list.do";
		}else{
			msg="답변하기 실패!";
			url="/reBoard/reply.do?no="+vo.getNo();
		}
		
		//3.
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	

}










