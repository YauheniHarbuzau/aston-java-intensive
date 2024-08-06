package com.aston.service.impl;

import com.aston.dao.entity.Pub;
import com.aston.dao.repository.BeerRepository;
import com.aston.dao.repository.PubRepository;
import com.aston.dao.repository.impl.BeerRepositoryImpl;
import com.aston.dao.repository.impl.PubRepositoryImpl;
import com.aston.exception.EntityNotFoundException;
import com.aston.service.PubService;
import com.aston.service.dto.request.PubRequest;
import com.aston.service.dto.response.PubResponse;
import com.aston.service.dto.response.PubResponseWithBeers;
import com.aston.service.mapper.BeerMapper;
import com.aston.service.mapper.BeerMapperImpl;
import com.aston.service.mapper.PubMapper;
import com.aston.service.mapper.PubMapperImpl;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Сервис для работы с {@link Pub}, {@link PubRequest}, {@link PubResponse}, {@link PubResponseWithBeers}
 *
 * @see PubService
 * @see PubRepository
 * @see PubRepositoryImpl
 * @see BeerRepository
 * @see BeerRepositoryImpl
 * @see PubMapper
 * @see BeerMapper
 * @see EntityNotFoundException
 */
@RequiredArgsConstructor
public class PubServiceImpl implements PubService {

    private final PubRepository pabRepository;
    private final BeerRepository beerRepository;
    private final PubMapper pabMapper;
    private final BeerMapper beerMapper;

    public PubServiceImpl() {
        this.pabRepository = new PubRepositoryImpl();
        this.beerRepository = new BeerRepositoryImpl();
        this.pabMapper = new PubMapperImpl();
        this.beerMapper = new BeerMapperImpl();
    }

    @Override
    public PubResponse getByUuid(UUID uuid) {
        return pabRepository.findByUuid(uuid)
                .map(pabMapper::toPubResponse)
                .orElseThrow(() -> EntityNotFoundException.of(Pub.class, uuid));
    }

    @Override
    public PubResponseWithBeers getByUuidWithBeers(UUID uuid) {
        var pubWithBeers = pabRepository.findByUuid(uuid)
                .map(pabMapper::toPubResponseWithBeers)
                .orElseThrow(() -> EntityNotFoundException.of(Pub.class, uuid));

        var beers = beerRepository.findAllByPubUuid(uuid).stream()
                .map(beerMapper::toBeerResponse)
                .toList();

        pubWithBeers.setBeers(beers);
        return pubWithBeers;
    }

    @Override
    public List<PubResponse> getAll() {
        return pabRepository.findAll().stream()
                .map(pabMapper::toPubResponse)
                .toList();
    }

    @Override
    public PubResponse create(PubRequest pubRequest) {
        var pubToCreate = pabMapper.toPub(pubRequest);
        var pubCreated = pabRepository.create(pubToCreate);
        return pabMapper.toPubResponse(pubCreated);
    }

    @Override
    public PubResponse update(UUID uuid, PubRequest pubRequest) {
        var pubInDb = pabRepository.findByUuid(uuid)
                .orElseThrow(() -> EntityNotFoundException.of(Pub.class, uuid));

        var pubToUpdate = pabMapper.updatePub(pubInDb, pubRequest);
        var pubUpdated = pabRepository.update(uuid, pubToUpdate);

        return pabMapper.toPubResponse(pubUpdated);
    }

    @Override
    public void deleteByUuid(UUID uuid) {
        pabRepository.deleteByUuid(uuid);
    }
}
