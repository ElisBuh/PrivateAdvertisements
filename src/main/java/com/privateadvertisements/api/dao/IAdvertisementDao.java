package com.privateadvertisements.api.dao;

import com.privateadvertisements.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAdvertisementDao extends JpaRepository<Advertisement, Integer> {
}
