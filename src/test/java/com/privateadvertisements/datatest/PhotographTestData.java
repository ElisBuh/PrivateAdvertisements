package com.privateadvertisements.datatest;

import com.privateadvertisements.model.Photograph;

public class PhotographTestData {
    public static final Photograph PHOTOGRAPH_1 = new Photograph(100004,
            "foto/Schenker_VIA14_Laptop_asv2021-01.jpg",
            AdvertisementTestData.ADVERTISEMENT_1);
    public static final Photograph PHOTOGRAPH_2 = new Photograph(100005,
            "foto/220px-Толстой_Л._Н._Война_и_мир,_Т._1._Обложка_изд.1912г,Россия.jpg",
            AdvertisementTestData.ADVERTISEMENT_2);
    public static final Photograph PHOTOGRAPH_3 = new Photograph(100006,
            "foto/Schenker_VIA14_Laptop_asv2021-01.jpg",
            AdvertisementTestData.ADVERTISEMENT_1);
    public static final Photograph PHOTOGRAPH_4 = new Photograph(100007,
            "foto/220px-Толстой_Л._Н._Война_и_мир,_Т._1._Обложка_изд.1912г,Россия.jpg",
            AdvertisementTestData.ADVERTISEMENT_2);

    public static final Photograph NEW_PHOTOGRAPH = new Photograph(100010, "Test", AdvertisementTestData.ADVERTISEMENT_1);
}
