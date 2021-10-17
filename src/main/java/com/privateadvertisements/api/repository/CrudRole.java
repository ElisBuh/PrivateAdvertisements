package com.privateadvertisements.api.repository;

import com.privateadvertisements.model.Role;
import com.privateadvertisements.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CrudRole extends JpaRepository<Role, Integer> {

    Role getByName(String name);
}
