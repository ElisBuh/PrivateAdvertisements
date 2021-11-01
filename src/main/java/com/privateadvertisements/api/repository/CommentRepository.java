package com.privateadvertisements.api.repository;

import com.privateadvertisements.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT c from Comment c WHERE c.advertisement.id=:adId ORDER BY c.dateCreate DESC ")
    List<Comment> getAllCommentByAd(@Param("adId") int adId);
}
