package com.privateadvertisements.api.dataJpa;

import com.privateadvertisements.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CrudChat extends JpaRepository<Chat, Integer> {

    @Query("SELECT m FROM Chat m JOIN FETCH m.messages WHERE m.id = ?1")
    Chat getWithMessages(int id);
}
