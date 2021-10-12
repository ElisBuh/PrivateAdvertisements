package com.privateadvertisements.api.dataJpa;

import com.privateadvertisements.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CrudAdvertisement extends JpaRepository<Advertisement, Integer> {

    @Query("SELECT m from Advertisement m WHERE m.user.id=:userId AND m.datePublication >= :startDate AND m.datePublication < :endDate ORDER BY m.datePublication DESC")
    List<Advertisement> getBetweenHalfOpen(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") int userId);

    @Query("SELECT m from Advertisement m WHERE m.datePublication >= :startDate AND m.datePublication < :endDate ORDER BY m.datePublication DESC")
    List<Advertisement> getAllBetweenHalfOpen(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT m FROM Advertisement m JOIN FETCH m.comments")
    Advertisement getWithComments();

    @Modifying
    @Query("DELETE FROM Advertisement m WHERE m.id=:id AND m.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    Advertisement getByTitle(String title);
}
