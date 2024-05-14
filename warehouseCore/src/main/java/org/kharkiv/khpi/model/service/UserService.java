package org.kharkiv.khpi.model.service;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.kharkiv.khpi.model.UserEntity;
import org.kharkiv.khpi.model.repository.UserDao;

@Stateless
public class UserService {

    @Inject
    private UserDao userDao;

    public UserEntity save(UserEntity userEntity) {
        return userDao.save(userEntity);
    }

    public void delete(Long id) {
        userDao.delete(id);
    }

    public void update(Long id, String username, String password) {
        UserEntity userEntity = userDao.findById(id);

        userEntity.setUsername(username);
        userEntity.setPassword(password);

        userDao.save(userEntity);
    }

    public UserEntity findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public UserEntity findById(Long id) {
        return userDao.findById(id);
    }
}
