package com.aston.list;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Интерфейс, предоставляющий методы, аналогичные {@link List}
 *
 * @see CustomArrayList
 */
public interface CustomList<E> extends Iterable<E> {

    void add(E element);

    void add(int index, E element);

    void addAll(Collection<? extends E> collection);

    E get(int index);

    E[] set(int index, E element);

    void remove(int index);

    void remove(Object object);

    void clear();

    boolean isEmpty();

    int size();

    int capacity();

    void sort(Comparator<? super E> comparator);
}
