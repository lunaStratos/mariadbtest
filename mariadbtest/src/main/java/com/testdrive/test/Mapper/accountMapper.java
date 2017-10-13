package com.testdrive.test.Mapper;

import java.util.ArrayList;

import com.testdrive.test.VO.Account;

public interface accountMapper {
	
	public int register(Account account);
	
	public ArrayList<Account> list();
	
	public Account login(Account account);

	public String idcheck(String id);

}
