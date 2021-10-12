package com.privateadvertisements.dao;

import com.privateadvertisements.api.dao.IAbstractDao;
import com.privateadvertisements.api.dataJpa.CrudRole;
import com.privateadvertisements.model.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleDao implements IAbstractDao<Role> {

    private final CrudRole crudRole;

    public RoleDao(CrudRole crudRole) {
        this.crudRole = crudRole;
    }

    @Override
    public Role save(Role role) {
        return crudRole.save(role);
    }

    @Override
    public void delete(Integer id) {
        crudRole.deleteById(id);
    }

    @Override
    public Role get(Integer id) {
        return crudRole.getById(id);
    }

    @Override
    public List<Role> getAll() {
        return crudRole.findAll();
    }
}
