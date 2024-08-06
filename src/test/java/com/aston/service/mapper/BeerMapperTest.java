package com.aston.service.mapper;

import com.aston.dao.entity.Address;
import com.aston.dao.entity.Beer;
import com.aston.service.dto.request.BeerRequest;
import com.aston.service.dto.response.BeerResponse;
import com.aston.service.dto.response.BeerResponseWithPubs;
import com.aston.testdatautil.BeerTestData;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isNull;

/**
 * Тестовый класс для {@link BeerMapper}
 */
class BeerMapperTest {

    private final BeerMapper beerMapper = new BeerMapperImpl();

    @Test
    void checkToBeerResponseShouldReturnCorrectBeerResponse() {
        // given
        Beer mappingBeer = BeerTestData.builder()
                .build().buildBeer();
        Address mappingBeerFactoryAddress = mappingBeer.getFactoryAddress();

        // when
        BeerResponse actualBeerResponse = beerMapper.toBeerResponse(mappingBeer);

        // then
        assertThat(actualBeerResponse)
                .hasFieldOrPropertyWithValue("uuid", mappingBeer.getUuid())
                .hasFieldOrPropertyWithValue("name", mappingBeer.getName())
                .hasFieldOrPropertyWithValue("description", mappingBeer.getDescription())
                .hasFieldOrPropertyWithValue("factory", mappingBeer.getFactory())
                .hasFieldOrPropertyWithValue("factoryAddress.country", mappingBeerFactoryAddress.getCountry())
                .hasFieldOrPropertyWithValue("factoryAddress.region", mappingBeerFactoryAddress.getRegion())
                .hasFieldOrPropertyWithValue("factoryAddress.city", mappingBeerFactoryAddress.getCity())
                .hasFieldOrPropertyWithValue("factoryAddress.street", mappingBeerFactoryAddress.getStreet())
                .hasFieldOrPropertyWithValue("factoryAddress.number", mappingBeerFactoryAddress.getNumber())
                .hasFieldOrPropertyWithValue("ABV", mappingBeer.getABV())
                .hasFieldOrPropertyWithValue("OG", mappingBeer.getOG())
                .hasFieldOrPropertyWithValue("IBU", mappingBeer.getIBU())
                .hasFieldOrPropertyWithValue("createDate", mappingBeer.getCreateDate())
                .hasFieldOrPropertyWithValue("updateDate", mappingBeer.getUpdateDate());
    }

    @Test
    void checkToBeerResponseWithPubsShouldReturnCorrectBeerResponseWithPubs() {
        // given
        Beer mappingBeer = BeerTestData.builder()
                .build().buildBeer();
        Address mappingBeerFactoryAddress = mappingBeer.getFactoryAddress();

        // when
        BeerResponseWithPubs actualBeerResponseWithPubs = beerMapper.toBeerResponseWithPubs(mappingBeer);

        // then
        assertThat(actualBeerResponseWithPubs)
                .hasFieldOrPropertyWithValue("uuid", mappingBeer.getUuid())
                .hasFieldOrPropertyWithValue("name", mappingBeer.getName())
                .hasFieldOrPropertyWithValue("description", mappingBeer.getDescription())
                .hasFieldOrPropertyWithValue("factory", mappingBeer.getFactory())
                .hasFieldOrPropertyWithValue("factoryAddress.country", mappingBeerFactoryAddress.getCountry())
                .hasFieldOrPropertyWithValue("factoryAddress.region", mappingBeerFactoryAddress.getRegion())
                .hasFieldOrPropertyWithValue("factoryAddress.city", mappingBeerFactoryAddress.getCity())
                .hasFieldOrPropertyWithValue("factoryAddress.street", mappingBeerFactoryAddress.getStreet())
                .hasFieldOrPropertyWithValue("factoryAddress.number", mappingBeerFactoryAddress.getNumber())
                .hasFieldOrPropertyWithValue("ABV", mappingBeer.getABV())
                .hasFieldOrPropertyWithValue("OG", mappingBeer.getOG())
                .hasFieldOrPropertyWithValue("IBU", mappingBeer.getIBU())
                .hasFieldOrPropertyWithValue("pubs", isNull())
                .hasFieldOrPropertyWithValue("createDate", mappingBeer.getCreateDate())
                .hasFieldOrPropertyWithValue("updateDate", mappingBeer.getUpdateDate());
    }

    @Test
    void checkToBeerShouldReturnCorrectBeer() {
        // given
        BeerRequest mappingBeerRequest = BeerTestData.builder()
                .build().buildBeerRequest();

        // when
        Beer actualBeer = beerMapper.toBeer(mappingBeerRequest);

        // then
        assertThat(actualBeer)
                .hasFieldOrPropertyWithValue(Beer.Fields.uuid, isNull())
                .hasFieldOrPropertyWithValue(Beer.Fields.name, mappingBeerRequest.name())
                .hasFieldOrPropertyWithValue(Beer.Fields.description, mappingBeerRequest.description())
                .hasFieldOrPropertyWithValue(Beer.Fields.factory, mappingBeerRequest.factory())
                .hasFieldOrPropertyWithValue(Beer.Fields.factoryAddress, mappingBeerRequest.factoryAddress())
                .hasFieldOrPropertyWithValue(Beer.Fields.ABV, mappingBeerRequest.ABV())
                .hasFieldOrPropertyWithValue(Beer.Fields.OG, mappingBeerRequest.OG())
                .hasFieldOrPropertyWithValue(Beer.Fields.IBU, mappingBeerRequest.IBU())
                .hasFieldOrPropertyWithValue(Beer.Fields.createDate, isNull())
                .hasFieldOrPropertyWithValue(Beer.Fields.updateDate, isNull());
    }

    @Test
    void checkUpdateBeerShouldReturnCorrectBeer() {
        // given
        Beer mappingBeer = BeerTestData.builder()
                .build().buildBeer();
        BeerRequest mappingBeerRequest = BeerTestData.builder()
                .build().buildBeerRequest();

        // when
        Beer actualBeer = beerMapper.updateBeer(mappingBeer, mappingBeerRequest);

        // then
        assertThat(actualBeer)
                .hasFieldOrPropertyWithValue(Beer.Fields.uuid, mappingBeer.getUuid())
                .hasFieldOrPropertyWithValue(Beer.Fields.name, mappingBeerRequest.name())
                .hasFieldOrPropertyWithValue(Beer.Fields.description, mappingBeerRequest.description())
                .hasFieldOrPropertyWithValue(Beer.Fields.factory, mappingBeerRequest.factory())
                .hasFieldOrPropertyWithValue(Beer.Fields.factoryAddress, mappingBeerRequest.factoryAddress())
                .hasFieldOrPropertyWithValue(Beer.Fields.ABV, mappingBeerRequest.ABV())
                .hasFieldOrPropertyWithValue(Beer.Fields.OG, mappingBeerRequest.OG())
                .hasFieldOrPropertyWithValue(Beer.Fields.IBU, mappingBeerRequest.IBU())
                .hasFieldOrPropertyWithValue(Beer.Fields.createDate, mappingBeer.getCreateDate())
                .hasFieldOrPropertyWithValue(Beer.Fields.updateDate, mappingBeer.getUpdateDate());
    }
}
