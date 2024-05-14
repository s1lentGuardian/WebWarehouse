package org.kharkiv.khpi.model.repository;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.kharkiv.khpi.model.UserEntity;
import org.kharkiv.khpi.model.exception.UserNotFoundException;

@Stateless
public class UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    public UserEntity findByUsername(String username) {
        try {
            return entityManager.createQuery("SELECT u FROM UserEntity u WHERE u.username = :username", UserEntity.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
           return null;
        }
    }

    public UserEntity save(UserEntity userEntity) {
        if(userEntity.getUserId() == null)
            entityManager.persist(userEntity);
        else
            entityManager.merge(userEntity);

        return userEntity;
    }

    public UserEntity findById(long id) {
        try {
            return entityManager.find(UserEntity.class, id);
        } catch (NoResultException e) {
            throw new UserNotFoundException("User with such id doesn't exist");
        }
    }

    public void delete(UserEntity userEntity) {
        entityManager.remove(userEntity);
    }

    public void delete(long id) {
        UserEntity userEntity = findById(id);
        delete(userEntity);
    }
}
