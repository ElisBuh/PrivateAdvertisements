package com.privateadvertisements.api.repository;

import com.privateadvertisements.model.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudCity extends JpaRepository<City, Integer> {

    City getByName(String name);
}
