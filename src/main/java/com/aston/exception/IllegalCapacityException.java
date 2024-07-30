package com.aston.exception;

import com.aston.constant.Constant;
import com.aston.list.CustomArrayList;

import static com.aston.constant.Constant.ILLEGAL_CAPACITY_EXCEPTION_MESSAGE_FORMAT;

/**
 * Исключение, генерируемое при недопустимом значении вместимости списка
 *
 * @see CustomArrayList
 * @see Constant
 */
public class IllegalCapacityException extends RuntimeException {

    private IllegalCapacityException(String message) {
        super(message);
    }

    public static IllegalCapacityException of(int capacity) {
        return new IllegalCapacityException(
                String.format(ILLEGAL_CAPACITY_EXCEPTION_MESSAGE_FORMAT, capacity)
        );
    }
}
