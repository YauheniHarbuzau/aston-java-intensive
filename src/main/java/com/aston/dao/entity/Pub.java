package com.aston.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Сущность Pub
 *
 * @see Address
 */
@Data
@Builder
@AllArgsConstructor
@FieldNameConstants
public class Pub {

    private UUID uuid;
    private String name;
    private String description;
    private Address pubAddress;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
