package com.privateadvertisements.api.repository;

import com.privateadvertisements.model.Advertisement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface CrudAdvertisement extends JpaRepository<Advertisement, Integer> {

    @Query("SELECT m from Advertisement m WHERE m.user.id=:userId AND m.datePublication >= :startDate AND m.datePublication < :endDate ORDER BY m.datePublication DESC")
    Page<Advertisement> getBetweenHalfOpenOfUser(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("userId") int userId, Pageable pageable);

    @Query("SELECT m from Advertisement m WHERE m.datePublication >= :startDate AND m.datePublication < :endDate ORDER BY m.datePublication DESC")
    Page<Advertisement> getAllBetweenHalfOpen(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);

    @Query("SELECT m from Advertisement m WHERE m.user.id=:userId")
    List<Advertisement> getAllByUserId(@Param("userId") int userId);

    @Query("SELECT m FROM Advertisement m JOIN FETCH m.comments")
    Advertisement getWithComments();

    @Modifying
    @Transactional
    @Query("DELETE FROM Advertisement m WHERE m.id=:id AND m.user.id=:userId")
    int delete(@Param("id") int id, @Param("userId") int userId);

    Advertisement getByTitle(String title);
}
