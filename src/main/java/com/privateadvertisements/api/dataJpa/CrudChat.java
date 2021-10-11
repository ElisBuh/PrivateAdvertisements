package com.privateadvertisements.api.dataJpa;

import com.privateadvertisements.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CrudChat extends JpaRepository<Chat, Integer> {
}
