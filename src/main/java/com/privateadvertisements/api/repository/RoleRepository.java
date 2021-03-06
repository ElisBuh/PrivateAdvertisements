package com.privateadvertisements.api.repository;

import com.privateadvertisements.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role getByName(String name);
}
