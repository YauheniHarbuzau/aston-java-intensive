package com.aston.util;

import com.aston.Main;
import com.aston.constant.Constant;
import lombok.experimental.UtilityClass;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.aston.constant.Constant.DB_URL;
import static com.aston.constant.Constant.DRIVER_CLASS_NAME;
import static com.aston.constant.Constant.PASSWORD;
import static com.aston.constant.Constant.SQL_CREATE_DATABASE;
import static com.aston.constant.Constant.SQL_CREATE_TABLES;
import static com.aston.constant.Constant.SQL_DROP_DATABASE;
import static com.aston.constant.Constant.SQL_INSERT_INTO_TABLES;
import static com.aston.constant.Constant.URL;
import static com.aston.constant.Constant.USERNAME;

/**
 * Утилитарный класс для создания и заполнения базы данных
 *
 * @see Main
 * @see ConnectionUtil
 * @see Constant
 */
@UtilityClass
public class InitDatabaseUtil {

    private Connection connection;

    public void createDatabaseWithTables() {
        try {
            createDatabase();
            createTables();
            insertIntoTables();
        } catch (Exception ex) {
            dropDatabase();
            createDatabaseWithTables();
        }
    }

    private void createDatabase() {
        process(DRIVER_CLASS_NAME, URL, USERNAME, PASSWORD, SQL_CREATE_DATABASE);
    }

    private void createTables() {
        process(DRIVER_CLASS_NAME, DB_URL, USERNAME, PASSWORD, SQL_CREATE_TABLES);
    }

    private void insertIntoTables() {
        process(DRIVER_CLASS_NAME, DB_URL, USERNAME, PASSWORD, SQL_INSERT_INTO_TABLES);
    }

    private void dropDatabase() {
        process(DRIVER_CLASS_NAME, URL, USERNAME, PASSWORD, SQL_DROP_DATABASE);
    }

    private void process(String driverClassName, String url, String username, String password, String sql) {
        try (var connection = ConnectionUtil.getConnection(driverClassName, url, username, password);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
