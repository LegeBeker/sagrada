package controller;

import model.Account;

public class AccountController {
	private Account curAccount;

	public Account getAccount() {
		return this.curAccount;
	}

	public Boolean loginAccount(String username, String password) {
		Account curAccount = new Account();

		curAccount.setUsername(username);
		curAccount.setPassword(password);

		if (!curAccount.login()) {
			return false;
		}
		this.curAccount = curAccount;
		return true;
	}

	public boolean createAccount(String username, String password) {
		Account curAccount = new Account();

		curAccount.setUsername(username);
		curAccount.setPassword(password);

		if (!curAccount.createAccount()) {
			return false;
		}
		this.curAccount = curAccount;
		return true;
	}

	public boolean logoutAccount() {
		this.curAccount = null;
		return true;
	}
}
