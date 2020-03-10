package factories;

import models.Account;

public class AccountFactory {
    public static Account createAccount() {
        Account account = new Account();
        account.setBalance(0L);
        return account;
    }
}
