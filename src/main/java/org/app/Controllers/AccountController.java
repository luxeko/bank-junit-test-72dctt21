package org.app.Controllers;

import org.app.Services.impl.AccountImpl;
import org.app.Entities.Account;
import org.app.Services.interfaces.IAccount;
import org.app.Services.interfaces.ICustomer;

public class AccountController {
    private final AccountImpl accountImpl = new AccountImpl();
    public AccountController() {
        
    }
    
    public AccountController(IAccount iAccount) {
        
    }
    public void insertAccount(Account account) {
       this.accountImpl.createAccount(account);
    }
    
    public void payInAccount() {
        this.accountImpl.payInAccount();
    }
    
    public void withdrawAccount() {
        this.accountImpl.withdrawAccount();
    }
}
 