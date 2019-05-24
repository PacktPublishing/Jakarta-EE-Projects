package org.jakartaeeprojects.spares.auth.boundary;

import org.jakartaeeprojects.spares.auth.control.UserNotFoundException;
import org.jakartaeeprojects.spares.auth.entity.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Stateless
public class UserService {

    @PersistenceContext
    private EntityManager em;

    public User findByCredentials(String email, String password) {
        User user = findByEmailAndPassword(email, password)
                .orElseThrow(UserNotFoundException::new);
        return user;
    }

    private Optional<User> findByEmailAndPassword(String email, String password) {
        try {
            return Optional.of(em
                    .createQuery("FROM User u WHERE u.email = :email and u.password = :password", User.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
