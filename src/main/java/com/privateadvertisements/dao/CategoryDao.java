package com.privateadvertisements.dao;

import com.privateadvertisements.api.dao.IAbstractDao;
import com.privateadvertisements.api.dataJpa.CrudCategory;
import com.privateadvertisements.model.Category;

import java.util.List;

public class CategoryDao implements IAbstractDao<Category> {

    private final CrudCategory crudCategory;

    public CategoryDao(CrudCategory crudCategory) {
        this.crudCategory = crudCategory;
    }

    @Override
    public Category save(Category category) {
        return crudCategory.save(category);
    }

    @Override
    public void delete(Integer id) {
        crudCategory.deleteById(id);
    }

    @Override
    public Category get(Integer id) {
        return crudCategory.getById(id);
    }

    @Override
    public List<Category> getAll() {
        return crudCategory.findAll();
    }
}
