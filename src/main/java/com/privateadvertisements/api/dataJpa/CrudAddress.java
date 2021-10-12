package com.privateadvertisements.api.dataJpa;

import com.privateadvertisements.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CrudAddress extends JpaRepository<Address, Integer> {
}
