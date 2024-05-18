package org.kharkiv.khpi.model.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.kharkiv.khpi.model.UserEntity;
import org.kharkiv.khpi.model.exception.UserInvalidPasswordException;
import org.kharkiv.khpi.model.exception.UsernamePasswordEmptyException;

@Stateless
public class LoginService {

    @Inject
    private UserService userService;

    public void login(String username, String password) {
        if (username == null || username.isEmpty()) {
            throw new UsernamePasswordEmptyException();
        }

        UserEntity user = userService.findByUsername(username);

        if (user == null) {
            throw new UsernamePasswordEmptyException();
        }

        if (!user.getPassword().equals(password)) {
            throw new UserInvalidPasswordException();
        }
    }
}
