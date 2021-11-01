package com.privateadvertisements.api.repository;

import com.privateadvertisements.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Integer> {

    Country getByName(String name);
}
