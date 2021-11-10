package com.privateadvertisements.api.service;

import com.privateadvertisements.exception.NotEntityException;
import com.privateadvertisements.model.Advertisement;
import com.privateadvertisements.model.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;

import static com.privateadvertisements.datatest.AdvertisementTestData.ADVERTISEMENT_1;
import static com.privateadvertisements.datatest.AdvertisementTestData.ADVERTISEMENT_LIST;
import static com.privateadvertisements.datatest.AdvertisementTestData.NEW_ADVERTISEMENT;
import static com.privateadvertisements.datatest.AdvertisementTestData.UPDATE_ADVERTISEMENT;
import static com.privateadvertisements.datatest.CommentTestData.COMMENT_LIST_AD_1;
import static com.privateadvertisements.datatest.CommentTestData.NEW_COMMENT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-integrationTest.properties")
@Sql(scripts = "classpath:initDB_hsqldb.sql", config = @SqlConfig(encoding = "UTF-8"))
@Sql(scripts = "classpath:populateDB_hsqldb.sql", config = @SqlConfig(encoding = "UTF-8"))
public class IAdvertisementServiceTest {

    @Autowired
    IAdvertisementService advertisementService;

    @Test
    public void save() {
        Advertisement advertisement = advertisementService.save(NEW_ADVERTISEMENT, 100006, "Книги");
        assertEquals(NEW_ADVERTISEMENT, advertisement);
    }

    @Test
    public void update() {
        Advertisement advertisement = advertisementService.update(UPDATE_ADVERTISEMENT, 100002);
        assertEquals(UPDATE_ADVERTISEMENT, advertisement);
    }

    @Test
    public void delete() {
        advertisementService.delete(100002, 100006);
        assertThrows(NotEntityException.class, () -> advertisementService.get(100002));
    }

    @Test
    public void get() {
        Advertisement advertisement = advertisementService.get(100002);
        assertEquals(ADVERTISEMENT_1.toString(), advertisement.toString());
    }

    @Test
    public void findAdvertisementByTitle() {
        Advertisement advertisement = advertisementService.findAdvertisementByTitle("Ноутбук");
        assertEquals(ADVERTISEMENT_1.toString(), advertisement.toString());
    }

    @Test
    public void topUpAdvertisement() {
        advertisementService.topUpAdvertisement(100002, 5);
        Advertisement advertisement = advertisementService.get(100002);
        Advertisement advertisementUpdate = new Advertisement(ADVERTISEMENT_1);
        advertisementUpdate.setDateTopOff(LocalDateTime.now().plusDays(5));
        advertisementUpdate.setTopRating(true);
        assertEquals(advertisementUpdate.toString(), advertisement.toString());

    }

//    @Test
//    public void addPhoto() {
//        Advertisement advertisement = advertisementService.addPhoto(100002, "Test");
//        Advertisement advertisementUpdate = new Advertisement(ADVERTISEMENT_1);
//        advertisementUpdate.setPhotographs(List.of(PHOTOGRAPH_1, PHOTOGRAPH_3, NEW_PHOTOGRAPH));
//        assertEquals(advertisementUpdate.getPhotographs(), advertisement.getPhotographs());
//    }

    @Test
    public void addComment() {
        Comment comment = advertisementService.addComment(NEW_COMMENT, 100002, 100006);
        assertEquals(NEW_COMMENT, comment);
    }

    @Test
    public void readCommentsOfAd() {
        List<Comment> commentList = advertisementService.readCommentsOfAd(100002);
        assertEquals(COMMENT_LIST_AD_1, commentList);
    }

    @Test
    public void getAllPagesAndSort() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("title"));
        List<Advertisement> advertisementList = advertisementService.getAllPagesAndSort(pageable);
        assertEquals(ADVERTISEMENT_LIST, advertisementList);
    }

}