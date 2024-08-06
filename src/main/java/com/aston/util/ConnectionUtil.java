package com.aston.util;

import com.aston.constant.Constant;
import com.aston.dao.repository.impl.AddressRepositoryImpl;
import com.aston.dao.repository.impl.BeerRepositoryImpl;
import com.aston.dao.repository.impl.PubRepositoryImpl;
import lombok.experimental.UtilityClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.aston.constant.Constant.DB_URL;
import static com.aston.constant.Constant.DRIVER_CLASS_NAME;
import static com.aston.constant.Constant.PASSWORD;
import static com.aston.constant.Constant.USERNAME;

/**
 * Утилитарный класс для выполнения и закрытия подключения к базе данных
 *
 * @see InitDatabaseUtil
 * @see PubRepositoryImpl
 * @see BeerRepositoryImpl
 * @see AddressRepositoryImpl
 * @see Constant
 */
@UtilityClass
public class ConnectionUtil {

    public Connection getConnection(String driverClassName, String url, String username, String password) {
        try {
            Class.forName(driverClassName);
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Connection openConnection() {
        return getConnection(DRIVER_CLASS_NAME, DB_URL, USERNAME, PASSWORD);
    }

    public void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
