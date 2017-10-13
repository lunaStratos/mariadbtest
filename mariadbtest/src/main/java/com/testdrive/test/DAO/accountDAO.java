package com.testdrive.test.DAO;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.testdrive.test.Mapper.accountMapper;
import com.testdrive.test.VO.Account;

@Repository
public class accountDAO {

	@Autowired
	SqlSession sqlSession;

	public int register(Account account) {
		// TODO Auto-generated method stub

		accountMapper mapper = sqlSession.getMapper(accountMapper.class);
		int result = mapper.register(account);
		return result;
	}

	public Account login(Account account) {
		// TODO Auto-generated method stub
		accountMapper mapper = sqlSession.getMapper(accountMapper.class);
		Account result = mapper.login(account);

		return result;
	}

	public ArrayList<Account> list() {
		// TODO Auto-generated method stub
		accountMapper mapper = sqlSession.getMapper(accountMapper.class);
		ArrayList<Account> result = mapper.list();

		return result;
	}


	public String idcheck(String id) {
		// TODO Auto-generated method stub
		accountMapper mapper = sqlSession.getMapper(accountMapper.class);
		String result = mapper.idcheck(id);
		return result;
	}

}
