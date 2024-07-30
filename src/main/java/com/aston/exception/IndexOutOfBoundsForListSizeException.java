package com.aston.exception;

import com.aston.constant.Constant;
import com.aston.list.CustomArrayList;

import static com.aston.constant.Constant.INDEX_OUT_OF_BOUNDS_FOR_LIST_SIZE_EXCEPTION_MESSAGE_FORMAT;

/**
 * Исключение, генерируемое при недопустимом относительно размера списка индеска
 *
 * @see CustomArrayList
 * @see Constant
 */
public class IndexOutOfBoundsForListSizeException extends RuntimeException {

    private IndexOutOfBoundsForListSizeException(String message) {
        super(message);
    }

    public static IndexOutOfBoundsForListSizeException of(int index, int size) {
        return new IndexOutOfBoundsForListSizeException(
                String.format(INDEX_OUT_OF_BOUNDS_FOR_LIST_SIZE_EXCEPTION_MESSAGE_FORMAT, index, size)
        );
    }
}
