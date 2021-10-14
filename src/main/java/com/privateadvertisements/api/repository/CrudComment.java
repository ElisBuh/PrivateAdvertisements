package com.privateadvertisements.api.repository;

import com.privateadvertisements.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudComment extends JpaRepository<Comment, Integer> {
}
