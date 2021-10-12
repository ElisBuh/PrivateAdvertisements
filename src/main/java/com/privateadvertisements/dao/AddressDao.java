package com.privateadvertisements.dao;

import com.privateadvertisements.api.dao.IAbstractDao;
import com.privateadvertisements.api.dataJpa.CrudAddress;
import com.privateadvertisements.model.Address;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AddressDao implements IAbstractDao<Address> {

    private final CrudAddress crudAddress;

    public AddressDao(CrudAddress crudAddress) {
        this.crudAddress = crudAddress;
    }

    @Override
    public Address save(Address address) {
        return crudAddress.save(address);
    }

    @Override
    public void delete(Integer id) {
        crudAddress.deleteById(id);
    }

    @Override
    public Address get(Integer id) {
        return crudAddress.getById(id);
    }

    @Override
    public List<Address> getAll() {
        return crudAddress.findAll();
    }
}
