package model;

import db.AccountDB;

public class Account {
	private String username;
	private String password;

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean login() {
		return AccountDB.checkLogin(username, password);
	}
}
