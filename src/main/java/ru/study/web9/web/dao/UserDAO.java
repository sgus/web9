package ru.study.web9.web.dao;

import ru.study.web9.web.model.User;

import java.util.List;

public interface UserDAO {
    List getAllUsers();

    void addUser(User user);

    void removeUserByLogin(String login);

    User getUserById(long id);

    User getUserByLogin(String login);

    void removeUserById(Long id);

    void updateUser(User user);

    boolean validateUser(User user);

    User findUserByUsername(String username);

}
