package com.hy.herb.member.model;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDAOMybatis extends SqlSessionDaoSupport 
	implements MemberDAO{

	private String namespace="config.mybatis.mapper.oracle.member";
	
	@Override
	public int memberInsert(MemberVO vo) {
		return getSqlSession().insert(namespace+".memberInsert", vo);
	}

	
	
	/*public int insertMember(MemberVO vo) throws SQLException{
		//회원가입
		Connection conn=null;
		PreparedStatement ps=null;
		int cnt=0;
		try{
			//1,2. conn
			conn=pool.getConnection();
			
			//3. ps
			String sql="insert into member(no, userid, name, pwd, email, hp,"
				+ " zipcode, address, addressDetail)"
				+"	values(member_seq.nextval,?,?,?,?,?,?,?,?)";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getUserid());
			ps.setString(2, vo.getName());
			ps.setString(3, vo.getPwd());
			ps.setString(4, vo.getEmail());
			ps.setString(5, vo.getHp());
			ps.setString(6, vo.getZipcode());
			ps.setString(7, vo.getAddress());
			ps.setString(8, vo.getAddressDetail());
			
			//4. exec
			cnt = ps.executeUpdate();
			System.out.println("회원가입 결과 cnt="+cnt+", 매개변수 vo="+vo);
		}finally{
			pool.dbClose(ps, conn);
		}
		
		return cnt;
	}
	
	public int duplicateUserid(String userid) throws SQLException{
		//아이디 중복확인
		
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int result=0;
		
		try{
			//1,2
			conn=pool.getConnection();
			
			//3.
			String sql="select count(*) from member"
				+"	where userid=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, userid);
			
			//4.
			rs = ps.executeQuery();
			if(rs.next()){
				int count = rs.getInt(1);
				if(count>0){
					//해당 아이디가 이미 존재
					result=MemberService.EXIST_ID;
				}else{
					//존재하지 않음
					result=MemberService.NONE_EXIST_ID;					
				}
			}
			System.out.println("아이디 중복확인 결과 result="+result
				+", userid="+userid);
		}finally{
			pool.dbClose(rs, ps, conn);
		}
		
		return result;
	}
	
	public int loginCheck(String userid, String pwd) throws SQLException{
		//로그인 처리
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		int result=0;
		
		try{
			//1,2
			conn=pool.getConnection();
			
			//3.
			String sql="select pwd from member where userid=? and outdate is null";
			ps=conn.prepareStatement(sql);
			ps.setString(1, userid);
			
			//4.
			rs = ps.executeQuery();
			if(rs.next()){
				String dbPwd =rs.getString(1);
				
				if(dbPwd.equals(pwd)){
					result=MemberService.LOGIN_OK; //로그인 성공
				}else{
					result=MemberService.PWD_DISAGREE; //비밀번호 불일치
				}
			}else{
				//해당 아이디가 없는 경우
				result=MemberService.ID_NONE;
			}
			
			System.out.println("로그인 처리 결과 result="+result
				+", 매개변수 userid="+userid+", pwd="+pwd);
		}finally{
			pool.dbClose(rs, ps, conn);
		}
		
		return result;
	}
	
	public MemberVO selectByUserid(String userid) throws SQLException{
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		MemberVO vo = new MemberVO();		
		try{
			//1,2
			conn=pool.getConnection();
			
			//3.
			String sql="select * from member where userid=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, userid);
			
			//4.
			rs = ps.executeQuery();
			while(rs.next()){
				String address = rs.getString("address");
				vo.setAddress(address);				
				//vo.setAddress(rs.getString("address"));
				
				vo.setAddressDetail(rs.getString("addressDetail"));
				vo.setAuthCode(rs.getString("authCode"));
				vo.setEmail(rs.getString("email"));
				vo.setHp(rs.getString("hp"));
				vo.setMileage(rs.getInt("mileage"));
				vo.setName(rs.getString("name"));
				vo.setNo(rs.getInt("no"));
				vo.setOutdate(rs.getTimestamp("outdate"));
				vo.setRegdate(rs.getTimestamp("regdate"));
				vo.setPwd(rs.getString("pwd"));
				vo.setUserid(rs.getString("userid"));
				vo.setZipcode(rs.getString("zipcode"));				
			}
			
			System.out.println("회원정보 조회 결과 vo="+vo
				+", 매개변수 userid="+userid);
		}finally{
			pool.dbClose(rs, ps, conn);
		}
		
		return vo;
	}
	
	public int updateMember(MemberVO vo) throws SQLException{
		//회원 정보 수정
		Connection conn=null;
		PreparedStatement ps=null;
		int cnt=0;
		try{
			//1,2. conn
			conn=pool.getConnection();
			
			//3. ps
			String sql="update member set email=?, hp=?,"
				+ " zipcode=?, address=?, addressDetail=?"
				+"	where userid=? and outdate is null";
			ps=conn.prepareStatement(sql);
			ps.setString(1, vo.getEmail());
			ps.setString(2, vo.getHp());
			ps.setString(3, vo.getZipcode());
			ps.setString(4, vo.getAddress());
			ps.setString(5, vo.getAddressDetail());
			ps.setString(6, vo.getUserid());
			
			//4. exec
			cnt = ps.executeUpdate();
			System.out.println("회원정보수정 결과 cnt="+cnt+", 매개변수 vo="+vo);
		}finally{
			pool.dbClose(ps, conn);
		}
		
		return cnt;
	}
	
	public int withdrawMember(String userid) throws SQLException{
		//회원탈퇴 처리 - outdate에 현재일자 update
		
		Connection conn=null;
		PreparedStatement ps=null;
		int cnt=0;
		try{
			conn=pool.getConnection();
			
			String sql="update member"
				+"	set outdate=sysdate"
				+" where userid=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, userid);
			
			cnt = ps.executeUpdate();
			System.out.println("회원탈퇴 결과 cnt="+cnt+", 매개변수 userid="
					+userid);
		}finally{
			pool.dbClose(ps, conn);
		}
		
		return cnt;
		
	}*/
	
}











