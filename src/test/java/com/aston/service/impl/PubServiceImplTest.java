package com.aston.service.impl;

import com.aston.dao.entity.Address;
import com.aston.dao.entity.Beer;
import com.aston.dao.entity.Pub;
import com.aston.dao.repository.BeerRepository;
import com.aston.dao.repository.PubRepository;
import com.aston.dao.repository.impl.BeerRepositoryImpl;
import com.aston.dao.repository.impl.PubRepositoryImpl;
import com.aston.exception.EntityNotFoundException;
import com.aston.service.dto.request.PubRequest;
import com.aston.service.dto.response.BeerResponse;
import com.aston.service.dto.response.PubResponse;
import com.aston.service.dto.response.PubResponseWithBeers;
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
 * Тестовый класс для {@link PubServiceImpl}
 */
@ExtendWith(MockitoExtension.class)
class PubServiceImplTest {

    @InjectMocks
    private PubServiceImpl pubService;

    @Mock
    private final PubRepository pubRepository = new PubRepositoryImpl();

    @Mock
    private final BeerRepository beerRepository = new BeerRepositoryImpl();

    @Spy
    private final PubMapper pubMapper = new PubMapperImpl();

    @Spy
    private final BeerMapper beerMapper = new BeerMapperImpl();

    @Nested
    class GetByUuidTest {
        @Test
        void checkGetByUuidShouldReturnCorrectPubResponse() {
            // given
            UUID uuid = TEST_PUB_UUID_1;

            Pub expectedPub = PubTestData.builder()
                    .build().buildPub();
            PubResponse expectedPubResponse = PubTestData.builder()
                    .build().buildPubResponse();

            doReturn(Optional.of(expectedPub))
                    .when(pubRepository).findByUuid(uuid);

            // when
            PubResponse actualPubResponse = pubService.getByUuid(uuid);

            // then
            assertEquals(expectedPubResponse, actualPubResponse);
        }

        @Test
        void checkGetByUuidShouldThrowEntityNotFoundException() {
            // given
            UUID uuidFound = TEST_PUB_UUID_1;
            UUID uuidNotFound = TEST_PUB_UUID_2;

            Pub pub = PubTestData.builder()
                    .withUuid(uuidFound)
                    .build().buildPub();
            PubResponse pubResponse = PubTestData.builder()
                    .withUuid(uuidFound)
                    .build().buildPubResponse();

            doReturn(Optional.of(pub))
                    .when(pubRepository).findByUuid(uuidFound);

            // when, then
            assertAll(
                    () -> assertDoesNotThrow(() -> pubService.getByUuid(uuidFound)),
                    () -> assertThrows(EntityNotFoundException.class, () -> pubService.getByUuid(uuidNotFound))
            );
        }
    }

    @Nested
    class GetByUuidWithBeersTest {
        @Test
        void checkGetByUuidWithBeersShouldReturnCorrectPubResponseWithBeers() {
            // given
            UUID uuid = TEST_PUB_UUID_1;

            Beer expectedBeer1 = BeerTestData.builder()
                    .withUuid(TEST_BEER_UUID_1)
                    .build().buildBeer();
            Beer expectedBeer2 = BeerTestData.builder()
                    .withUuid(TEST_BEER_UUID_2)
                    .build().buildBeer();
            List<Beer> expectedBeerList = List.of(expectedBeer1, expectedBeer2);

            BeerResponse expectedBeerResponse1 = BeerTestData.builder()
                    .withUuid(TEST_BEER_UUID_1)
                    .build().buildBeerResponse();
            BeerResponse expectedBeerResponse2 = BeerTestData.builder()
                    .withUuid(TEST_BEER_UUID_2)
                    .build().buildBeerResponse();
            List<BeerResponse> expectedBeerResponseList = List.of(expectedBeerResponse1, expectedBeerResponse2);

            Pub expectedPub = PubTestData.builder()
                    .build().buildPub();
            PubResponseWithBeers expectedPubResponseWithBeers = PubTestData.builder()
                    .withBeers(expectedBeerResponseList)
                    .build().buildPubResponseWithBeers();

            doReturn(Optional.of(expectedPub))
                    .when(pubRepository).findByUuid(uuid);
            doReturn(expectedBeerList)
                    .when(beerRepository).findAllByPubUuid(uuid);

            // when
            PubResponseWithBeers actualPubResponseWithBeers =
                    pubService.getByUuidWithBeers(uuid);

            // then
            assertEquals(expectedPubResponseWithBeers, actualPubResponseWithBeers);
        }

        @Test
        void checkGetByUuidWithPubsShouldThrowEntityNotFoundException() {
            // given
            UUID uuidNotFound = TEST_PUB_UUID_2;

            // when, then
            assertThrows(EntityNotFoundException.class, () -> pubService.getByUuidWithBeers(uuidNotFound));
        }
    }

    @Nested
    class GetAllTest {
        @Test
        void checkGetAllShouldReturnCorrectListOfPubResponse() {
            // given
            Pub pub1 = PubTestData.builder()
                    .withUuid(TEST_PUB_UUID_1)
                    .build().buildPub();
            Pub pub2 = PubTestData.builder()
                    .withUuid(TEST_PUB_UUID_2)
                    .build().buildPub();
            PubResponse pubResponse1 = PubTestData.builder()
                    .withUuid(TEST_PUB_UUID_1)
                    .build().buildPubResponse();
            PubResponse pubResponse2 = PubTestData.builder()
                    .withUuid(TEST_PUB_UUID_2)
                    .build().buildPubResponse();

            List<Pub> expectedPubList = List.of(pub1, pub2);
            List<PubResponse> expectedPubResponseList = List.of(pubResponse1, pubResponse2);

            doReturn(expectedPubList)
                    .when(pubRepository).findAll();

            // when
            List<PubResponse> actualPubResponseList = pubService.getAll();

            // then
            assertAll(
                    () -> assertEquals(expectedPubResponseList, actualPubResponseList),
                    () -> assertEquals(2, actualPubResponseList.size())
            );
        }

