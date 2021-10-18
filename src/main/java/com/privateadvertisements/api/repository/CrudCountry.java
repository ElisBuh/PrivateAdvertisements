package com.privateadvertisements.api.repository;

import com.privateadvertisements.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudCountry extends JpaRepository<Country, Integer> {

    Country getByName(String name);
}
