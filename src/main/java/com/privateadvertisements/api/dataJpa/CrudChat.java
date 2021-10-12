package com.privateadvertisements.api.dataJpa;

import com.privateadvertisements.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CrudChat extends JpaRepository<Chat, Integer> {
}