        @Test
        void checkGetAllShouldReturnEmptyList() {
            // given
            List<PubResponse> expectedPubResponseList = Collections.emptyList();

            doReturn(Collections.emptyList())
                    .when(pubRepository).findAll();

            // when
            List<PubResponse> actualPubResponseList = pubService.getAll();

            // then
            assertEquals(expectedPubResponseList, actualPubResponseList);
        }
    }

    @Nested
    class CreateTest {
        @Test
        void checkCreateShouldReturnCorrectPubResponse() {
            // given
            PubRequest pubRequestToCreate = PubTestData.builder()
                    .build().buildPubRequest();
            Address pubRequestToCreatePubAddress = pubRequestToCreate.pubAddress();

            Pub expectedPub = PubTestData.builder()
                    .build().buildPub();

            doReturn(expectedPub)
                    .when(pubRepository).create(any(Pub.class));

            // when
            PubResponse actualPubResponse = pubService.create(pubRequestToCreate);
            Address actualPubAddress = actualPubResponse.pubAddress();

            // then
            assertAll(
                    () -> assertThat(actualPubResponse.uuid()).isNotNull(),
                    () -> assertThat(actualPubResponse.name()).isEqualTo(pubRequestToCreate.name()),
                    () -> assertThat(actualPubResponse.description()).isEqualTo(pubRequestToCreate.description()),
                    () -> assertThat(actualPubAddress.getCountry()).isEqualTo(pubRequestToCreatePubAddress.getCountry()),
                    () -> assertThat(actualPubAddress.getRegion()).isEqualTo(pubRequestToCreatePubAddress.getRegion()),
                    () -> assertThat(actualPubAddress.getCity()).isEqualTo(pubRequestToCreatePubAddress.getCity()),
                    () -> assertThat(actualPubAddress.getStreet()).isEqualTo(pubRequestToCreatePubAddress.getStreet()),
                    () -> assertThat(actualPubAddress.getNumber()).isEqualTo(pubRequestToCreatePubAddress.getNumber()),
                    () -> assertThat(actualPubResponse.createDate()).isNotNull(),
                    () -> assertThat(actualPubResponse.updateDate()).isNotNull()
            );
        }
    }

    @Nested
    class UpdateTest {
        @Test
        void checkUpdateShouldReturnCorrectPubResponse() {
            // given
            UUID uuid = TEST_PUB_UUID_1;

            PubRequest pubRequestToUpdate = PubTestData.builder()
                    .build().buildPubRequest();
            Address pubRequestToUpdatePubAddress = pubRequestToUpdate.pubAddress();

            Pub pubInDb = PubTestData.builder()
                    .withUuid(uuid)
                    .build().buildPub();

            Pub expectedPub = PubTestData.builder()
                    .build().buildPub();

            doReturn(Optional.of(pubInDb))
                    .when(pubRepository).findByUuid(uuid);
            doReturn(expectedPub)
                    .when(pubRepository).update(any(UUID.class), any(Pub.class));

            // when
            PubResponse actualPubResponse = pubService.update(uuid, pubRequestToUpdate);
            Address actualPubAddress = actualPubResponse.pubAddress();

            // then
            assertAll(
                    () -> assertThat(actualPubResponse.uuid()).isEqualTo(uuid),
                    () -> assertThat(actualPubResponse.name()).isEqualTo(pubRequestToUpdate.name()),
                    () -> assertThat(actualPubResponse.description()).isEqualTo(pubRequestToUpdate.description()),
                    () -> assertThat(actualPubAddress.getCountry()).isEqualTo(pubRequestToUpdatePubAddress.getCountry()),
                    () -> assertThat(actualPubAddress.getRegion()).isEqualTo(pubRequestToUpdatePubAddress.getRegion()),
                    () -> assertThat(actualPubAddress.getCity()).isEqualTo(pubRequestToUpdatePubAddress.getCity()),
                    () -> assertThat(actualPubAddress.getStreet()).isEqualTo(pubRequestToUpdatePubAddress.getStreet()),
                    () -> assertThat(actualPubAddress.getNumber()).isEqualTo(pubRequestToUpdatePubAddress.getNumber()),
                    () -> assertThat(actualPubResponse.createDate()).isEqualTo(pubInDb.getCreateDate()),
                    () -> assertThat(actualPubResponse.updateDate()).isNotNull()
            );
        }

        @Test
        void checkUpdateShouldThrowEntityNotFoundException() {
            // given
            UUID uuidNotFound = TEST_PUB_UUID_2;

            // when, then
            assertThrows(EntityNotFoundException.class, () -> pubService.update(uuidNotFound, any(PubRequest.class)));
        }
    }

    @Nested
    class DeleteByUuidTest {
        @Test
        void checkDeleteByUuidShouldWorkCorrectly() {
            // given
            UUID uuid = TEST_PUB_UUID_1;

            doNothing()
                    .when(pubRepository).deleteByUuid(uuid);

            // when
            pubService.deleteByUuid(uuid);

            // then
            verify(pubRepository, times(1)).deleteByUuid(uuid);
            assertThat(pubRepository.findByUuid(uuid)).isEmpty();
        }
    }
}
