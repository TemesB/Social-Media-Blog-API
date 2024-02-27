package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    public boolean registerAccount(Account account) {
        // Check if username is blank or password is too short
        if (account.getUsername().isEmpty() || account.getPassword().length() < 4) {
            return false;
        }
        // Check if account with the same username already exists
        if (accountDAO.accountExists(account.getUsername())) {
            return false;
        }
        // Add the account to the database
        accountDAO.addAccount(account);
        return true;
    }

    public Account authenticate(String username, String password) {
        return accountDAO.getAccount(username, password);
    }
}