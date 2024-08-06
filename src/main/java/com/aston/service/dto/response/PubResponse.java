package com.aston.service.dto.response;

import com.aston.constant.Constant;
import com.aston.dao.entity.Address;
import com.aston.dao.entity.Pub;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.aston.constant.Constant.DATE_TIME_FORMAT;

/**
 * DTO для {@link Pub}
 *
 * @see Address
 * @see Constant
 */
public record PubResponse(

        UUID uuid,

        String name,

        String description,

        Address pubAddress,

        @JsonFormat(pattern = DATE_TIME_FORMAT)
        LocalDateTime createDate,

        @JsonFormat(pattern = DATE_TIME_FORMAT)
        LocalDateTime updateDate
) implements Serializable {
}
