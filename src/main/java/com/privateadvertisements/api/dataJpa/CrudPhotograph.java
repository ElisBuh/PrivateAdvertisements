package com.privateadvertisements.api.dataJpa;

import com.privateadvertisements.model.Photograph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CrudPhotograph extends JpaRepository<Photograph, Integer> {
}
