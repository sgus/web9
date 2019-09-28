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
        List query = entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
        return query;
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void removeUserByLogin(String login) {
        Query query = entityManager.createQuery("delete from User where login=:login");
        query.setParameter("login", login).executeUpdate();
    }

    @Override
    public User getUserById(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getUserByLogin(String login) {
        return entityManager.find(User.class, login);
    }

    @Override
    public void removeUserById(Long id) {
        Query query = entityManager.createQuery("delete from User where id = :ID");
        query.setParameter("ID", id);
        query.executeUpdate();
    }



    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
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
