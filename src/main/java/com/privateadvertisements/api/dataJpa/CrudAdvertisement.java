package com.privateadvertisements.api.dataJpa;

import com.privateadvertisements.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CrudAdvertisement extends JpaRepository<Advertisement, Integer> {
}
