package com.aston.dao.repository;

import com.aston.dao.entity.Beer;
import com.aston.dao.repository.impl.BeerRepositoryImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Интерфейс репозитория для работы с {@link Beer}
 *
 * @see BeerRepositoryImpl
 */
public interface BeerRepository {

    Optional<Beer> findByUuid(UUID uuid);

    List<Beer> findAll();

    List<Beer> findAllByPubUuid(UUID pubUuid);

    Beer create(Beer beer);

    Beer update(UUID uuid, Beer beer);

    void deleteByUuid(UUID uuid);
}
