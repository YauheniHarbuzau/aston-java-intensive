package com.aston.list;

import com.aston.constant.Constant;
import com.aston.exception.IllegalCapacityException;
import com.aston.exception.IndexOutOfBoundsForListSizeException;
import com.aston.iterator.CustomIterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Objects;

import static com.aston.constant.Constant.DEFAULT_ARRAY_LIST_CAPACITY;
import static com.aston.constant.Constant.DEFAULT_ARRAY_LIST_ELEMENT_DATA;

/**
 * Класс, реализующий функционал списка, аналогичный {@link ArrayList}
 *
 * @see CustomList
 * @see CustomIterator
 * @see Constant
 * @see IllegalCapacityException
 * @see IndexOutOfBoundsForListSizeException
 */
public class CustomArrayList<E> implements CustomList<E> {

    private Object[] elementData;
    private int capacity;
    private int size = 0;

    public CustomArrayList() {
        this.capacity = DEFAULT_ARRAY_LIST_CAPACITY;
        this.elementData = DEFAULT_ARRAY_LIST_ELEMENT_DATA;
    }

    public CustomArrayList(int capacity) {
        if (capacity > 0) {
            this.capacity = capacity;
        } else if (capacity == 0) {
            this.capacity = DEFAULT_ARRAY_LIST_CAPACITY;
        } else {
            throw IllegalCapacityException.of(capacity);
        }
        this.elementData = DEFAULT_ARRAY_LIST_ELEMENT_DATA;
    }

    /**
     * Добавление в список элемента
     *
     * @param element добавляемый элемент
     * @see #add(int, Object)
     * @see #grow()
     */
    @Override
    public void add(E element) {
        grow();

        Object[] oldElementData = elementData;
        elementData = new Object[oldElementData.length + 1];
        System.arraycopy(oldElementData, 0, elementData, 0, oldElementData.length);
        elementData[elementData.length - 1] = element;

        size++;
    }

    /**
     * Добавление в список элемента в указанную позицию (индекс)
     *
     * @param index   позиция (индекс) вставки элемента
     * @param element добавляемый элемент
     * @see #checkIndex(int, int)
     * @see #add(Object)
     */
    @Override
    public void add(int index, E element) {
        checkIndex(index, size + 1);

        if (index == size) {
            add(element);
        } else {
            Object[] oldElementData = elementData;
            elementData = new Object[index];
            System.arraycopy(oldElementData, 0, elementData, 0, elementData.length);

            add(element);

            Object[] leftElementData = elementData;
            elementData = new Object[oldElementData.length + 1];
            System.arraycopy(leftElementData, 0, elementData, 0, leftElementData.length);
            System.arraycopy(oldElementData, index, elementData, index + 1, oldElementData.length - index);
        }
    }

    /**
     * Добавление в список всех элементов из передаваемой коллекции
     *
     * @param collection коллекция, содержащая элементы для добавления в список
     */
    @Override
    public void addAll(Collection<? extends E> collection) {
        Object[] addedArray = collection.toArray();
        Object[] oldElementData = elementData;

        capacity = oldElementData.length + addedArray.length;
        elementData = new Object[capacity];

        System.arraycopy(oldElementData, 0, elementData, 0, oldElementData.length);
        System.arraycopy(addedArray, 0, elementData, oldElementData.length, addedArray.length);
        size = capacity;
    }

    /**
     * Получение из списка элемента по позиции (индексу)
     *
     * @param index позиция (индекс) искомого элемента
     * @return искомый элемент
     * @see #checkIndex(int, int)
     */
    @Override
    public E get(int index) {
        checkIndex(index, size);
        return (E) elementData[index];
    }

    /**
     * Обновление в списке элемента по позиции (индексу)
     *
     * @param index   позиция (индекс) обновляемого элемента
     * @param element новое значение элемента
     * @return обновляемое и обновленное значения элемента
     * @see #checkIndex(int, int)
     */
    @Override
    public E[] set(int index, E element) {
        checkIndex(index, size);

        Object oldElement = elementData[index];
        elementData[index] = element;
        return (E[]) new Object[]{oldElement, element};
    }

