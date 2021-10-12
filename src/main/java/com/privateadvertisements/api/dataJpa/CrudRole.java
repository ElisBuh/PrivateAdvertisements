package com.privateadvertisements.api.dataJpa;

import com.privateadvertisements.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CrudRole extends JpaRepository<Role, Integer> {
}
