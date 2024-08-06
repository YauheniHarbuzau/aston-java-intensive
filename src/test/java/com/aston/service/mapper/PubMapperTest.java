package com.aston.service.mapper;

import com.aston.dao.entity.Address;
import com.aston.dao.entity.Pub;
import com.aston.service.dto.request.PubRequest;
import com.aston.service.dto.response.PubResponse;
import com.aston.service.dto.response.PubResponseWithBeers;
import com.aston.testdatautil.PubTestData;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.isNull;

/**
 * Тестовый класс для {@link PubMapper}
 */
class PubMapperTest {

    private final PubMapper pubMapper = new PubMapperImpl();

    @Test
    void checkToPubResponseShouldReturnCorrectPubResponse() {
        // given
        Pub mappingPub = PubTestData.builder()
                .build().buildPub();
        Address mappingPubFactoryAddress = mappingPub.getPubAddress();

        // when
        PubResponse actualPubResponse = pubMapper.toPubResponse(mappingPub);

        // then
        assertThat(actualPubResponse)
                .hasFieldOrPropertyWithValue("uuid", mappingPub.getUuid())
                .hasFieldOrPropertyWithValue("name", mappingPub.getName())
                .hasFieldOrPropertyWithValue("description", mappingPub.getDescription())
                .hasFieldOrPropertyWithValue("pubAddress.country", mappingPubFactoryAddress.getCountry())
                .hasFieldOrPropertyWithValue("pubAddress.region", mappingPubFactoryAddress.getRegion())
                .hasFieldOrPropertyWithValue("pubAddress.city", mappingPubFactoryAddress.getCity())
                .hasFieldOrPropertyWithValue("pubAddress.street", mappingPubFactoryAddress.getStreet())
                .hasFieldOrPropertyWithValue("pubAddress.number", mappingPubFactoryAddress.getNumber())
                .hasFieldOrPropertyWithValue("createDate", mappingPub.getCreateDate())
                .hasFieldOrPropertyWithValue("updateDate", mappingPub.getUpdateDate());
    }

    @Test
    void checkToPubResponseWithBeersShouldReturnCorrectPubResponseWithBeers() {
        // given
        Pub mappingPub = PubTestData.builder()
                .build().buildPub();
        Address mappingPubFactoryAddress = mappingPub.getPubAddress();

        // when
        PubResponseWithBeers actualPubResponseWithBeers = pubMapper.toPubResponseWithBeers(mappingPub);

        // then
        assertThat(actualPubResponseWithBeers)
                .hasFieldOrPropertyWithValue("uuid", mappingPub.getUuid())
                .hasFieldOrPropertyWithValue("name", mappingPub.getName())
                .hasFieldOrPropertyWithValue("description", mappingPub.getDescription())
                .hasFieldOrPropertyWithValue("pubAddress.country", mappingPubFactoryAddress.getCountry())
                .hasFieldOrPropertyWithValue("pubAddress.region", mappingPubFactoryAddress.getRegion())
                .hasFieldOrPropertyWithValue("pubAddress.city", mappingPubFactoryAddress.getCity())
                .hasFieldOrPropertyWithValue("pubAddress.street", mappingPubFactoryAddress.getStreet())
                .hasFieldOrPropertyWithValue("pubAddress.number", mappingPubFactoryAddress.getNumber())
                .hasFieldOrPropertyWithValue("beers", isNull())
                .hasFieldOrPropertyWithValue("createDate", mappingPub.getCreateDate())
                .hasFieldOrPropertyWithValue("updateDate", mappingPub.getUpdateDate());
    }

    @Test
    void checkToPabShouldReturnCorrectPab() {
        // given
        PubRequest mappingPubRequest = PubTestData.builder()
                .build().buildPubRequest();

        // when
        Pub actualPub = pubMapper.toPub(mappingPubRequest);

        // then
        assertThat(actualPub)
                .hasFieldOrPropertyWithValue(Pub.Fields.uuid, isNull())
                .hasFieldOrPropertyWithValue(Pub.Fields.name, mappingPubRequest.name())
                .hasFieldOrPropertyWithValue(Pub.Fields.description, mappingPubRequest.description())
                .hasFieldOrPropertyWithValue(Pub.Fields.pubAddress, mappingPubRequest.pubAddress())
                .hasFieldOrPropertyWithValue(Pub.Fields.createDate, isNull())
                .hasFieldOrPropertyWithValue(Pub.Fields.updateDate, isNull());
    }

    @Test
    void checkUpdatePabShouldReturnCorrectPab() {
        // given
        Pub mappingPub = PubTestData.builder()
                .build().buildPub();
        PubRequest mappingPubRequest = PubTestData.builder()
                .build().buildPubRequest();

        // when
        Pub actualPub = pubMapper.updatePub(mappingPub, mappingPubRequest);

        // then
        assertThat(actualPub)
                .hasFieldOrPropertyWithValue(Pub.Fields.uuid, mappingPub.getUuid())
                .hasFieldOrPropertyWithValue(Pub.Fields.name, mappingPubRequest.name())
                .hasFieldOrPropertyWithValue(Pub.Fields.description, mappingPubRequest.description())
                .hasFieldOrPropertyWithValue(Pub.Fields.pubAddress, mappingPubRequest.pubAddress())
                .hasFieldOrPropertyWithValue(Pub.Fields.createDate, mappingPub.getCreateDate())
                .hasFieldOrPropertyWithValue(Pub.Fields.updateDate, mappingPub.getUpdateDate());
    }
}
