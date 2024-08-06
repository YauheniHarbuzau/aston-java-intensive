package com.aston.constant;

import com.aston.dao.repository.impl.BeerRepositoryImpl;
import com.aston.dao.repository.impl.PubRepositoryImpl;
import com.aston.exception.EntityNotFoundException;
import com.aston.exception.RequestValidationException;
import com.aston.service.dto.response.BeerResponse;
import com.aston.service.dto.response.BeerResponseWithPubs;
import com.aston.service.dto.response.PubResponse;
import com.aston.service.dto.response.PubResponseWithBeers;
import com.aston.util.ApacheTomcatEmbedUtil;
import com.aston.util.ConnectionUtil;
import com.aston.util.InitDatabaseUtil;
import com.aston.util.YamlUtil;

/**
 * Класс для хранения констант
 *
 * @see PubRepositoryImpl
 * @see BeerRepositoryImpl
 * @see EntityNotFoundException
 * @see RequestValidationException
 * @see PubResponse
 * @see PubResponseWithBeers
 * @see BeerResponse
 * @see BeerResponseWithPubs
 * @see YamlUtil
 * @see ConnectionUtil
 * @see InitDatabaseUtil
 * @see ApacheTomcatEmbedUtil
 */
public class Constant {

    public static final String APPLICATION_YML_FILE_NAME = "application.yml";

    public static final String DRIVER_CLASS_NAME = YamlUtil.getDatasource().get("driver-class-name");
    public static final String URL = YamlUtil.getDatasource().get("url");
    public static final String DB_URL = YamlUtil.getDatasource().get("db-url");
    public static final String USERNAME = YamlUtil.getDatasource().get("username");
    public static final String PASSWORD = YamlUtil.getDatasource().get("password");

    public static final String PUB_CONTROLLER_NAME = "pubController";
    public static final String BEER_CONTROLLER_NAME = "beerController";

    public static final String PUB_SERVLET_PATTERN = "/api/v1/pubs";
    public static final String BEER_SERVLET_PATTERN = "/api/v1/beers";

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss:SSS";

    public static final String ENTITY_NOT_FOUND_EXCEPTION_MESSAGE_FORMAT = "%s not found by: %s";
    public static final String REQUEST_VALIDATION_EXCEPTION_MESSAGE_FORMAT = "%s not valid: %s";

    public static final String SQL_CREATE_DATABASE = "CREATE DATABASE pubs_base;";
    public static final String SQL_DROP_DATABASE = "DROP DATABASE pubs_base;";

    private static final String SQL_CREATE_PUB_ADDRESSES_TABLE = """
            CREATE TABLE IF NOT EXISTS pub_addresses
            (
                id      BIGSERIAL PRIMARY KEY NOT NULL,
                country VARCHAR(255)          NOT NULL,
                region  VARCHAR(255)          NOT NULL,
                city    VARCHAR(255)          NOT NULL,
                street  VARCHAR(255)          NOT NULL,
                number  VARCHAR(255)          NOT NULL
            );
            """;

    private static final String SQL_CREATE_PUBS_TABLE = """
            CREATE TABLE IF NOT EXISTS pubs
            (
                id             BIGSERIAL PRIMARY KEY               NOT NULL,
                uuid           UUID                                NOT NULL UNIQUE,
                name           VARCHAR(255)                        NOT NULL,
                description    TEXT                                NOT NULL,
                pub_address_id BIGINT REFERENCES pub_addresses(id) NOT NULL,
                create_date    TIMESTAMP WITHOUT TIME ZONE         NOT NULL,
                update_date    TIMESTAMP WITHOUT TIME ZONE         NOT NULL
            );
            """;

    private static final String SQL_CREATE_FACTORY_ADDRESSES_TABLE = """
            CREATE TABLE IF NOT EXISTS factory_addresses
            (
                id      BIGSERIAL PRIMARY KEY NOT NULL,
                country VARCHAR(255)          NOT NULL,
                region  VARCHAR(255)          NOT NULL,
                city    VARCHAR(255)          NOT NULL,
                street  VARCHAR(255)          NOT NULL,
                number  VARCHAR(255)          NOT NULL
            );
            """;

    private static final String SQL_CREATE_BEERS_TABLE = """
            CREATE TABLE IF NOT EXISTS beers
            (
                id                 BIGSERIAL PRIMARY KEY                   NOT NULL,
                uuid               UUID                                    NOT NULL UNIQUE,
                name               VARCHAR(255)                            NOT NULL,
                description        TEXT                                    NOT NULL,
                factory            VARCHAR(255)                            NOT NULL,
                factory_address_id BIGINT REFERENCES factory_addresses(id) NOT NULL,
                ABV                DECIMAL                                 NOT NULL,
                OG                 DECIMAL                                 NOT NULL,
                IBU                INTEGER                                 NOT NULL,
                create_date        TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
                update_date        TIMESTAMP WITHOUT TIME ZONE             NOT NULL
            );
            """;

