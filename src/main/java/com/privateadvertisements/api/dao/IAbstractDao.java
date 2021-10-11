package com.privateadvertisements.api.dao;

import com.privateadvertisements.model.AEntity;

import java.util.List;

public interface IAbstractDao<T extends AEntity> {

    T save(T t);

    void delete(Integer id);

    T get(Integer id);

    List<T> getAll();
}
