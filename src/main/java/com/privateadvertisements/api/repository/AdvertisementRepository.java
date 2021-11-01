package com.privateadvertisements.api.repository;

import com.privateadvertisements.model.Advertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AdvertisementRepository extends JpaRepository<Advertisement, Integer> {

    @Query("SELECT a from Advertisement a WHERE a.user.id=:userId AND a.datePublication >= :startDate AND a.datePublication < :endDate ORDER BY a.datePublication DESC")
    Page<Advertisement> getBetweenHalfOpenOfUser(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, @Param("userId") int userId, Pageable pageable);

    @Query("SELECT a from Advertisement a WHERE a.datePublication >= :startDate AND a.datePublication < :endDate ORDER BY a.datePublication DESC")
    Page<Advertisement> getAllBetweenHalfOpen(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);

    @Query("SELECT a from Advertisement a WHERE a.user.id=:userId")
    List<Advertisement> getAllByUserId(@Param("userId") int userId);

    @Query("SELECT a FROM Advertisement a JOIN FETCH a.comments")
    Advertisement getWithComments();

    @Modifying
    @Query("DELETE FROM Advertisement a WHERE a.id=:id AND a.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    Advertisement getByTitle(String title);
}
