package com.hy.herb.member.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{
	@Autowired
	private MemberDAO memberDao;
	
	//아이디 중복확인에서 사용하는 상수
	public static final int EXIST_ID=1;  //이미 해당 아이디가 존재함
	public static final int NONE_EXIST_ID=2; //아이디가 존재하지 않음
	
	//로그인 처리에 사용하는 상수
	public static final int LOGIN_OK=1;  //로그인 성공
	public static final int ID_NONE=2;   //아이디가 없는 경우
	public static final int PWD_DISAGREE=3; //비밀번호가 일치하지 않는 경우
	
	@Override
	public int memberInsert(MemberVO vo) {
		return memberDao.memberInsert(vo) ;
	}
	
	
	
	/*public int insertMember(MemberVO vo) throws SQLException{
		int cnt=dao.insertMember(vo);
		return cnt;
	}
		
	public int duplicateUserid(String userid) throws SQLException{
		return dao.duplicateUserid(userid);
	}
	
	public int loginCheck(String userid, String pwd) throws SQLException{
		return dao.loginCheck(userid, pwd);
	}
	
	public MemberVO selectByUserid(String userid) throws SQLException{
		return dao.selectByUserid(userid);
	}
		
	public int updateMember(MemberVO vo) throws SQLException{
		return dao.updateMember(vo);
	}

	public int withdrawMember(String userid) throws SQLException{
		return dao.withdrawMember(userid);
	}
		*/
	
}













