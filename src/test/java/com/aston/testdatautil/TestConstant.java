package com.aston.testdatautil;

import java.time.LocalDateTime;
import java.util.UUID;

import static java.time.Month.AUGUST;

/**
 * Класс для предоставления тестовых данных
 */
public class TestConstant {

    public static final UUID TEST_PUB_UUID_1 = UUID.fromString("4addfa05-f4b7-4679-bdc0-86ac6bcd59b5");
    public static final UUID TEST_PUB_UUID_2 = UUID.fromString("6ab5a629-2fd9-4c04-9322-fb725642dda7");

    public static final UUID TEST_BEER_UUID_1 = UUID.fromString("33d81402-7202-4c70-ae5a-5a08ec2e8db4");
    public static final UUID TEST_BEER_UUID_2 = UUID.fromString("0f9e36be-a10f-470f-aebf-8fabf3d79906");

    public static final LocalDateTime TEST_DATE_TIME = LocalDateTime.of(2024, AUGUST, 4, 10, 0, 0);
}
