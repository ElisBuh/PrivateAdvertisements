package com.privateadvertisements.api.dataJpa;

import com.privateadvertisements.model.Photograph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudPhotograph extends JpaRepository<Photograph, Integer> {
}