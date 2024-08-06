package com.aston.testdatautil;

import com.aston.dao.entity.Address;
import com.aston.dao.entity.Beer;
import com.aston.service.dto.request.BeerRequest;
import com.aston.service.dto.response.BeerResponse;
import com.aston.service.dto.response.BeerResponseWithPubs;
import com.aston.service.dto.response.PubResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.aston.testdatautil.TestConstant.TEST_BEER_UUID_1;
import static com.aston.testdatautil.TestConstant.TEST_DATE_TIME;
import static java.util.Collections.emptyList;

/**
 * Класс для предоставления тестовых данных
 */
@Data
@Builder(setterPrefix = "with")
public class BeerTestData {

    @Builder.Default
    private UUID uuid = TEST_BEER_UUID_1;

    @Builder.Default
    private String name = "BEER_NAME";

    @Builder.Default
    private String description = "BEER_DESCRIPTION";

    @Builder.Default
    private String factory = "BEER_FACTORY";

    @Builder.Default
    private Address factoryAddress = AddressTestData.builder().build().buildAddress();

    @Builder.Default
    private Double ABV = 5.0;

    @Builder.Default
    private Double OG = 11.5;

    @Builder.Default
    private Integer IBU = 10;

    @Builder.Default
    private List<PubResponse> pubs = emptyList();

    @Builder.Default
    private LocalDateTime createDate = TEST_DATE_TIME;

    @Builder.Default
    private LocalDateTime updateDate = TEST_DATE_TIME;

    public Beer buildBeer() {
        return new Beer(uuid, name, description, factory, factoryAddress, ABV, OG, IBU, createDate, updateDate);
    }

    public BeerRequest buildBeerRequest() {
        return new BeerRequest(name, description, factory, factoryAddress, ABV, OG, IBU);
    }

    public BeerResponse buildBeerResponse() {
        return new BeerResponse(uuid, name, description, factory, factoryAddress, ABV, OG, IBU, createDate, updateDate);
    }

    public BeerResponseWithPubs buildBeerResponseWithPubs() {
        return new BeerResponseWithPubs(uuid, name, description, factory, factoryAddress, ABV, OG, IBU, pubs, createDate, updateDate);
    }
}
