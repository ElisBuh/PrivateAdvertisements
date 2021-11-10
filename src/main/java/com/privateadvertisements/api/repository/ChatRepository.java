package com.privateadvertisements.api.repository;

import com.privateadvertisements.model.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface ChatRepository extends JpaRepository<Chat, Integer> {

    @Query("SELECT c FROM Chat c JOIN FETCH c.messages WHERE c.id = ?1")
    Optional<Chat> getWithMessages(int id);

    @Modifying
    @Query("DELETE FROM Chat c WHERE c.id=?1")
    int delete(int id);
}
