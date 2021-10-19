package com.privateadvertisements.api.service;

import com.privateadvertisements.model.Advertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAdvertisementService {

    Advertisement save(Advertisement advertisement);

    void delete(Integer id, Integer userId);

    Advertisement get(Integer id);

    Page<Advertisement> getAllPagesAndSort(Pageable pageable);


}
