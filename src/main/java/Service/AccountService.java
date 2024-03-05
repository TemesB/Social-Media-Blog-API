package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    // add dependency
    private AccountDAO accountDAO;

    // no-args constructor 
    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    public Account registerNewAcct(Account account) {
        // Check if username is blank or password is too short(less than 4)
        if (account.getUsername().length() > 0 &&  account.getPassword().length() >= 4) {
            return  accountDAO.addAccount(account);
        } else {
            return null;
        }
    }

    //    Method to authenticate for login
    public Account authenticate(Account account) {
        if(accountDAO.getAccount(account) != null){
            return accountDAO.getAccount(account);
        }
        else {
            return null;
        }
    }
}