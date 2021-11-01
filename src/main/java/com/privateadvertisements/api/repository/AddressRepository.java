package com.privateadvertisements.api.repository;

import com.privateadvertisements.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AddressRepository extends JpaRepository<Address, Integer> {
}
