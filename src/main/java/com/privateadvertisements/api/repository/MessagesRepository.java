package com.privateadvertisements.api.repository;

import com.privateadvertisements.model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MessagesRepository extends JpaRepository<Messages, Integer> {
}
