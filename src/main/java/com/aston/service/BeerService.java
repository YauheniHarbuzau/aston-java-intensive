package com.aston.service;

import com.aston.dao.entity.Beer;
import com.aston.service.dto.request.BeerRequest;
import com.aston.service.dto.response.BeerResponse;
import com.aston.service.dto.response.BeerResponseWithPubs;
import com.aston.service.impl.BeerServiceImpl;

import java.util.List;
import java.util.UUID;

/**
 * Интрерфейс сервиса для работы с {@link Beer}, {@link BeerRequest}, {@link BeerResponse}, {@link BeerResponseWithPubs}
 *
 * @see BeerServiceImpl
 */
public interface BeerService {

    BeerResponse getByUuid(UUID uuid);

    BeerResponseWithPubs getByUuidWithPubs(UUID uuid);

    List<BeerResponse> getAll();

    BeerResponse create(BeerRequest beerRequest);

    BeerResponse update(UUID uuid, BeerRequest beerRequest);

    void deleteByUuid(UUID uuid);
}
