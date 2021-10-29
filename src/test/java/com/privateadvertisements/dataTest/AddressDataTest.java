package com.privateadvertisements.dataTest;

import com.privateadvertisements.model.Address;

public class AddressDataTest {

    public static final Address ADDRESS_1 = new Address(100000,
            CountryTestData.COUNTRY_1,
            CityTestData.CITY_1,
            23001,
            "Пушкина",
            12,
            39);
    public static final Address ADDRESS_2 = new Address(100001,
            CountryTestData.COUNTRY_1,
            CityTestData.CITY_2,
            23001,
            "Ленина",
            12,
            39);


    public static final Address NEW_ADDRESS = new Address(
            CountryTestData.COUNTRY_1,
            CityTestData.NEW_CITY,
            10000,
            "Тест",
            100,
            100);
}
