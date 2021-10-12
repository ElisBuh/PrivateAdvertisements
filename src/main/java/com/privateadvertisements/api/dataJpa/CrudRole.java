package com.privateadvertisements.api.dataJpa;

import com.privateadvertisements.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CrudRole extends JpaRepository<Role, Integer> {
}