    private static final String SQL_CREATE_PUBS_BEERS_TABLE = """
            CREATE TABLE IF NOT EXISTS pubs_beers
            (
                pub_id  BIGINT NOT NULL REFERENCES pubs(id)  ON DELETE CASCADE,
                beer_id BIGINT NOT NULL REFERENCES beers(id) ON DELETE CASCADE,
                PRIMARY KEY (pub_id, beer_id)
            );
            """;

    public static final String SQL_CREATE_TABLES = SQL_CREATE_PUB_ADDRESSES_TABLE +
                                                   SQL_CREATE_PUBS_TABLE +
                                                   SQL_CREATE_FACTORY_ADDRESSES_TABLE +
                                                   SQL_CREATE_BEERS_TABLE +
                                                   SQL_CREATE_PUBS_BEERS_TABLE;

    private static final String SQL_INSERT_INTO_PUB_ADDRESSES_TABLE = """
            INSERT INTO pub_addresses (country, region, city, street, number)
            VALUES ('Country', 'Region', 'City', 'Street', 'Number'),
                   ('Country', 'Region', 'City', 'Street', 'Number'),
                   ('Country', 'Region', 'City', 'Street', 'Number'),
                   ('Country', 'Region', 'City', 'Street', 'Number'),
                   ('Country', 'Region', 'City', 'Street', 'Number');
            """;

    private static final String SQL_INSERT_INTO_PUBS_TABLE = """
            INSERT INTO pubs (uuid, name, description, pub_address_id, create_date, update_date)
            VALUES ('8c0940bd-796a-4ae4-8791-933552a8ea76', 'Pub 1', 'Description', 1, '2024-08-04 10:00:00', '2024-08-04 10:00:00'),
                   ('9abdd85a-5b23-4a80-9230-79d12d88c621', 'Pub 2', 'Description', 2, '2024-08-04 10:00:00', '2024-08-04 10:00:00'),
                   ('2eb28a75-fb83-4669-8a06-562a5a5a6a05', 'Pub 3', 'Description', 3, '2024-08-04 10:00:00', '2024-08-04 10:00:00'),
                   ('87b37549-7d42-45e8-a53a-ddc48c7fc334', 'Pub 4', 'Description', 4, '2024-08-04 10:00:00', '2024-08-04 10:00:00'),
                   ('526144c8-568b-4170-aa29-bbee0aba3684', 'Pub 5', 'Description', 5, '2024-08-04 10:00:00', '2024-08-04 10:00:00');
            """;

    private static final String SQL_INSERT_INTO_FACTORY_ADDRESSES_TABLE = """
            INSERT INTO factory_addresses (country, region, city, street, number)
            VALUES ('Country', 'Region', 'City', 'Street', 'Number'),
                   ('Country', 'Region', 'City', 'Street', 'Number');
            """;

    private static final String SQL_INSERT_INTO_BEERS_TABLE = """
            INSERT INTO beers (uuid, name, description, factory, factory_address_id, ABV, OG, IBU, create_date, update_date)
            VALUES ('b184082b-676d-495b-958f-92252a3b256e', 'Beer 1','Description', 'Factory', 1, 5.0, 11.5, 15, '2024-08-04 10:00:00', '2024-08-04 10:00:00'),
                   ('9afeb22d-8055-4621-b331-84578efc6d5c', 'Beer 2', 'Description', 'Factory', 1, 5.0, 11.5, 15, '2024-08-04 10:00:00', '2024-08-04 10:00:00'),
                   ('15f80296-a004-4842-a950-3b87b7a0abc4', 'Beer 3', 'Description', 'Factory', 1, 5.0, 11.5, 15, '2024-08-04 10:00:00', '2024-08-04 10:00:00'),
                   ('781e2fd1-5dee-459f-9ae9-c99c989d9199', 'Beer 4', 'Description', 'Factory', 1, 5.0, 11.5, 15, '2024-08-04 10:00:00', '2024-08-04 10:00:00'),
                   ('49cb7399-980c-4d04-b31b-c2f3bc359e4d', 'Beer 5', 'Description', 'Factory', 1, 5.0, 11.5, 15, '2024-08-04 10:00:00', '2024-08-04 10:00:00'),
                   ('2499c5b9-a965-4cfe-9783-20b1a46c4801', 'Beer 6', 'Description', 'Factory', 2, 5.0, 11.5, 15, '2024-08-04 10:00:00', '2024-08-04 10:00:00'),
                   ('5a82563c-138c-426d-9cd6-f1f9ff5927bb', 'Beer 7', 'Description', 'Factory', 2, 5.0, 11.5, 15, '2024-08-04 10:00:00', '2024-08-04 10:00:00'),
                   ('3c08b39a-d93d-4b82-a432-1c1c2dc18870', 'Beer 8', 'Description', 'Factory', 2, 5.0, 11.5, 15, '2024-08-04 10:00:00', '2024-08-04 10:00:00'),
                   ('2b8adf00-7cf7-4787-85c9-e8f1fe35e362', 'Beer 9', 'Description', 'Factory', 2, 5.0, 11.5, 15, '2024-08-04 10:00:00', '2024-08-04 10:00:00'),
                   ('ea2ef8b5-6a2e-4a9d-a67d-83c587d2d92f', 'Beer 10', 'Description', 'Factory', 2, 5.0, 11.5, 15, '2024-08-04 10:00:00', '2024-08-04 10:00:00');
            """;

