package com.aston.testdatautil;

import com.aston.dao.entity.Address;
import com.aston.dao.entity.Pub;
import com.aston.service.dto.request.PubRequest;
import com.aston.service.dto.response.BeerResponse;
import com.aston.service.dto.response.PubResponse;
import com.aston.service.dto.response.PubResponseWithBeers;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.aston.testdatautil.TestConstant.TEST_DATE_TIME;
import static com.aston.testdatautil.TestConstant.TEST_PUB_UUID_1;
import static java.util.Collections.emptyList;

/**
 * Класс для предоставления тестовых данных
 */
@Data
@Builder(setterPrefix = "with")
public class PubTestData {

    @Builder.Default
    private UUID uuid = TEST_PUB_UUID_1;

    @Builder.Default
    private String name = "PUB_NAME";

    @Builder.Default
    private String description = "PUB_DESCRIPTION";

    @Builder.Default
    private Address pubAddress = AddressTestData.builder().build().buildAddress();

    @Builder.Default
    private List<BeerResponse> beers = emptyList();

    @Builder.Default
    private LocalDateTime createDate = TEST_DATE_TIME;

    @Builder.Default
    private LocalDateTime updateDate = TEST_DATE_TIME;

    public Pub buildPub() {
        return new Pub(uuid, name, description, pubAddress, createDate, updateDate);
    }

    public PubRequest buildPubRequest() {
        return new PubRequest(name, description, pubAddress);
    }

    public PubResponse buildPubResponse() {
        return new PubResponse(uuid, name, description, pubAddress, createDate, updateDate);
    }

    public PubResponseWithBeers buildPubResponseWithBeers() {
        return new PubResponseWithBeers(uuid, name, description, pubAddress, beers, createDate, updateDate);
    }
}
