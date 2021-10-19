package com.privateadvertisements.service;

import com.privateadvertisements.api.repository.CrudAdvertisement;
import com.privateadvertisements.api.repository.CrudCategory;
import com.privateadvertisements.api.repository.CrudUser;
import com.privateadvertisements.api.service.IAdvertisementService;
import com.privateadvertisements.exception.NotEntityException;
import com.privateadvertisements.model.Advertisement;
import com.privateadvertisements.model.Category;
import com.privateadvertisements.model.StatusAd;
import com.privateadvertisements.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;

@Service
public class AdvertisementService implements IAdvertisementService {
    private static final Logger log = LoggerFactory.getLogger(AdvertisementService.class);

    private final CrudAdvertisement advertisementRepository;
    private final CrudUser userRepository;
    private final CrudCategory categoryRepository;

    public AdvertisementService(CrudAdvertisement advertisementRepository, CrudUser userRepository, CrudCategory categoryRepository) {
        this.advertisementRepository = advertisementRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Advertisement save(Advertisement advertisement, Integer userId, String name) {
        try {
            User user = userRepository.getById(userId);
            advertisement.setUser(user);
            Category category = categoryRepository.getByCategory(name);
            advertisement.setCategory(category);
            advertisement.setDatePublication(LocalDateTime.now());
            advertisement.setDatePublicationOff(LocalDateTime.now().plusMonths(3));
            advertisement.setStatusAd(StatusAd.NEW);
            System.out.println(advertisement);
            return advertisementRepository.save(advertisement);
        } catch (EntityNotFoundException e) {
            log.error("Такого ид нет {}", userId);
            throw new NotEntityException("Такого ид нет");
        }
    }

    @Override
    public void delete(Integer id, Integer userId) {
        log.info("Delete adv id: {}", id);
        if (advertisementRepository.delete(id, userId) == 0) {
            log.error("Не верные данные idAd: {}, userId: {}", id, userId);
            throw new NotEntityException("Не верные данные");
        }


    }

    @Override
    public Advertisement get(Integer id) {
        log.info("get adv id: {}", id);
        try {
            Advertisement advertisement = advertisementRepository.getById(id);
            log.info(advertisement.getTitle());
            return advertisement;
        } catch (EntityNotFoundException e) {
            log.error("Такого ид нет {}", id);
            throw new NotEntityException("Такого ид нет: " + id);
        }
    }

    @Override
    public Advertisement findAdvertisementByTitle(String title) {
        log.info("find by title: {}", title);
        try {
            return advertisementRepository.getByTitle(title);
        } catch (EntityNotFoundException e) {
            log.error("Такого объявления нет {}", title);
            throw new NotEntityException("Такого объявления нет: " + title);
        }
    }

    @Override
    public Page<Advertisement> getAllBetweenHalfOpen(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return advertisementRepository.getAllBetweenHalfOpen(startDate, endDate, pageable);
    }

    @Override
    public Page<Advertisement> getBetweenHalfOpenOfUser(Integer userId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return advertisementRepository.getBetweenHalfOpenOfUser(startDate, endDate, userId, pageable);
    }

    @Override
    public Page<Advertisement> getAllPagesAndSort(Pageable pageable) {
        return advertisementRepository.findAll(pageable);
    }
}
