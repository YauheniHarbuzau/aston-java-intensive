package com.aston.dao.repository.impl;

import com.aston.dao.entity.Address;
import com.aston.dao.repository.AddressRepository;
import com.aston.util.ConnectionUtil;

import java.sql.SQLException;

/**
 * Репозиторий для работы с {@link Address}
 *
 * @see AddressRepository
 * @see ConnectionUtil
 */
public class AddressRepositoryImpl implements AddressRepository {

    @Override
    public Address findById(Long id, String sqlQuery) {
        try (var connection = ConnectionUtil.openConnection();
             var preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, id);

            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Address.builder()
                        .country(resultSet.getString("country"))
                        .region(resultSet.getString("region"))
                        .city(resultSet.getString("city"))
                        .street(resultSet.getString("street"))
                        .number(resultSet.getString("number"))
                        .build();
            }
            return null;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
