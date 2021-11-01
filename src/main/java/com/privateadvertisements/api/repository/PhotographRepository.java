package com.privateadvertisements.api.repository;

import com.privateadvertisements.model.Photograph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotographRepository extends JpaRepository<Photograph, Integer> {
}
