package com.privateadvertisements.dao;

import com.privateadvertisements.api.dao.IAbstractDao;
import com.privateadvertisements.api.dataJpa.CrudAdvertisement;
import com.privateadvertisements.model.Advertisement;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdvertisementDao implements IAbstractDao<Advertisement> {

    private final CrudAdvertisement crudAdvertisement;

    public AdvertisementDao(CrudAdvertisement crudAdvertisement) {
        this.crudAdvertisement = crudAdvertisement;
    }

    @Override
    public Advertisement save(Advertisement advertisement) {
        return crudAdvertisement.save(advertisement);
    }

    @Override
    public void delete(Integer id) {
        crudAdvertisement.deleteById(id);

    }

    @Override
    public Advertisement get(Integer id) {
        return crudAdvertisement.getById(id);
    }

    @Override
    public List<Advertisement> getAll() {
        return crudAdvertisement.findAll();
    }
}
