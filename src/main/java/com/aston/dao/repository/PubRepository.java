package com.aston.dao.repository;

import com.aston.dao.entity.Pub;
import com.aston.dao.repository.impl.PubRepositoryImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Интерфейс репозитория для работы с {@link Pub}
 *
 * @see PubRepositoryImpl
 */
public interface PubRepository {

    Optional<Pub> findByUuid(UUID uuid);

    List<Pub> findAll();

    List<Pub> findAllByBeerUuid(UUID beerUuid);

    Pub create(Pub pab);

    Pub update(UUID uuid, Pub pab);

    void deleteByUuid(UUID uuid);
}
