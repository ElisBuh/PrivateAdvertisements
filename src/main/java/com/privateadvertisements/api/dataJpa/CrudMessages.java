package com.privateadvertisements.api.dataJpa;

import com.privateadvertisements.model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CrudMessages extends JpaRepository<Messages, Integer> {
}
