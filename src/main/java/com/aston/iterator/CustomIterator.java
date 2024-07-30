package com.aston.iterator;

import com.aston.list.CustomArrayList;

import java.util.Iterator;

/**
 * Класс, предоставляющий функционал итератора
 *
 * @see CustomArrayList
 */
public class CustomIterator<E> implements Iterator<E> {

    private final Object[] elementData;
    private int index = 0;

    public CustomIterator(Object[] elementData) {
        this.elementData = elementData;
    }

    @Override
    public boolean hasNext() {
        return index < elementData.length;
    }

    @Override
    public E next() {
        return (E) elementData[index++];
    }
}
