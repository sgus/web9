package ru.study.web9.web.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.study.web9.web.dao.RoleDAO;
import ru.study.web9.web.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

@Repository("roleDAO")
@Transactional
public class RoleDAOImpl implements RoleDAO {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Role> getAllRoles() {
        List query = entityManager.createQuery("SELECT u FROM Role u", Role.class).getResultList();
        return query;
    }

    @Override
    public void addRole(Role role) {

    }

    @Override
    public Role getRoleById(long id) {
        return entityManager.find(Role.class, id);
    }

    @Override
    public Role getRoleByName(String name) {
        Query query = entityManager.createQuery("from Role where name = :name");
        query.setParameter("name", name);
        return (Role) query.getResultList().get(0);

    }

    @Override
    public void removeRoleById(Long id) {

    }

    @Override
    public void updateRole(Role role) {

    }
}
