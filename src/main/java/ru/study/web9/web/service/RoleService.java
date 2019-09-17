package ru.study.web9.web.service;


import ru.study.web9.web.model.Role;

import java.util.List;

public interface RoleService {

    List<Role> getAllRoles();

    void addRole(Role role) ;

    Role getRoleById(long id) ;

    Role getRoleByName(String name) ;

    void deleteById(Long id);

    void updateRole(Role role);


}
