package com.aston.dao.repository;

import com.aston.dao.entity.Address;
import com.aston.dao.repository.impl.AddressRepositoryImpl;

/**
 * Интерфейс репозитория для работы с {@link Address}
 *
 * @see AddressRepositoryImpl
 */
public interface AddressRepository {

    Address findById(Long id, String sqlQuery);
}
