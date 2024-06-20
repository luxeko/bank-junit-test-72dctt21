package org.app.Services.interfaces;

import org.app.Entities.User;

public interface IAuth {
    User login(String username, String password);
}
