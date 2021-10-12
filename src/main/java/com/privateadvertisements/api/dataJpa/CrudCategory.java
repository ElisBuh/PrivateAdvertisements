package com.privateadvertisements.api.dataJpa;

import com.privateadvertisements.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface CrudCategory extends JpaRepository<Category, Integer> {
}
