package com.aston.service.impl;

import com.aston.dao.entity.Address;
import com.aston.dao.entity.Beer;
import com.aston.dao.entity.Pub;
import com.aston.dao.repository.BeerRepository;
import com.aston.dao.repository.PubRepository;
import com.aston.dao.repository.impl.BeerRepositoryImpl;
import com.aston.dao.repository.impl.PubRepositoryImpl;
import com.aston.exception.EntityNotFoundException;
import com.aston.service.dto.request.BeerRequest;
import com.aston.service.dto.response.BeerResponse;
import com.aston.service.dto.response.BeerResponseWithPubs;
import com.aston.service.dto.response.PubResponse;
import com.aston.service.mapper.BeerMapper;
import com.aston.service.mapper.BeerMapperImpl;
import com.aston.service.mapper.PubMapper;
import com.aston.service.mapper.PubMapperImpl;
import com.aston.testdatautil.BeerTestData;
import com.aston.testdatautil.PubTestData;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.aston.testdatautil.TestConstant.TEST_BEER_UUID_1;
import static com.aston.testdatautil.TestConstant.TEST_BEER_UUID_2;
import static com.aston.testdatautil.TestConstant.TEST_PUB_UUID_1;
import static com.aston.testdatautil.TestConstant.TEST_PUB_UUID_2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Тестовый класс для {@link BeerServiceImpl}
 */
@ExtendWith(MockitoExtension.class)
class BeerServiceImplTest {

    @InjectMocks
    private BeerServiceImpl beerService;

    @Mock
    private final BeerRepository beerRepository = new BeerRepositoryImpl();

    @Mock
    private final PubRepository pubRepository = new PubRepositoryImpl();

    @Spy
    private final BeerMapper beerMapper = new BeerMapperImpl();

    @Spy
    private final PubMapper pubMapper = new PubMapperImpl();

    @Nested
    class GetByUuidTest {
        @Test
        void checkGetByUuidShouldReturnCorrectBeerResponse() {
            // given
            UUID uuid = TEST_BEER_UUID_1;

            Beer expectedBeer = BeerTestData.builder()
                    .build().buildBeer();
            BeerResponse expectedBeerResponse = BeerTestData.builder()
                    .build().buildBeerResponse();

            doReturn(Optional.of(expectedBeer))
                    .when(beerRepository).findByUuid(uuid);

            // when
            BeerResponse actualBeerResponse = beerService.getByUuid(uuid);

            // then
            assertEquals(expectedBeerResponse, actualBeerResponse);
        }

        @Test
        void checkGetByUuidShouldThrowEntityNotFoundException() {
            // given
            UUID uuidFound = TEST_BEER_UUID_1;
            UUID uuidNotFound = TEST_BEER_UUID_2;

            Beer beer = BeerTestData.builder()
                    .withUuid(uuidFound)
                    .build().buildBeer();
            BeerResponse beerResponse = BeerTestData.builder()
                    .withUuid(uuidFound)
                    .build().buildBeerResponse();

            doReturn(Optional.of(beer))
                    .when(beerRepository).findByUuid(uuidFound);

            // when, then
            assertAll(
                    () -> assertDoesNotThrow(() -> beerService.getByUuid(uuidFound)),
                    () -> assertThrows(EntityNotFoundException.class, () -> beerService.getByUuid(uuidNotFound))
            );
        }
    }

    @Nested
    class GetByUuidWithPubsTest {
        @Test
        void checkGetByUuidWithPubsShouldReturnCorrectBeerResponseWithPubs() {
            // given
            UUID uuid = TEST_BEER_UUID_1;

            Pub expectedPub1 = PubTestData.builder()
                    .withUuid(TEST_PUB_UUID_1)
                    .build().buildPub();
            Pub expectedPub2 = PubTestData.builder()
                    .withUuid(TEST_PUB_UUID_2)
                    .build().buildPub();
            List<Pub> expectedPubList = List.of(expectedPub1, expectedPub2);

            PubResponse expectedPubResponse1 = PubTestData.builder()
                    .withUuid(TEST_PUB_UUID_1)
                    .build().buildPubResponse();
            PubResponse expectedPubResponse2 = PubTestData.builder()
                    .withUuid(TEST_PUB_UUID_2)
                    .build().buildPubResponse();
            List<PubResponse> expectedPubResponseList = List.of(expectedPubResponse1, expectedPubResponse2);

            Beer expectedBeer = BeerTestData.builder()
                    .build().buildBeer();
            BeerResponseWithPubs expectedBeerResponseWithPubs = BeerTestData.builder()
                    .withPubs(expectedPubResponseList)
                    .build().buildBeerResponseWithPubs();

            doReturn(Optional.of(expectedBeer))
                    .when(beerRepository).findByUuid(uuid);
            doReturn(expectedPubList)
                    .when(pubRepository).findAllByBeerUuid(uuid);

            // when
            BeerResponseWithPubs actualBeerResponseWithPubs = beerService.getByUuidWithPubs(uuid);

            // then
            assertEquals(expectedBeerResponseWithPubs, actualBeerResponseWithPubs);
        }

