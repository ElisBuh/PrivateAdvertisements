package com.privateadvertisements.service;

import com.privateadvertisements.api.repository.CrudAdvertisement;
import com.privateadvertisements.api.service.IAdvertisementService;
import com.privateadvertisements.exception.NotEntityException;
import com.privateadvertisements.model.Advertisement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class AdvertisementService implements IAdvertisementService {
    private static final Logger log = LoggerFactory.getLogger(AdvertisementService.class);

    private final CrudAdvertisement advertisementRepository;

    public AdvertisementService(CrudAdvertisement advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    @Override
    public Advertisement save(Advertisement advertisement) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Advertisement get(Integer id) {
        log.info("get adv id: {}", id);
        try {
            Advertisement advertisement = advertisementRepository.getById(id);
            System.out.println(advertisement);
            return advertisement;
        } catch (EntityNotFoundException e) {
            log.error("Такого ид нет {}", id);
            throw new NotEntityException("Такого ид нет: " + id);
        }

    }

    @Override
    public Page<Advertisement> getAllPagesAndSort(Pageable pageable) {
        return advertisementRepository.findAll(pageable);
    }
}
