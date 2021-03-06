package com.privateadvertisements.api.repository;

import com.privateadvertisements.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {


    User getByLogin(String login);

    @Query("SELECT u FROM User u JOIN FETCH u.advertisements WHERE u.id = ?1")
    User getWithAdvertisement(int id);

    @Modifying
    @Query("DELETE FROM User u WHERE u.id=?1")
    int delete(int id);


}
