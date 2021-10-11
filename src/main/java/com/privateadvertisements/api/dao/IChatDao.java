package com.privateadvertisements.api.dao;

import com.privateadvertisements.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IChatDao extends JpaRepository<Chat, Integer> {
}
