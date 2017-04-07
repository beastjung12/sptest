package com.hy.herb.reboard.model;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hy.herb.common.SearchVO;

@Service
public class ReBoardServiceImpl implements ReBoardService{
	private static final Logger logger
	=LoggerFactory.getLogger(ReBoardServiceImpl.class);
	
	@Resource(name="fileUploadProperties")
	private Properties fileProperties;
	
	@Autowired
	private ReBoardDAO reBoardDao;
			
	public int insertReBoard(ReBoardVO vo){
		return reBoardDao.insertReBoard(vo);
	}
	
	public List<ReBoardVO> selectAll(SearchVO searchVo){
		return reBoardDao.selectAll(searchVo);
	}
	
	public int updateReadCount(int no){
		return reBoardDao.updateReadCount(no);
	}
	
	public ReBoardVO selectByNo(int no){
		return reBoardDao.selectByNo(no);
	}
	
	public int updateReBoard(ReBoardVO vo){
		return reBoardDao.updateReBoard(vo);
	}
	
	public boolean checkPwd(int no, String pwd){
		String dbPwd =reBoardDao.selectPwd(no);
		
		if(pwd.equals(dbPwd)){
			return true;
		}else{
			return false;
		}
	}
	
	public int deleteReBoard(Map<String, String> map){
		return reBoardDao.deleteReBoard(map);
	}

	@Override
	public int selectTotalRecord(SearchVO searchVo) {
		return reBoardDao.selectTotalRecord(searchVo);
	}
	
	@Override
	public int updateDownCount(int no) {
		return reBoardDao.updateDownCount(no);
	}
	
	@Override
	@Transactional
	public int reply(ReBoardVO vo) {
		int cnt = reBoardDao.updateSortNo(vo);
		logger.info("답변처리- sortNo 수정 결과, cnt={}", cnt);
		
		//sortNo, step 1씩 증가시키기
		vo.setSortNo(vo.getSortNo()+1);
		vo.setStep(vo.getStep()+1);
		
		cnt = reBoardDao.reply(vo);
		logger.info("답변처리 결과, cnt={}", cnt);
		
		return cnt;
	}
	
	
	/*
	public List<ReBoardVO> selectMainNotice() throws SQLException{
		return reBoardDao.selectMainNotice();
	}*/
	
	public  List<Map<String, Object>> fileUpload(HttpServletRequest request){
		//파일 업로드 처리
		
		//업로드 처리를 위해 MultipartHttpServletRequest로 다운 캐스팅
		MultipartHttpServletRequest multiRequest
		=(MultipartHttpServletRequest) request;
		
		Map<String, MultipartFile> fileMap
			=multiRequest.getFileMap();
		
		//업로드한 파일들의 정보를 담을 List
		List<Map<String, Object>> fileList
		=new ArrayList<Map<String,Object>>();
		
 		Iterator<String> iter = fileMap.keySet().iterator();
 		while(iter.hasNext()){
 			String key = iter.next();
  			MultipartFile tempFile = fileMap.get(key);
  			
  			//파일이 업로드된 경우
  			if(!tempFile.isEmpty()){
  				String originFileName = tempFile.getOriginalFilename();
  				//이름이 중복되는 경우 파일명 변경하기
  				String fileName = getUniqueFileName(originFileName);
  				
  				long fileSize=tempFile.getSize();
  				
  				//파일 업로드 처리
  				//업로드할 경로 구하기  				
  				String savePath= getUploadPath(request); 
  				File file = new File(savePath, fileName);
  				try {
					tempFile.transferTo(file);
					logger.info("파일 업로드 완료! 업로드 경로:{},파일명:{}",
							savePath, fileName);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
  				  				
  				//파일명과 파일크기를 먼저 map에 저장
  				Map<String, Object> map 
  					= new HashMap<String, Object>();
  				map.put("fileName", fileName);
  				map.put("fileSize", fileSize);
  				map.put("originalFileName", originFileName);
  				
  				//다시 map을 List에 저장
  				fileList.add(map);
  			}
 		}//while
 		
 		return fileList;
	}
	
	public String getUploadPath(HttpServletRequest request){
		//파일 업로드 경로 구하기
		String type=fileProperties.getProperty("file.upload.type");
		
		String upPath="";
		if(type.equals("test")){
			//테스트 경로
			upPath=fileProperties.getProperty("file.upload.path.test");
		}else{
			//배포시 실제 경로
			String dir = fileProperties.getProperty("file.upload.path");
			//=> pds_upload
			
			/*
			 [1] application.getRealPath(dir); 
			 [2] config.getServletContext().getRealPath(dir); 
			    또는
	       request.getSession().getServletContext().getRealPath(dir); 			  
			 */
			upPath
			=request.getSession().getServletContext().getRealPath(dir);			
		}
		
		return upPath;		
	}
	
	public String getUniqueFileName(String fName){
		//파일명이 중복되지 않도록 파일명 이름 변경
		//ab.txt => ab_20170404152650123.txt
		//=> 순수 파일명과 확장자사이에 현재시간(밀리초) 추가
		int idx = fName.lastIndexOf(".");
 		String fileNm = fName.substring(0, idx);  //ab
 		String ext = fName.substring(idx);  //.txt
 		
 		return fileNm+"_"+getCurrentTime()+ext;
	}
	
	public String getCurrentTime(){
		Date today = new Date();
		SimpleDateFormat sdf 
			= new SimpleDateFormat("yyyyMMddHHmmssSSS");
		
		return sdf.format(today);
	}

	

	
	
}













