package com.privateadvertisements.api.repository;

import com.privateadvertisements.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT c from Comment c WHERE c.advertisement.id=?1 ORDER BY c.dateCreate DESC ")
    List<Comment> getAllCommentByAd(int adId);
}
