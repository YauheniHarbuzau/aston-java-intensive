package com.aston.constant;

import com.aston.exception.IllegalCapacityException;
import com.aston.exception.IndexOutOfBoundsForListSizeException;
import com.aston.list.CustomArrayList;

/**
 * Класс для хранения констант
 *
 * @see CustomArrayList
 * @see IllegalCapacityException
 * @see IndexOutOfBoundsForListSizeException
 */
public class Constant {

    public static final int DEFAULT_ARRAY_LIST_CAPACITY = 10;
    public static final Object[] DEFAULT_ARRAY_LIST_ELEMENT_DATA = new Object[]{};

    public static final String ILLEGAL_CAPACITY_EXCEPTION_MESSAGE_FORMAT = "Illegal list capacity: %s";
    public static final String INDEX_OUT_OF_BOUNDS_FOR_LIST_SIZE_EXCEPTION_MESSAGE_FORMAT = "Index %s out of bounds for list size %s";
}
