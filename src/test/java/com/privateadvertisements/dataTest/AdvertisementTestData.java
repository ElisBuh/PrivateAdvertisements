package com.privateadvertisements.dataTest;

import com.privateadvertisements.model.Advertisement;
import com.privateadvertisements.model.StatusAd;
import org.hibernate.type.LocalDateType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class AdvertisementTestData {
    public static final Advertisement ADVERTISEMENT_1 = new Advertisement(100002,
            UserTestData.USER_1,
            List.of(CommentTestData.COMMENT_1),
            CategoryTestData.CATEGORY_1,
            "Ноутбук",
            "Хороший ноутбук, черный",
            new BigDecimal("1268.25"),
            LocalDate.of(2021,10,12),
            null,
            StatusAd.NEW,
            false,
            null,
            List.of(PhotographTestData.PHOTOGRAPH_1, PhotographTestData.PHOTOGRAPH_3));


    public static final Advertisement ADVERTISEMENT_2 = new Advertisement(100003,
            UserTestData.USER_2,
            List.of(CommentTestData.COMMENT_2),
            CategoryTestData.CATEGORY_2,
            "Война и Мир",
            "Интересное чтиво",
            new BigDecimal("10.18"),
            LocalDate.of(2021,10,12),
            null,
            StatusAd.NEW,
            false,
            null,
            List.of(PhotographTestData.PHOTOGRAPH_2, PhotographTestData.PHOTOGRAPH_4));
}
