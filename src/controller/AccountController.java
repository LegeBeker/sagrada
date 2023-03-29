package controller;

import model.Account;

public class AccountController {
	public Account curAccount;

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
}
