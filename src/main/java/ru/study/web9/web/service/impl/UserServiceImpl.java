package ru.study.web9.web.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.study.web9.web.dao.RoleDAO;
import ru.study.web9.web.dao.UserDAO;
import ru.study.web9.web.model.Role;
import ru.study.web9.web.model.User;
import ru.study.web9.web.service.UserService;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDAO dao;

    @Autowired
    public UserServiceImpl(UserDAO dao) {

        this.dao = dao;
    }

    @Autowired
    private RoleDAO roleDAO;

    @Override
    public User getUserById(long id) {
        return dao.getUserById(id);
    }

    @Override
    public User getUserByLogin(String login) {
        return dao.getUserByLogin(login);
    }

    @Override
    public List<User> getAllUsers() {
        return dao.getAllUsers();
    }

    @Override
    public void addUser(User user) {

            List<Role> roles = new ArrayList<Role>();
            user.getRoles().forEach(role -> roles.add(roleDAO.getRoleByName(role.getName())));
            user.setRoles(roles);
        user.getRoles().forEach(role -> System.out.println(role.getId()+" "+role.getName()));

        dao.addUser(user);
    }

    @Override
    public void deleteById(Long id) {
        dao.removeUserById(id);
    }

    @Override
    public void updateUser(User user) {
        User userById = dao.getUserById(user.getId());
        if (!userById.getRoles().equals(user.getRoles())) {
            List<Role> roles = new ArrayList<Role>();
            user.getRoles().stream().forEach(role -> roles.add(roleDAO.getRoleByName(role.getName())));
            userById.setRoles(roles);
        }
        userById.setLogin(user.getLogin());
        userById.setPassword(user.getPassword());
        userById.setRating(user.getRating());
        dao.updateUser(userById);
    }

    @Override
    public boolean checkUser(User user) {
        return dao.validateUser(user);
    }
}
