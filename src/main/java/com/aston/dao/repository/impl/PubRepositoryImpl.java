package com.aston.dao.repository.impl;

import com.aston.constant.Constant;
import com.aston.dao.entity.Address;
import com.aston.dao.entity.Pub;
import com.aston.dao.repository.AddressRepository;
import com.aston.dao.repository.PubRepository;
import com.aston.util.ConnectionUtil;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.aston.constant.Constant.SQL_PUB_ADDRESS_FIND_BY_ID;
import static com.aston.constant.Constant.SQL_PUB_CREATE;
import static com.aston.constant.Constant.SQL_PUB_DELETE_BY_UUID;
import static com.aston.constant.Constant.SQL_PUB_FIND_ALL;
import static com.aston.constant.Constant.SQL_PUB_FIND_ALL_BY_BEER_UUID;
import static com.aston.constant.Constant.SQL_PUB_FIND_BY_UUID;
import static com.aston.constant.Constant.SQL_PUB_UPDATE;
import static java.util.Optional.empty;

/**
 * Репозиторий для работы с {@link Pub}
 *
 * @see PubRepository
 * @see AddressRepository
 * @see AddressRepositoryImpl
 * @see ConnectionUtil
 * @see Constant
 */
public class PubRepositoryImpl implements PubRepository {

    private final AddressRepository addressRepository;

    public PubRepositoryImpl() {
        this.addressRepository = new AddressRepositoryImpl();
    }

    @Override
    public Optional<Pub> findByUuid(UUID uuid) {
        try (var connection = ConnectionUtil.openConnection();
             var preparedStatement = connection.prepareStatement(SQL_PUB_FIND_BY_UUID)) {
            preparedStatement.setObject(1, uuid);

            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(Pub.builder()
                        .uuid(resultSet.getObject("uuid", UUID.class))
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .pubAddress(findPubAddress(resultSet.getLong("pub_address_id")))
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
    public List<Pub> findAll() {
        try (var connection = ConnectionUtil.openConnection();
             var statement = connection.createStatement()) {
            List<Pub> pubs = new ArrayList<>();

            var resultSet = statement.executeQuery(SQL_PUB_FIND_ALL);
            while (resultSet.next()) {
                pubs.add(Pub.builder()
                        .uuid(resultSet.getObject("uuid", UUID.class))
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .pubAddress(findPubAddress(resultSet.getLong("pub_address_id")))
                        .createDate(resultSet.getTimestamp("create_date").toLocalDateTime())
                        .updateDate(resultSet.getTimestamp("update_date").toLocalDateTime())
                        .build());
            }
            return pubs;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<Pub> findAllByBeerUuid(UUID beerUuid) {
        try (var connection = ConnectionUtil.openConnection();
             var preparedStatement = connection.prepareStatement(SQL_PUB_FIND_ALL_BY_BEER_UUID)) {
            List<Pub> pubs = new ArrayList<>();

            preparedStatement.setObject(1, beerUuid);

            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                pubs.add(Pub.builder()
                        .uuid(resultSet.getObject("uuid", UUID.class))
                        .name(resultSet.getString("name"))
                        .description(resultSet.getString("description"))
                        .pubAddress(findPubAddress(resultSet.getLong("pub_address_id")))
                        .createDate(resultSet.getTimestamp("create_date").toLocalDateTime())
                        .updateDate(resultSet.getTimestamp("update_date").toLocalDateTime())
                        .build());
            }
            return pubs;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Pub create(Pub pub) {
        try (var connection = ConnectionUtil.openConnection();
             var preparedStatement = connection.prepareStatement(SQL_PUB_CREATE)) {
            var uuid = UUID.randomUUID();
            var dateTimeNow = LocalDateTime.now();
            var createDate = Timestamp.valueOf(dateTimeNow);

            preparedStatement.setObject(1, pub.getPubAddress().getCountry());
            preparedStatement.setString(2, pub.getPubAddress().getRegion());
            preparedStatement.setString(3, pub.getPubAddress().getCity());
            preparedStatement.setString(4, pub.getPubAddress().getStreet());
            preparedStatement.setString(5, pub.getPubAddress().getNumber());
            preparedStatement.setObject(6, uuid);
            preparedStatement.setString(7, pub.getName());
            preparedStatement.setString(8, pub.getDescription());
            preparedStatement.setTimestamp(9, createDate);
            preparedStatement.setTimestamp(10, createDate);
            preparedStatement.executeUpdate();

            return Pub.builder()
                    .uuid(uuid)
                    .name(pub.getName())
                    .description(pub.getDescription())
                    .pubAddress(pub.getPubAddress())
                    .createDate(dateTimeNow)
                    .updateDate(dateTimeNow)
                    .build();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public Pub update(UUID uuid, Pub pub) {
        try (var connection = ConnectionUtil.openConnection();
             var preparedStatement = connection.prepareStatement(SQL_PUB_UPDATE)) {
            var dateTimeNow = LocalDateTime.now();
            var updateDate = Timestamp.valueOf(dateTimeNow);

            preparedStatement.setString(1, pub.getName());
            preparedStatement.setString(2, pub.getDescription());
            preparedStatement.setTimestamp(3, updateDate);
            preparedStatement.setObject(4, uuid);
            preparedStatement.setObject(5, pub.getPubAddress().getCountry());
            preparedStatement.setString(6, pub.getPubAddress().getRegion());
            preparedStatement.setString(7, pub.getPubAddress().getCity());
            preparedStatement.setString(8, pub.getPubAddress().getStreet());
            preparedStatement.setString(9, pub.getPubAddress().getNumber());
            preparedStatement.executeUpdate();

            return Pub.builder()
                    .uuid(uuid)
                    .name(pub.getName())
                    .description(pub.getDescription())
                    .pubAddress(pub.getPubAddress())
                    .createDate(pub.getCreateDate())
                    .updateDate(dateTimeNow)
                    .build();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void deleteByUuid(UUID uuid) {
        try (var connection = ConnectionUtil.openConnection();
             var preparedStatement = connection.prepareStatement(SQL_PUB_DELETE_BY_UUID)) {
            preparedStatement.setObject(1, uuid);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Address findPubAddress(Long pubAddressId) {
        return addressRepository.findById(pubAddressId, SQL_PUB_ADDRESS_FIND_BY_ID);
    }
}
