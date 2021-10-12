package com.privateadvertisements.api.dataJpa;

import com.privateadvertisements.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CrudCategory extends JpaRepository<Category, Integer> {
}
