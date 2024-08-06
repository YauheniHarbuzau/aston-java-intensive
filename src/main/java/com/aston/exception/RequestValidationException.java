package com.aston.exception;

import com.aston.constant.Constant;
import com.aston.util.RequestValidationUtil;

import static com.aston.constant.Constant.REQUEST_VALIDATION_EXCEPTION_MESSAGE_FORMAT;

/**
 * Исключение, генерируемое при невалидных данных запроса
 *
 * @see RequestValidationUtil
 * @see Constant
 */
public class RequestValidationException extends RuntimeException {

    public RequestValidationException(String message) {
        super(message);
    }

    public static RequestValidationException of(Object objectRequest) {
        return new RequestValidationException(
                String.format(REQUEST_VALIDATION_EXCEPTION_MESSAGE_FORMAT, objectRequest.getClass().getSimpleName(), objectRequest)
        );
    }
}
