package com.hy.herb.zipcode.model;

import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class ZipcodeDAOMybatis extends SqlSessionDaoSupport 
	implements ZipcodeDAO{
	private String namespace="config.mybatis.mapper.oracle.zipcode";
	
	public List<ZipcodeVO> selectZipcode(String dong){
		//우편번호 검색	
		return 
		getSqlSession().selectList(namespace+".selectZipcode", dong);
	}
	
}