    private static final String SQL_INSERT_INTO_PUBS_BEERS_TABLE = """
            INSERT INTO pubs_beers (pub_id, beer_id)
            VALUES (1, 1),
                   (1, 2),
                   (1, 3),
                   (1, 4),
                   (1, 5),
                   (2, 2),
                   (2, 3),
                   (2, 4),
                   (2, 5),
                   (2, 6),
                   (3, 3),
                   (3, 4),
                   (3, 5),
                   (3, 6),
                   (3, 7),
                   (4, 4),
                   (4, 5),
                   (4, 6),
                   (4, 7),
                   (4, 8),
                   (5, 5),
                   (5, 6),
                   (5, 7),
                   (5, 8),
                   (5, 9),
                   (5, 10);
            """;

    public static final String SQL_INSERT_INTO_TABLES = SQL_INSERT_INTO_PUB_ADDRESSES_TABLE +
                                                        SQL_INSERT_INTO_PUBS_TABLE +
                                                        SQL_INSERT_INTO_FACTORY_ADDRESSES_TABLE +
                                                        SQL_INSERT_INTO_BEERS_TABLE +
                                                        SQL_INSERT_INTO_PUBS_BEERS_TABLE;

    public static final String SQL_PUB_ADDRESS_FIND_BY_ID = """
            SELECT *
            FROM pub_addresses
            WHERE id = ?;
            """;

    public static final String SQL_PUB_FIND_BY_UUID = """
            SELECT *
            FROM pubs
            WHERE uuid = ?;
            """;

    public static final String SQL_PUB_FIND_ALL = """
            SELECT *
            FROM pubs;
            """;

    public static final String SQL_PUB_FIND_ALL_BY_BEER_UUID = """
            SELECT *
            FROM pubs p
            INNER JOIN pubs_beers pb
                ON p.id = pb.pub_id
            INNER JOIN beers b
                ON pb.beer_id = b.id
            WHERE b.uuid = ?;
            """;

    public static final String SQL_PUB_CREATE = """
            WITH created_pub_address_id AS (INSERT INTO pub_addresses (country, region, city, street, number)
                                            VALUES (?, ?, ?, ?, ?)
                                            RETURNING id)
            INSERT INTO pubs (uuid, name, description, pub_address_id, create_date, update_date)
            SELECT ?, ?, ?, id, ?, ?
            FROM created_pub_address_id;
            """;

    public static final String SQL_PUB_UPDATE = """
            WITH updated AS (UPDATE pubs
                             SET name = ?, description = ?, update_date = ?
                             WHERE uuid = ?
                             RETURNING pub_address_id)
            UPDATE pub_addresses
            SET country = ?, region = ?, city = ?, street = ?, number = ?
            FROM updated
            WHERE id = pub_address_id;
            """;

    public static final String SQL_PUB_DELETE_BY_UUID = """
            DELETE FROM pubs
            WHERE uuid = ?;
            """;

    public static final String SQL_FACTORY_ADDRESS_FIND_BY_ID = """
            SELECT *
            FROM factory_addresses
            WHERE id = ?;
            """;

    public static final String SQL_BEER_FIND_BY_UUID = """
            SELECT *
            FROM beers
            WHERE uuid = ?;
            """;

    public static final String SQL_BEER_FIND_ALL = """
            SELECT *
            FROM beers;
            """;

    public static final String SQL_BEER_FIND_ALL_BY_PUB_UUID = """
            SELECT *
            FROM beers b
            INNER JOIN pubs_beers pb
                ON b.id = pb.beer_id
            INNER JOIN pubs p
                ON pb.pub_id = p.id
            WHERE p.uuid = ?;
            """;

    public static final String SQL_BEER_CREATE = """
            WITH created_factory_address_id AS (INSERT INTO factory_addresses (country, region, city, street, number)
                                                VALUES (?, ?, ?, ?, ?)
                                                RETURNING id)
            INSERT INTO beers (uuid, name, description, factory, factory_address_id, ABV, OG, IBU, create_date, update_date)
            SELECT ?, ?, ?, ?, id, ?, ?, ?, ?, ?
            FROM created_factory_address_id;
            """;

    public static final String SQL_BEER_UPDATE = """
            WITH updated AS (UPDATE beers
                             SET name = ?, description = ?, factory = ?, ABV = ?, OG = ?, IBU = ?, update_date = ?
                             WHERE uuid = ?
                             RETURNING factory_address_id)
            UPDATE factory_addresses
            SET country = ?, region = ?, city = ?, street = ?, number = ?
            FROM updated
            WHERE id = factory_address_id;
            """;

    public static final String SQL_BEER_DELETE_BY_UUID = """
            DELETE FROM beers
            WHERE uuid = ?;
            """;
}
