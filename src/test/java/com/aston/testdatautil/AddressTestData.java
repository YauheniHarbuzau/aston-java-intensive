package com.aston.testdatautil;

import com.aston.dao.entity.Address;
import lombok.Builder;
import lombok.Data;

/**
 * Класс для предоставления тестовых данных
 */
@Data
@Builder(setterPrefix = "with")
public class AddressTestData {

    @Builder.Default
    private String country = "COUNTRY";

    @Builder.Default
    private String region = "REGION";

    @Builder.Default
    private String city = "CITY";

    @Builder.Default
    private String street = "STREET";

    @Builder.Default
    private String number = "NUMBER";

    public Address buildAddress() {
        return new Address(country, region, city, street, number);
    }
}
