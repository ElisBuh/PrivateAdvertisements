package com.privateadvertisements.api.repository;

import com.privateadvertisements.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CrudUser extends JpaRepository<User, Integer> {


    User getByLogin(String login);

    @Query("SELECT m FROM User m JOIN FETCH m.advertisements WHERE m.id = ?1")
    User getWithAdvertisement(int id);

    @Modifying
    @Transactional
    @Query("DELETE FROM User u WHERE u.id=:id")
    int delete(@Param("id") int id);


}
