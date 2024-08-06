package com.aston.dao.repository.impl;

import com.aston.constant.Constant;
import com.aston.dao.entity.Address;
import com.aston.dao.entity.Beer;
import com.aston.dao.repository.AddressRepository;
import com.aston.dao.repository.BeerRepository;
import com.aston.util.ConnectionUtil;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.aston.constant.Constant.SQL_BEER_CREATE;
import static com.aston.constant.Constant.SQL_BEER_DELETE_BY_UUID;
import static com.aston.constant.Constant.SQL_BEER_FIND_ALL;
import static com.aston.constant.Constant.SQL_BEER_FIND_ALL_BY_PUB_UUID;
import static com.aston.constant.Constant.SQL_BEER_FIND_BY_UUID;
import static com.aston.constant.Constant.SQL_BEER_UPDATE;
import static com.aston.constant.Constant.SQL_FACTORY_ADDRESS_FIND_BY_ID;
import static java.util.Optional.empty;

/**
 * Репозиторий для работы с {@link Beer}
 *
 * @see BeerRepository
 * @see AddressRepository
 * @see AddressRepositoryImpl
 * @see ConnectionUtil
 * @see Constant
 */
public class BeerRepositoryImpl implements BeerRepository {

    private final AddressRepository addressRepository;

    public BeerRepositoryImpl() {
        this.addressRepository = new AddressRepositoryImpl();
    }

