package com.privateadvertisements.dao;

import com.privateadvertisements.api.dao.IAbstractDao;
import com.privateadvertisements.api.dataJpa.CrudPhotograph;
import com.privateadvertisements.model.Photograph;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PhotographDao implements IAbstractDao<Photograph> {

    private final CrudPhotograph crudPhotograph;

    public PhotographDao(CrudPhotograph crudPhotograph) {
        this.crudPhotograph = crudPhotograph;
    }

    @Override
    public Photograph save(Photograph photograph) {
        return crudPhotograph.save(photograph);
    }

    @Override
    public void delete(Integer id) {
        crudPhotograph.deleteById(id);
    }

    @Override
    public Photograph get(Integer id) {
        return crudPhotograph.getById(id);
    }

    @Override
    public List<Photograph> getAll() {
        return crudPhotograph.findAll();
    }
}
