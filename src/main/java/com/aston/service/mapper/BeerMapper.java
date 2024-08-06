package com.aston.service.mapper;

import com.aston.dao.entity.Beer;
import com.aston.service.dto.request.BeerRequest;
import com.aston.service.dto.response.BeerResponse;
import com.aston.service.dto.response.BeerResponseWithPubs;
import com.aston.service.impl.BeerServiceImpl;
import com.aston.service.impl.PubServiceImpl;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * Конвертер для работы с {@link Beer}, {@link BeerRequest}, {@link BeerResponse}, {@link BeerResponseWithPubs}
 *
 * @see BeerServiceImpl
 * @see PubServiceImpl
 */
@Mapper
public interface BeerMapper {

    BeerResponse toBeerResponse(Beer beer);

    BeerResponseWithPubs toBeerResponseWithPubs(Beer beer);

    Beer toBeer(BeerRequest beerRequest);

    Beer updateBeer(@MappingTarget Beer beer, BeerRequest beerRequest);
}
