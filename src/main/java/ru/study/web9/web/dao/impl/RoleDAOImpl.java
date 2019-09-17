package ru.study.web9.web.dao.impl;

import org.springframework.stereotype.Repository;
import ru.study.web9.web.dao.RoleDAO;
import ru.study.web9.web.model.Role;

import javax.transaction.Transactional;
import java.util.List;

@Repository("roleDAO")
@Transactional
public class RoleDAOImpl implements RoleDAO {
    @Override
    public List<Role> getAllRoles() {
        return null;
    }

    @Override
    public void addRole(Role role) {

    }

    @Override
    public Role getRoleById(long id) {
        return null;
    }

    @Override
    public Role getRoleByName(String name) {
        return null;
    }

    @Override
    public void removeRoleById(Long id) {

    }

    @Override
    public void updateRole(Role role) {

    }
}
