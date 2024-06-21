package org.app.Controllers;

import org.app.Entities.User;
import org.app.Services.impl.AuthImpl;

import java.io.IOException;

public class AuthController {
    public AuthController() {
    }

    public User login(String username, String password) {
        AuthImpl auth = new AuthImpl();
        return auth.login(username, password);
    }
}