        @Test
        void checkGetByUuidWithPubsShouldThrowEntityNotFoundException() {
            // given
            UUID uuidNotFound = TEST_BEER_UUID_2;

            // when, then
            assertThrows(EntityNotFoundException.class, () -> beerService.getByUuidWithPubs(uuidNotFound));
        }
    }

    @Nested
    class GetAllTest {
        @Test
        void checkGetAllShouldReturnCorrectListOfBeerResponse() {
            // given
            Beer beer1 = BeerTestData.builder()
                    .withUuid(TEST_BEER_UUID_1)
                    .build().buildBeer();
            Beer beer2 = BeerTestData.builder()
                    .withUuid(TEST_BEER_UUID_2)
                    .build().buildBeer();
            BeerResponse beerResponse1 = BeerTestData.builder()
                    .withUuid(TEST_BEER_UUID_1)
                    .build().buildBeerResponse();
            BeerResponse beerResponse2 = BeerTestData.builder()
                    .withUuid(TEST_BEER_UUID_2)
                    .build().buildBeerResponse();

            List<Beer> expectedBeerList = List.of(beer1, beer2);
            List<BeerResponse> expectedBeerResponseList = List.of(beerResponse1, beerResponse2);

            doReturn(expectedBeerList)
                    .when(beerRepository).findAll();

            // when
            List<BeerResponse> actualBeerResponseList = beerService.getAll();

            // then
            assertAll(
                    () -> assertEquals(expectedBeerResponseList, actualBeerResponseList),
                    () -> assertEquals(2, actualBeerResponseList.size())
            );
        }

        @Test
        void checkGetAllShouldReturnEmptyList() {
            // given
            List<BeerResponse> expectedBeerResponseList = Collections.emptyList();

            doReturn(Collections.emptyList())
                    .when(beerRepository).findAll();

            // when
            List<BeerResponse> actualBeerResponseList = beerService.getAll();

            // then
            assertEquals(expectedBeerResponseList, actualBeerResponseList);
        }
    }

    @Nested
    class CreateTest {
        @Test
        void checkCreateShouldReturnCorrectBeerResponse() {
            // given
            BeerRequest beerRequestToCreate = BeerTestData.builder()
                    .build().buildBeerRequest();
            Address beerRequestToCreateFactoryAddress = beerRequestToCreate.factoryAddress();

            Beer expectedBeer = BeerTestData.builder()
                    .build().buildBeer();

            doReturn(expectedBeer)
                    .when(beerRepository).create(any(Beer.class));

            // when
            BeerResponse actualBeerResponse = beerService.create(beerRequestToCreate);
            Address actualFactoryAddress = actualBeerResponse.factoryAddress();

            // then
            assertAll(
                    () -> assertThat(actualBeerResponse.uuid()).isNotNull(),
                    () -> assertThat(actualBeerResponse.name()).isEqualTo(beerRequestToCreate.name()),
                    () -> assertThat(actualBeerResponse.description()).isEqualTo(beerRequestToCreate.description()),
                    () -> assertThat(actualBeerResponse.factory()).isEqualTo(beerRequestToCreate.factory()),
                    () -> assertThat(actualFactoryAddress.getCountry()).isEqualTo(beerRequestToCreateFactoryAddress.getCountry()),
                    () -> assertThat(actualFactoryAddress.getRegion()).isEqualTo(beerRequestToCreateFactoryAddress.getRegion()),
                    () -> assertThat(actualFactoryAddress.getCity()).isEqualTo(beerRequestToCreateFactoryAddress.getCity()),
                    () -> assertThat(actualFactoryAddress.getStreet()).isEqualTo(beerRequestToCreateFactoryAddress.getStreet()),
                    () -> assertThat(actualFactoryAddress.getNumber()).isEqualTo(beerRequestToCreateFactoryAddress.getNumber()),
                    () -> assertThat(actualBeerResponse.ABV()).isEqualTo(beerRequestToCreate.ABV()),
                    () -> assertThat(actualBeerResponse.OG()).isEqualTo(beerRequestToCreate.OG()),
                    () -> assertThat(actualBeerResponse.IBU()).isEqualTo(beerRequestToCreate.IBU()),
                    () -> assertThat(actualBeerResponse.createDate()).isNotNull(),
                    () -> assertThat(actualBeerResponse.updateDate()).isNotNull()
            );
        }
    }

