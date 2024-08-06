package com.aston.service.dto.request;

import com.aston.dao.entity.Address;
import com.aston.dao.entity.Beer;
import lombok.Builder;

import java.io.Serializable;

/**
 * DTO для {@link Beer}
 *
 * @see Address
 */
public record BeerRequest(

        String name,
        String description,
        String factory,
        Address factoryAddress,
        Double ABV,
        Double OG,
        Integer IBU
) implements Serializable {
    @Builder public BeerRequest {}
}
