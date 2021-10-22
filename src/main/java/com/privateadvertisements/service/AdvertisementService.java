package com.privateadvertisements.service;

import com.privateadvertisements.api.repository.CrudAdvertisement;
import com.privateadvertisements.api.repository.CrudCategory;
import com.privateadvertisements.api.repository.CrudComment;
import com.privateadvertisements.api.repository.CrudPhotograph;
import com.privateadvertisements.api.repository.CrudUser;
import com.privateadvertisements.api.service.IAdvertisementService;
import com.privateadvertisements.exception.NotEntityException;
import com.privateadvertisements.model.Advertisement;
import com.privateadvertisements.model.Category;
import com.privateadvertisements.model.Comment;
import com.privateadvertisements.model.Photograph;
import com.privateadvertisements.model.StatusAd;
import com.privateadvertisements.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdvertisementService implements IAdvertisementService {
    private static final Logger log = LoggerFactory.getLogger(AdvertisementService.class);

    private final CrudAdvertisement advertisementRepository;
    private final CrudUser userRepository;
    private final CrudCategory categoryRepository;
    private final CrudComment commentRepository;
    private final CrudPhotograph photographRepository;

    public AdvertisementService(CrudAdvertisement advertisementRepository,
                                CrudUser userRepository,
                                CrudCategory categoryRepository,
                                CrudComment commentRepository,
                                CrudPhotograph photographRepository) {
        this.advertisementRepository = advertisementRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.commentRepository = commentRepository;
        this.photographRepository = photographRepository;
    }

    @Override
    public Advertisement save(Advertisement advertisement, Integer userId, String nameCategory) {
        try {
            log.info("save Ad {}, by userId: {} to category: {}", advertisement.getTitle(), userId, nameCategory);
            User user = userRepository.getById(userId);
            advertisement.setUser(user);
            Category category = categoryRepository.getByCategory(nameCategory);
            advertisement.setCategory(category);
            advertisement.setDatePublication(LocalDateTime.now());
            advertisement.setDatePublicationOff(LocalDateTime.now().plusMonths(3));
            advertisement.setStatusAd(StatusAd.NEW);
            advertisement.setTopRating(false);
            System.out.println(advertisement);
            return advertisementRepository.save(advertisement);
        } catch (EntityNotFoundException e) {
            log.error("Такого ид нет {}", userId);
            throw new NotEntityException("Такого ид нет");
        }
    }

    @Override
    public Advertisement update(Advertisement advertisement, Integer id) {
        try {
            log.info("update adv id: {}", id);
            Advertisement advertisementOld = advertisementRepository.getById(id);
            log.info(advertisementOld.getTitle());
            advertisementOld.setTitle(advertisement.getTitle());
            advertisementOld.setContent(advertisement.getContent());
            advertisementOld.setCost(advertisement.getCost());
            advertisementOld.setStatusAd(advertisement.getStatusAd());
            advertisementOld = advertisementRepository.save(advertisementOld);
            if (advertisement.getStatusAd().equals(StatusAd.SOLD) || advertisement.getStatusAd().equals(StatusAd.OVERDUE)) {
                updateRating(advertisementOld.getUser().getId());
            }
            return advertisementOld;
        } catch (EntityNotFoundException e) {
            log.error("Такого ид нет {}", id);
            throw new NotEntityException("Такого ид нет: " + id);
        }
    }

    @Override
    public void delete(Integer id, Integer userId) {
        log.info("Delete adv id: {}", id);
        if (advertisementRepository.delete(id, userId) == 0) {
            log.error("Не верные данные idAd: {}, userId: {}", id, userId);
            throw new NotEntityException("Не верные данные");
        }
    }

    @Override
    public Advertisement get(Integer id) {
        try {
            log.info("get adv id: {}", id);
            Advertisement advertisement = advertisementRepository.getById(id);
            log.info(advertisement.getTitle());
            return advertisement;
        } catch (EntityNotFoundException e) {
            log.error("Такого ид нет {}", id);
            throw new NotEntityException("Такого ид нет: " + id);
        }
    }

    @Override
    public Advertisement findAdvertisementByTitle(String title) {
        try {
            log.info("find by title: {}", title);
            return advertisementRepository.getByTitle(title);
        } catch (EntityNotFoundException e) {
            log.error("Такого объявления нет {}", title);
            throw new NotEntityException("Такого объявления нет: " + title);
        }
    }

    @Override
    public Comment addComment(Comment comment, Integer adId, Integer userId) {
        try {
            log.info("Comment add to adId: {}, by userId: {}", adId, userId);
            comment.setAdvertisement(advertisementRepository.getById(adId));
            comment.setUser(userRepository.getById(userId));
            comment.setDateCreate(LocalDateTime.now());
            return commentRepository.save(comment);
        } catch (EntityNotFoundException e) {
            log.error("Ошибка входных данных adId: {}, userId: {}", adId, userId);
            throw new NotEntityException("Ошибка входных данных adId: " + adId + " userId: " + userId);
        }
    }

    @Override
    public void topUpAdvertisement(Integer id, int day) {
        try {
            log.info("topUp ad id: {} on day: {}", id, day);
            Advertisement advertisement = advertisementRepository.getById(id);
            log.info(advertisement.getTitle());
            advertisement.setTopRating(true);
            advertisement.setDateTopOff(LocalDateTime.now().plusDays(day));
            advertisementRepository.save(advertisement);
        } catch (EntityNotFoundException e) {
            log.error("Такого ид нет {}", id);
            throw new NotEntityException("Такого ид нет: " + id);
        }
    }

    @Override
    public Advertisement addPhoto(Integer adId, String... path) {
        log.info("add photos to adId: {}", adId);
        Advertisement advertisement = get(adId);
        List<Photograph> photographList = advertisement.getPhotographs();
        for (String s : path) {
            Photograph photograph = new Photograph();
            photograph.setPath(s);
            photographList.add(photographRepository.save(photograph));
        }
        advertisement.setPhotographs(photographList);
        return advertisementRepository.save(advertisement);
    }

    @Override
    public Page<Advertisement> getAllBetweenHalfOpen(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        log.info("dateStart: {}, dateEnd: {}", startDate, endDate);
        return advertisementRepository.getAllBetweenHalfOpen(startDate, endDate, pageable);
    }

    @Override
    public Page<Advertisement> getBetweenHalfOpenOfUser(Integer userId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        log.info("GetAll by userId: {}", userId);
        return advertisementRepository.getBetweenHalfOpenOfUser(startDate, endDate, userId, pageable);
    }

    @Override
    public List<Advertisement> getAllPagesAndSort(Pageable pageable) {
        log.info("getAll sort by {}", pageable.getSort());
        return advertisementRepository.findAll(pageable).stream()
                .sorted((o1, o2) -> o2.getUser().getRating().compareTo(o1.getUser().getRating()))
                .sorted((o1, o2) -> o2.getTopRating().compareTo(o1.getTopRating()))
                .filter(advertisement -> advertisement.getStatusAd().equals(StatusAd.NEW))
                .collect(Collectors.toList());
    }


    private void updateRating(Integer userId) {
        log.info("updateRating user: {}", userId);
        List<Advertisement> advertisementList = advertisementRepository.getAllByUserId(userId);
        int overdue = 0;
        int all = 0;
        for (Advertisement a : advertisementList) {
            if (a.getStatusAd().equals(StatusAd.SOLD) || a.getStatusAd().equals(StatusAd.OVERDUE)) {
                overdue++;
                all++;
            }
        }
        float rating = (all - overdue);
        rating = rating / all * 100;
        User user = userRepository.getById(userId);
        user.setRating(Math.round(rating));
        userRepository.save(user);
    }
}
