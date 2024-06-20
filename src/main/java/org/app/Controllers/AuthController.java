package org.app.Controllers;

import org.app.Entities.User;
import org.app.Services.interfaces.IAuth;

public class AuthController {
    private IAuth iAuth;

    public AuthController() {
    }

    public AuthController(IAuth iAuth) {
        this.iAuth = iAuth;
    }

    public User login(String username, String password) {
        return iAuth.login(username, password);
    }
}
