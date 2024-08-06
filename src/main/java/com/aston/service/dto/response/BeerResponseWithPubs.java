package com.aston.service.dto.response;

import com.aston.constant.Constant;
import com.aston.dao.entity.Address;
import com.aston.dao.entity.Beer;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.aston.constant.Constant.DATE_TIME_FORMAT;

/**
 * DTO для {@link Beer}
 *
 * @see Address
 * @see PubResponse
 * @see Constant
 */
@Data
@AllArgsConstructor
public class BeerResponseWithPubs {

    private UUID uuid;

    private String name;

    private String description;

    private String factory;

    private Address factoryAddress;

    private Double ABV;

    private Double OG;

    private Integer IBU;

    private List<PubResponse> pubs;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime createDate;

    @JsonFormat(pattern = DATE_TIME_FORMAT)
    private LocalDateTime updateDate;
}
