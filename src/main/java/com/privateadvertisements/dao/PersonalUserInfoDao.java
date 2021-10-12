package com.privateadvertisements.dao;

import com.privateadvertisements.api.dao.IAbstractDao;
import com.privateadvertisements.api.dataJpa.CrudPersonalUserInfo;
import com.privateadvertisements.model.PersonalUserInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonalUserInfoDao implements IAbstractDao<PersonalUserInfo> {

    private final CrudPersonalUserInfo crudPersonalUserInfo;

    public PersonalUserInfoDao(CrudPersonalUserInfo crudPersonalUserInfo) {
        this.crudPersonalUserInfo = crudPersonalUserInfo;
    }

    @Override
    public PersonalUserInfo save(PersonalUserInfo personalUserInfo) {
        return crudPersonalUserInfo.save(personalUserInfo);
    }

    @Override
    public void delete(Integer id) {
        crudPersonalUserInfo.deleteById(id);

    }

    @Override
    public PersonalUserInfo get(Integer id) {
        return crudPersonalUserInfo.getById(id);
    }

    @Override
    public List<PersonalUserInfo> getAll() {
        return crudPersonalUserInfo.findAll();
    }
}