    @Override
    public Optional<Beer> findByUuid(UUID uuid) {
        try (var connection = ConnectionUtil.openConnection();
             var preparedStatement = connection.prepareStatement(SQL_BEER_FIND_BY_UUID)) {
            preparedStatement.setObject(1, uuid);

            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(Beer.builder()
                        .uuid(resultSet.getObject("uuid", UUID.class))
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .factory(resultSet.getString("factory"))
                        .factoryAddress(findFactoryAddress(resultSet.getLong("factory_address_id")))
                        .ABV(resultSet.getDouble("ABV"))
                        .OG(resultSet.getDouble("OG"))
                        .IBU(resultSet.getInt("IBU"))
                        .createDate(resultSet.getTimestamp("create_date").toLocalDateTime())
                        .updateDate(resultSet.getTimestamp("update_date").toLocalDateTime())
                        .build());
            }
            return empty();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Beer> findAll() {
        try (var connection = ConnectionUtil.openConnection();
             var statement = connection.createStatement()) {
            List<Beer> beers = new ArrayList<>();

            var resultSet = statement.executeQuery(SQL_BEER_FIND_ALL);
            while (resultSet.next()) {
                beers.add(Beer.builder()
                        .uuid(resultSet.getObject("uuid", UUID.class))
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .factory(resultSet.getString("factory"))
                        .factoryAddress(findFactoryAddress(resultSet.getLong("factory_address_id")))
                        .ABV(resultSet.getDouble("ABV"))
                        .OG(resultSet.getDouble("OG"))
                        .IBU(resultSet.getInt("IBU"))
                        .createDate(resultSet.getTimestamp("create_date").toLocalDateTime())
                        .updateDate(resultSet.getTimestamp("update_date").toLocalDateTime())
                        .build());
            }
            return beers;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Beer> findAllByPubUuid(UUID pubUuid) {
        try (var connection = ConnectionUtil.openConnection();
             var preparedStatement = connection.prepareStatement(SQL_BEER_FIND_ALL_BY_PUB_UUID)) {
            List<Beer> beers = new ArrayList<>();

            preparedStatement.setObject(1, pubUuid);

            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                beers.add(Beer.builder()
                        .uuid(resultSet.getObject("uuid", UUID.class))
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .factory(resultSet.getString("factory"))
                        .factoryAddress(findFactoryAddress(resultSet.getLong("factory_address_id")))
                        .ABV(resultSet.getDouble("ABV"))
                        .OG(resultSet.getDouble("OG"))
                        .IBU(resultSet.getInt("IBU"))
                        .createDate(resultSet.getTimestamp("create_date").toLocalDateTime())
                        .updateDate(resultSet.getTimestamp("update_date").toLocalDateTime())
                        .build());
            }
            return beers;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Beer create(Beer beer) {
        try (var connection = ConnectionUtil.openConnection();
             var preparedStatement = connection.prepareStatement(SQL_BEER_CREATE)) {
            var uuid = UUID.randomUUID();
            var dateTimeNow = LocalDateTime.now();
            var createDate = Timestamp.valueOf(dateTimeNow);

            preparedStatement.setString(1, beer.getFactoryAddress().getCountry());
            preparedStatement.setString(2, beer.getFactoryAddress().getRegion());
            preparedStatement.setString(3, beer.getFactoryAddress().getCity());
            preparedStatement.setString(4, beer.getFactoryAddress().getStreet());
            preparedStatement.setString(5, beer.getFactoryAddress().getNumber());
            preparedStatement.setObject(6, uuid);
            preparedStatement.setString(7, beer.getName());
            preparedStatement.setString(8, beer.getDescription());
            preparedStatement.setString(9, beer.getFactory());
            preparedStatement.setDouble(10, beer.getABV());
            preparedStatement.setDouble(11, beer.getOG());
            preparedStatement.setInt(12, beer.getIBU());
            preparedStatement.setTimestamp(13, createDate);
            preparedStatement.setTimestamp(14, createDate);
            preparedStatement.executeUpdate();

            return Beer.builder()
                    .uuid(uuid)
                    .name(beer.getName())
                    .description(beer.getDescription())
                    .factory(beer.getFactory())
                    .factoryAddress(beer.getFactoryAddress())
                    .ABV(beer.getABV())
                    .OG(beer.getOG())
                    .IBU(beer.getIBU())
                    .createDate(dateTimeNow)
                    .updateDate(dateTimeNow)
                    .build();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Beer update(UUID uuid, Beer beer) {
        try (var connection = ConnectionUtil.openConnection();
             var preparedStatement = connection.prepareStatement(SQL_BEER_UPDATE)) {
            var dateTimeNow = LocalDateTime.now();
            var updateDate = Timestamp.valueOf(dateTimeNow);

            preparedStatement.setString(1, beer.getName());
            preparedStatement.setString(2, beer.getDescription());
            preparedStatement.setString(3, beer.getFactory());
            preparedStatement.setDouble(4, beer.getABV());
            preparedStatement.setDouble(5, beer.getOG());
            preparedStatement.setInt(6, beer.getIBU());
            preparedStatement.setTimestamp(7, updateDate);
            preparedStatement.setObject(8, uuid);
            preparedStatement.setString(9, beer.getFactoryAddress().getCountry());
            preparedStatement.setString(10, beer.getFactoryAddress().getRegion());
            preparedStatement.setString(11, beer.getFactoryAddress().getCity());
            preparedStatement.setString(12, beer.getFactoryAddress().getStreet());
            preparedStatement.setString(13, beer.getFactoryAddress().getNumber());

            preparedStatement.executeUpdate();

            return Beer.builder()
                    .uuid(uuid)
                    .name(beer.getName())
                    .description(beer.getDescription())
                    .factory(beer.getFactory())
                    .factoryAddress(beer.getFactoryAddress())
                    .ABV(beer.getABV())
                    .OG(beer.getOG())
                    .IBU(beer.getIBU())
                    .createDate(beer.getCreateDate())
                    .updateDate(dateTimeNow)
                    .build();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void deleteByUuid(UUID uuid) {
        try (var connection = ConnectionUtil.openConnection();
             var preparedStatement = connection.prepareStatement(SQL_BEER_DELETE_BY_UUID)) {
            preparedStatement.setObject(1, uuid);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Address findFactoryAddress(Long factoryAddressId) {
        return addressRepository.findById(factoryAddressId, SQL_FACTORY_ADDRESS_FIND_BY_ID);
    }
}
