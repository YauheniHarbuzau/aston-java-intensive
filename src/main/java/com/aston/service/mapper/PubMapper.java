package com.aston.service.mapper;

import com.aston.dao.entity.Pub;
import com.aston.service.dto.request.PubRequest;
import com.aston.service.dto.response.PubResponse;
import com.aston.service.dto.response.PubResponseWithBeers;
import com.aston.service.impl.BeerServiceImpl;
import com.aston.service.impl.PubServiceImpl;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Конвертер для работы с {@link Pub}, {@link PubRequest}, {@link PubResponse}, {@link PubResponseWithBeers}
 *
 * @see PubServiceImpl
 * @see BeerServiceImpl
 */
@Mapper
public interface PubMapper {

    PubResponse toPubResponse(Pub pub);

    PubResponseWithBeers toPubResponseWithBeers(Pub pub);

    Pub toPub(PubRequest pubRequest);

    Pub updatePub(@MappingTarget Pub pub, PubRequest pubRequest);
}
