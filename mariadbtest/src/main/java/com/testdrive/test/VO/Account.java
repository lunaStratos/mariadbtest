package com.testdrive.test.VO;

public class Account {

	private String uid;
	private String password;
	private String name;
	private String rememberMe;

	public Account(String uid, String password, String name, String rememberMe) {
		super();
		this.uid = uid;
		this.password = password;
		this.name = name;
		this.rememberMe = rememberMe;
	}

	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(String rememberMe) {
		this.rememberMe = rememberMe;
	}

	@Override
	public String toString() {
		return "Account [uid=" + uid + ", password=" + password + ", name=" + name + ", rememberMe=" + rememberMe + "]";
	}

}
