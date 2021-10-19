package com.privateadvertisements.api.service;

import com.privateadvertisements.model.Advertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface IAdvertisementService {

    Advertisement save(Advertisement advertisement, Integer userId, String name);

    void delete(Integer id, Integer userId);

    Advertisement get(Integer id);

    Page<Advertisement> getAllPagesAndSort(Pageable pageable);

    Advertisement findAdvertisementByTitle(String title);

    Page<Advertisement> getAllBetweenHalfOpen(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    Page<Advertisement> getBetweenHalfOpenOfUser(Integer userId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
