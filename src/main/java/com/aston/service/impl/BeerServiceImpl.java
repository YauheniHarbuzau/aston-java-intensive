package com.aston.service.impl;

import com.aston.dao.entity.Beer;
import com.aston.dao.repository.BeerRepository;
import com.aston.dao.repository.PubRepository;
import com.aston.dao.repository.impl.BeerRepositoryImpl;
import com.aston.dao.repository.impl.PubRepositoryImpl;
import com.aston.exception.EntityNotFoundException;
import com.aston.service.BeerService;
import com.aston.service.dto.request.BeerRequest;
import com.aston.service.dto.response.BeerResponse;
import com.aston.service.dto.response.BeerResponseWithPubs;
import com.aston.service.mapper.BeerMapper;
import com.aston.service.mapper.BeerMapperImpl;
import com.aston.service.mapper.PubMapper;
import com.aston.service.mapper.PubMapperImpl;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с {@link Beer}, {@link BeerRequest}, {@link BeerResponse}, {@link BeerResponseWithPubs}
 *
 * @see BeerService
 * @see BeerRepository
 * @see BeerRepositoryImpl
 * @see PubRepository
 * @see PubRepositoryImpl
 * @see BeerMapper
 * @see PubMapper
 * @see EntityNotFoundException
 */
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final PubRepository pubRepository;
    private final BeerMapper beerMapper;
    private final PubMapper pubMapper;

    public BeerServiceImpl() {
        this.beerRepository = new BeerRepositoryImpl();
        this.pubRepository = new PubRepositoryImpl();
        this.beerMapper = new BeerMapperImpl();
        this.pubMapper = new PubMapperImpl();
    }

    @Override
    public BeerResponse getByUuid(UUID uuid) {
        return beerRepository.findByUuid(uuid)
                .map(beerMapper::toBeerResponse)
                .orElseThrow(() -> EntityNotFoundException.of(Beer.class, uuid));
    }

    @Override
    public BeerResponseWithPubs getByUuidWithPubs(UUID uuid) {
        var beerWithPubs = beerRepository.findByUuid(uuid)
                .map(beerMapper::toBeerResponseWithPubs)
                .orElseThrow(() -> EntityNotFoundException.of(Beer.class, uuid));

        var pubs = pubRepository.findAllByBeerUuid(uuid).stream()
                .map(pubMapper::toPubResponse)
                .toList();

        beerWithPubs.setPubs(pubs);
        return beerWithPubs;
    }

    @Override
    public List<BeerResponse> getAll() {
        return beerRepository.findAll().stream()
                .map(beerMapper::toBeerResponse)
                .toList();
    }

    @Override
    public BeerResponse create(BeerRequest beerRequest) {
        var beerToCreate = beerMapper.toBeer(beerRequest);
        var beerCreated = beerRepository.create(beerToCreate);
        return beerMapper.toBeerResponse(beerCreated);
    }

    @Override
    public BeerResponse update(UUID uuid, BeerRequest beerRequest) {
        var beerInDb = beerRepository.findByUuid(uuid)
                .orElseThrow(() -> EntityNotFoundException.of(Beer.class, uuid));

        var beerToUpdate = beerMapper.updateBeer(beerInDb, beerRequest);
        var beerUpdated = beerRepository.update(uuid, beerToUpdate);

        return beerMapper.toBeerResponse(beerUpdated);
    }

    @Override
    public void deleteByUuid(UUID uuid) {
        beerRepository.deleteByUuid(uuid);
    }
}
