package com.aston.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сущность Beer
 *
 * @see Address
 */
@Data
@Builder
@AllArgsConstructor
@FieldNameConstants
public class Beer {

    private UUID uuid;
    private String name;
    private String description;
    private String factory;
    private Address factoryAddress;
    private Double ABV; // Alcohol By Volume
    private Double OG; // Original Gravity
    private Integer IBU; // International Bitterness Unit
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
