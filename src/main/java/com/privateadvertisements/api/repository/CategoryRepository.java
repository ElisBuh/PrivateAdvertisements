package com.privateadvertisements.api.repository;

import com.privateadvertisements.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Category getByCategory(String category);
}
