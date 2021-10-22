package com.privateadvertisements.api.repository;

import com.privateadvertisements.model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface CrudMessages extends JpaRepository<Messages, Integer> {
}
