package controller;

public class AccountController {
	public Account curAccount;
	
	public Boolean login(String username, String password) {
		Account tempAccount = new Account();
		tempAccount.loginAccount(username, password);
		this.curAccount = tempAccount;
	}
}
