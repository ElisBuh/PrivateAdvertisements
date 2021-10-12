package com.privateadvertisements.api.dataJpa;

import com.privateadvertisements.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CrudAddress extends JpaRepository<Address, Integer> {
}
