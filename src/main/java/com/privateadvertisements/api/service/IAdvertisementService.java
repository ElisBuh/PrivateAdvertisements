package com.privateadvertisements.api.service;

import com.privateadvertisements.model.Advertisement;
import com.privateadvertisements.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface IAdvertisementService {

    Advertisement save(Advertisement advertisement, Integer userId, String nameCategory);

    Advertisement update(Advertisement advertisement, Integer id);

    void delete(Integer id, Integer userId);

    Advertisement get(Integer id);

    Advertisement findAdvertisementByTitle(String title);

    void topUpAdvertisement(Integer id, int day);

    Advertisement addPhoto(Integer adId, MultipartFile file);

    Comment addComment(Comment comment, Integer adId, Integer userId);

    List<Comment> readCommentsOfAd(Integer adId);

    List<Advertisement> getAllPagesAndSort(Pageable pageable);

    Page<Advertisement> getAllBetweenHalfOpen(LocalDate startDate, LocalDate endDate, Pageable pageable);

    Page<Advertisement> getBetweenHalfOpenOfUser(Integer userId, LocalDate startDate, LocalDate endDate, Pageable pageable);
}
