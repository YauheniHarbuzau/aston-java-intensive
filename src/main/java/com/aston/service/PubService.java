package com.aston.service;

import com.aston.dao.entity.Pub;
import com.aston.service.dto.request.PubRequest;
import com.aston.service.dto.response.PubResponse;
import com.aston.service.dto.response.PubResponseWithBeers;
import com.aston.service.impl.PubServiceImpl;

import java.util.List;
import java.util.UUID;

/**
 * Интрерфейс сервиса для работы с {@link Pub}, {@link PubRequest}, {@link PubResponse}, {@link PubResponseWithBeers}
 *
 * @see PubServiceImpl
 */
public interface PubService {

    PubResponse getByUuid(UUID uuid);

    PubResponseWithBeers getByUuidWithBeers(UUID uuid);

    List<PubResponse> getAll();

    PubResponse create(PubRequest pubRequest);

    PubResponse update(UUID uuid, PubRequest pubRequest);

    void deleteByUuid(UUID uuid);
}
