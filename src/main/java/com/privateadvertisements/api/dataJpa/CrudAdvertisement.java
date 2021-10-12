package com.privateadvertisements.api.dataJpa;

import com.privateadvertisements.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudAdvertisement extends JpaRepository<Advertisement, Integer> {
}
