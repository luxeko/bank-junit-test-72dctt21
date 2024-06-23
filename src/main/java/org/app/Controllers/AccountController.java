package org.app.Controllers;

import org.app.Services.impl.AccountImpl;
import org.app.Entities.Account;
import org.app.Services.interfaces.ICustomer;

public class AccountController {
    private final AccountImpl accountImpl = new AccountImpl();
    public AccountController() {
        
    }
    public boolean insertAccount(Account account) {
        return this.accountImpl.createAccount(account);
    }
    
    public void payInAccount() {
        this.accountImpl.payInAccount();
    }
}
 