    /**
     * Удаление из списка элемента по позиции (индексу)
     *
     * @param index позиция (индекс) удаляемого элемента
     * @see #checkIndex(int, int)
     */
    @Override
    public void remove(int index) {
        checkIndex(index, size);

        Object[] oldElementData = elementData;
        elementData = new Object[oldElementData.length - 1];
        System.arraycopy(oldElementData, 0, elementData, 0, index);
        System.arraycopy(oldElementData, index + 1, elementData, index, oldElementData.length - index - 1);
        size--;
    }

    /**
     * Удаление из списка первого элемента, совпадающего по значению
     *
     * @param object значение удаляемого элемента
     * @see #getIndexForElement(Object)
     */
    @Override
    public void remove(Object object) {
        int index = getIndexForElement(object);
        if (index < 0) {
            return;
        }
        remove(index);
    }

    /**
     * Очистка списка от всех элементов
     */
    @Override
    public void clear() {
        elementData = DEFAULT_ARRAY_LIST_ELEMENT_DATA;
        size = 0;
    }

    /**
     * Проверка путой список или содержит элементы
     *
     * @return булево значение true/false (пустой список или содержит элементы)
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Получение размера списка
     *
     * @return размер списка
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Получение вместимости списка
     *
     * @return вместимость списка
     */
    @Override
    public int capacity() {
        return capacity;
    }

    /**
     * Сортировка списка с использованием компаратора
     *
     * @param comparator компаратор, задающий условие сравнения элементов списка
     * @see #quickSort(Object[], int, int, Comparator)
     */
    @Override
    public void sort(Comparator<? super E> comparator) {
        quickSort((E[]) elementData, 0, elementData.length - 1, comparator);
    }

    /**
     * Предоставление для списка итератора
     *
     * @return итератор для списка
     */
    @Override
    public Iterator<E> iterator() {
        return new CustomIterator<>(elementData);
    }

    /**
     * Представление списка в формате String
     *
     * @return список в формате String
     */
    @Override
    public String toString() {
        return Arrays.toString(elementData);
    }

    /**
     * Вспомогательный метод для проверки позиции (индекса) элемента в списке
     *
     * @param index позиция (индекс) элемента в списке
     * @param size  размер списка
     * @see #add(int, Object)
     * @see #get(int)
     * @see #set(int, Object)
     * @see #remove(int)
     */
    private void checkIndex(int index, int size) {
        try {
            Objects.checkIndex(index, size);
        } catch (IndexOutOfBoundsException ex) {
            throw IndexOutOfBoundsForListSizeException.of(index, size);
        }
    }

    /**
     * Вспомогательный метод для увеличения вместимости списка
     *
     * @see #add(Object)
     */
    private void grow() {
        if (size >= capacity) {
            capacity = (int) (capacity * 1.5) + 1;
        }
    }

    /**
     * Вспомогательный метод для поиска позиции (индекса) элемента по его значению
     *
     * @param element значение элемента
     * @return позиция (индекс) элемента
     * @see #remove(Object)
     */
    private int getIndexForElement(Object element) {
        if (element == null) {
            return -1;
        }

        for (int i = 0; i < size; i++) {
            if (element.equals(elementData[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Вспомогательный метод, предоставляющий функционал алгоритма быстрой сортировки
     *
     * @see #sort(Comparator)
     * @see #partition(Object[], int, int, Comparator)
     */
    private void quickSort(E[] arr, int begin, int end, Comparator<? super E> comparator) {
        if (begin < end) {
            int partitionIndex = partition(arr, begin, end, comparator);

            quickSort(arr, begin, partitionIndex - 1, comparator);
            quickSort(arr, partitionIndex + 1, end, comparator);
        }
    }

    /**
     * Вспомогательный метод, предоставляющий функционал алгоритма быстрой сортировки
     *
     * @see #quickSort(Object[], int, int, Comparator)
     */
    private int partition(E[] arr, int begin, int end, Comparator<? super E> comparator) {
        E pivot = arr[end];
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (comparator.compare(arr[j], pivot) < 0) {
                i++;

                E swapTemp = arr[i];
                arr[i] = arr[j];
                arr[j] = swapTemp;
            }
        }

        E swapTemp = arr[i + 1];
        arr[i + 1] = arr[end];
        arr[end] = swapTemp;

        return i + 1;
    }
}
