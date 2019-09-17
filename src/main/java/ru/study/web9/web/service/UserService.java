package ru.study.web9.web.service;


import ru.study.web9.web.model.User;

import java.util.List;

public interface UserService {

    User getUserById(long id) ;

    User getUserByLogin(String login) ;

    List<User> getAllUsers();

    void addUser(User user) ;

    void deleteById(Long id);

    void updateUser(User user);

    boolean checkUser(User user);

}
