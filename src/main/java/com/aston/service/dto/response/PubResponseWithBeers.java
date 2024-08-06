package com.aston.service.dto.response;

import com.aston.constant.Constant;
import com.aston.dao.entity.Address;
import com.aston.dao.entity.Pub;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.aston.constant.Constant.DATE_TIME_FORMAT;

/**
 * DTO для {@link Pub}
 *
 * @see Address
 * @see BeerResponse
 * @see Constant
 */
@Data
@AllArgsConstructor
public class PubResponseWithBeers {

    private UUID uuid;

    private String name;

    private String description;

    private Address pubAddress;

    private List<BeerResponse> beers;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime createDate;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime updateDate;
}
