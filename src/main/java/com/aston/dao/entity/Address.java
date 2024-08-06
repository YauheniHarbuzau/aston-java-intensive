package com.aston.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

/**
 * Сущность Address
 *
 * @see Pub
 * @see Beer
 */
@Data
@Builder
@AllArgsConstructor
@FieldNameConstants
public class Address {

    private String country;
    private String region;
    private String city;
    private String street;
    private String number;
}
