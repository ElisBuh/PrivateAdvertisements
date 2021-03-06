package com.privateadvertisements.service;

import com.privateadvertisements.api.repository.AdvertisementRepository;
import com.privateadvertisements.api.repository.CategoryRepository;
import com.privateadvertisements.api.repository.CommentRepository;
import com.privateadvertisements.api.repository.PhotographRepository;
import com.privateadvertisements.api.repository.UserRepository;
import com.privateadvertisements.api.service.IAdvertisementService;
import com.privateadvertisements.exception.NotEntityException;
import com.privateadvertisements.exception.StorageException;
import com.privateadvertisements.model.Advertisement;
import com.privateadvertisements.model.Category;
import com.privateadvertisements.model.Comment;
import com.privateadvertisements.model.Photograph;
import com.privateadvertisements.model.StatusAd;
import com.privateadvertisements.model.User;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AdvertisementService implements IAdvertisementService {
    private static final Logger log = LoggerFactory.getLogger(AdvertisementService.class);

    private final AdvertisementRepository advertisementRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    private final PhotographRepository photographRepository;

    @Value("${upload.path}")
    private String uploadPath = "upload-dir";


    @Override
    public Advertisement save(Advertisement advertisement, Integer userId, String nameCategory) {
        try {
            log.info("save Ad {}, by userId: {} to category: {}", advertisement.getTitle(), userId, nameCategory);
            User user = userRepository.getById(userId);
            advertisement.setUser(user);
            Category category = categoryRepository.getByCategory(nameCategory);
            advertisement.setCategory(category);
            advertisement.setDatePublication(LocalDate.now());
            advertisement.setDatePublicationOff(LocalDate.now().plusMonths(3));
            advertisement.setStatusAd(StatusAd.NEW);
            advertisement.setTopRating(false);
            return advertisementRepository.save(advertisement);
        } catch (EntityNotFoundException e) {
            log.error("???????????? ???? ?????? {}", userId);
            throw new NotEntityException("???????????? ???? ??????");
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
            log.error("???????????? ???? ?????? {}", id);
            throw new NotEntityException("???????????? ???? ??????: " + id);
        }
    }

    @Override
    public void delete(Integer id, Integer userId) {
        log.info("Delete adv id: {}", id);
        if (advertisementRepository.delete(id, userId) == 0) {
            log.error("???? ???????????? ???????????? idAd: {}, userId: {}", id, userId);
            throw new NotEntityException("???? ???????????? ????????????");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Advertisement get(Integer id) {
        try {
            log.info("get adv id: {}", id);
            Advertisement advertisement = advertisementRepository.getById(id);
            log.info(advertisement.getTitle());
            return advertisement;
        } catch (EntityNotFoundException e) {
            log.error("???????????? ???? ?????? {}", id);
            throw new NotEntityException("???????????? ???? ??????: " + id);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Advertisement findAdvertisementByTitle(String title) {
        try {
            log.info("find by title: {}", title);
            return advertisementRepository.getByTitle(title);
        } catch (EntityNotFoundException e) {
            log.error("???????????? ???????????????????? ?????? {}", title);
            throw new NotEntityException("???????????? ???????????????????? ??????: " + title);
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
            log.error("???????????? ?????????????? ???????????? adId: {}, userId: {}", adId, userId);
            throw new NotEntityException("???????????? ?????????????? ???????????? adId: " + adId + " userId: " + userId);
        }
    }

    public List<Comment> readCommentsOfAd(Integer adId) {
        log.info("readCommentsOfAd id: {}", adId);
        return commentRepository.getAllCommentByAd(adId);
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
            log.error("???????????? ???? ?????? {}", id);
            throw new NotEntityException("???????????? ???? ??????: " + id);
        }
    }

    @Override
    public Advertisement addPhoto(Integer adId, MultipartFile file) {
        log.info("add photo to adId: {}", adId);
        Advertisement advertisement = get(adId);
        Path rootLocation = Paths.get(uploadPath);
        if (!Files.exists(rootLocation)) {
            try {
                Files.createDirectories(rootLocation);
            } catch (IOException e) {
                throw new StorageException("Could not initialize storage", e);
            }
        }
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            Path destinationFile = rootLocation.resolve(
                            Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
            Photograph photograph = new Photograph();
            photograph.setPath(destinationFile.toString());
            photograph.setAdvertisement(advertisement);
            List<Photograph> photographList = advertisement.getPhotographs();
            photographList.add(photographRepository.save(photograph));
            advertisement.setPhotographs(photographList);
            advertisementRepository.save(advertisement);
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
        return null;
    }

//    public Path load(String filename) {
//        return rootLocation.resolve(filename);
//    }

    @Override
    @Transactional(readOnly = true)
    public Page<Advertisement> getAllBetweenHalfOpen(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        log.info("dateStart: {}, dateEnd: {}", startDate, endDate);
        return advertisementRepository.getAllBetweenHalfOpen(startDate, endDate, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Advertisement> getBetweenHalfOpenOfUser(Integer userId, LocalDate startDate, LocalDate endDate, Pageable pageable) {
        log.info("GetAll by userId: {}", userId);
        return advertisementRepository.getBetweenHalfOpenOfUser(startDate, endDate, userId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
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
                all++;
                if (a.getStatusAd().equals(StatusAd.OVERDUE)) {
                    overdue++;
                }
            }
        }
        float rating = (all - overdue);
        rating = rating / all * 100;
        User user = userRepository.getById(userId);
        user.setRating(Math.round(rating));
        userRepository.save(user);
    }

    @Scheduled(cron = "${interval-in-cron-ad-date-off}")
    public void checkAdOnDateOff() {
        log.info("checkAdOnDateOff");
        List<Advertisement> advertisementList = advertisementRepository.findAll();
        for (Advertisement advertisement : advertisementList) {
            if (advertisement.getDatePublicationOff() != null) {
                if (advertisement.getDatePublicationOff().isBefore(LocalDate.now()) & advertisement.getStatusAd().equals(StatusAd.NEW)) {
                    log.info("change status Ad {}, on {}", advertisement.getId(), StatusAd.OVERDUE);
                    advertisement.setStatusAd(StatusAd.OVERDUE);
                    advertisementRepository.save(advertisement);
                }
            }
            if (advertisement.getDateTopOff() != null) {
                if (advertisement.getDateTopOff().isBefore(LocalDateTime.now()) & advertisement.getTopRating()) {
                    log.info("change offTop Ad {}", advertisement.getId());
                    advertisement.setTopRating(false);
                    advertisementRepository.save(advertisement);
                }
            }
        }
    }


}
