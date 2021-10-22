package com.privateadvertisements.api.repository;

import com.privateadvertisements.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CrudUser extends JpaRepository<User, Integer> {


    User getByLogin(String login);

    @Query("SELECT m FROM User m JOIN FETCH m.advertisements WHERE m.id = ?1")
    User getWithAdvertisement(int id);



}
