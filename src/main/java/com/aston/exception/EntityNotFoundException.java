package com.aston.exception;

import com.aston.constant.Constant;
import com.aston.service.impl.BeerServiceImpl;
import com.aston.service.impl.PubServiceImpl;

import static com.aston.constant.Constant.ENTITY_NOT_FOUND_EXCEPTION_MESSAGE_FORMAT;

/**
 * Исключение, генерируемое при отсутствии данных
 *
 * @see PubServiceImpl
 * @see BeerServiceImpl
 * @see Constant
 */
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String message) {
        super(message);
    }

    public static EntityNotFoundException of(Class<?> entityClass, Object entityField) {
        return new EntityNotFoundException(
                String.format(ENTITY_NOT_FOUND_EXCEPTION_MESSAGE_FORMAT, entityClass.getSimpleName(), entityField)
        );
    }
}
