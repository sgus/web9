package ru.study.web9.web.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.study.web9.web.dao.UserDAO;
import ru.study.web9.web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository("userDao")
@Transactional
public class UserDAOImpl implements UserDAO {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List getAllUsers() {
        return null;
    }

    @Override
    public void addUser(User user) {

    }

    @Override
    public void removeUserByLogin(String login) {

    }

    @Override
    public User getUserById(long id) {
        return null;
    }

    @Override
    public User getUserByLogin(String login) {
        return null;
    }

    @Override
    public void removeUserById(Long id) {

    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public boolean validateUser(User user) {
        return false;
    }

    @Override
    public User findUserByUsername(String username) {
        Query query = entityManager.createQuery("from User where email = :email");
        query.setParameter("email", username);
        return (User) query.getResultList().get(0);
    }
}