    @Nested
    class UpdateTest {
        @Test
        void checkUpdateShouldReturnCorrectBeerResponse() {
            // given
            UUID uuid = TEST_BEER_UUID_1;

            BeerRequest beerRequestToUpdate = BeerTestData.builder()
                    .build().buildBeerRequest();
            Address beerRequestToUpdateFactoryAddress = beerRequestToUpdate.factoryAddress();

            Beer beerInDb = BeerTestData.builder()
                    .withUuid(uuid)
                    .build().buildBeer();

            Beer expectedBeer = BeerTestData.builder()
                    .build().buildBeer();

            doReturn(Optional.of(beerInDb))
                    .when(beerRepository).findByUuid(uuid);
            doReturn(expectedBeer)
                    .when(beerRepository).update(any(UUID.class), any(Beer.class));

            // when
            BeerResponse actualBeerResponse = beerService.update(uuid, beerRequestToUpdate);
            Address actualFactoryAddress = actualBeerResponse.factoryAddress();

            // then
            assertAll(
                    () -> assertThat(actualBeerResponse.uuid()).isEqualTo(uuid),
                    () -> assertThat(actualBeerResponse.name()).isEqualTo(beerRequestToUpdate.name()),
                    () -> assertThat(actualBeerResponse.description()).isEqualTo(beerRequestToUpdate.description()),
                    () -> assertThat(actualBeerResponse.factory()).isEqualTo(beerRequestToUpdate.factory()),
                    () -> assertThat(actualFactoryAddress.getCountry()).isEqualTo(beerRequestToUpdateFactoryAddress.getCountry()),
                    () -> assertThat(actualFactoryAddress.getRegion()).isEqualTo(beerRequestToUpdateFactoryAddress.getRegion()),
                    () -> assertThat(actualFactoryAddress.getCity()).isEqualTo(beerRequestToUpdateFactoryAddress.getCity()),
                    () -> assertThat(actualFactoryAddress.getStreet()).isEqualTo(beerRequestToUpdateFactoryAddress.getStreet()),
                    () -> assertThat(actualFactoryAddress.getNumber()).isEqualTo(beerRequestToUpdateFactoryAddress.getNumber()),
                    () -> assertThat(actualBeerResponse.ABV()).isEqualTo(beerRequestToUpdate.ABV()),
                    () -> assertThat(actualBeerResponse.OG()).isEqualTo(beerRequestToUpdate.OG()),
                    () -> assertThat(actualBeerResponse.IBU()).isEqualTo(beerRequestToUpdate.IBU()),
                    () -> assertThat(actualBeerResponse.createDate()).isEqualTo(beerInDb.getCreateDate()),
                    () -> assertThat(actualBeerResponse.updateDate()).isNotNull()
            );
        }

        @Test
        void checkUpdateShouldThrowEntityNotFoundException() {
            // given
            UUID uuidNotFound = TEST_BEER_UUID_2;

            // when, then
            assertThrows(EntityNotFoundException.class, () -> beerService.update(uuidNotFound, any(BeerRequest.class)));
        }
    }

    @Nested
    class DeleteByUuidTest {
        @Test
        void checkDeleteByUuidShouldWorkCorrectly() {
            // given
            UUID uuid = TEST_BEER_UUID_1;

            doNothing()
                    .when(beerRepository).deleteByUuid(uuid);

            // when
            beerService.deleteByUuid(uuid);

            // then
            verify(beerRepository, times(1)).deleteByUuid(uuid);
            assertThat(beerRepository.findByUuid(uuid)).isEmpty();
        }
    }
}
