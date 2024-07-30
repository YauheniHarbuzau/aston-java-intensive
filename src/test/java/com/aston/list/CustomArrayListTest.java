package com.aston.list;

import com.aston.exception.IndexOutOfBoundsForListSizeException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тестовый класс для {@link CustomArrayList}
 */
class CustomArrayListTest {

    @Nested
    class AddElementTest {
        @Test
        void checkAddShouldIncreaseListSize() {
            // given
            CustomList<String> list = new CustomArrayList<>();
            int listSizeBeforeAdding = list.size();

            // when
            list.add("0");
            int listSizeAfterAdding = list.size();

            // then
            assertAll(
                    () -> assertEquals(0, listSizeBeforeAdding),
                    () -> assertEquals(1, listSizeAfterAdding)
            );
        }
    }

    @Nested
    class AddElementByIndexTest {
        @Test
        void checkAddShouldIncreaseListSize() {
            // given
            CustomList<String> list = new CustomArrayList<>();
            list.add("2");
            int listSizeBeforeAdding = list.size();

            // when
            list.add(0, "0"); // adding to beginning
            list.add(1, "1"); // adding to middle
            list.add(3, "3"); // adding to end
            int listSizeAfterAdding = list.size();

            // then
            assertAll(
                    () -> assertEquals(1, listSizeBeforeAdding),
                    () -> assertEquals(4, listSizeAfterAdding)
            );
        }

        @Test
        void checkAddShouldThrowIndexOutOfBoundsForListSizeException() {
            // given
            CustomList<String> list = new CustomArrayList<>();
            list.add("0");
            list.add("1");

            // when, then
            assertAll(
                    () -> assertThrows(IndexOutOfBoundsForListSizeException.class, () -> list.add(3, "3")),
                    () -> assertThrows(IndexOutOfBoundsForListSizeException.class, () -> list.add(-1, "-1"))
            );
        }
    }

    @Nested
    class AddAllTest {
        @Test
        void checkAddAllShouldWorkCorrectly() {
            // given
            CustomList<String> list = new CustomArrayList<>();
            list.add("0");
            List<String> addedList = List.of("1", "2", "3");

            // when
            list.addAll(addedList);

            // then
            assertAll(
                    () -> assertEquals("0", list.get(0)),
                    () -> assertEquals("1", list.get(1)),
                    () -> assertEquals("2", list.get(2)),
                    () -> assertEquals("3", list.get(3)),
                    () -> assertEquals(4, list.capacity()),
                    () -> assertEquals(4, list.size())
            );
        }
    }

    @Nested
    class GetTest {
        @Test
        void checkGetShouldReturnCorrectResult() {
            // given
            CustomList<String> list = new CustomArrayList<>();
            list.add("0");
            list.add("1");
            list.add("2");
            list.add("3");

            // when, then
            assertAll(
                    () -> assertEquals("0", list.get(0)),
                    () -> assertEquals("1", list.get(1)),
                    () -> assertEquals("2", list.get(2)),
                    () -> assertEquals("3", list.get(3))
            );
        }

        @Test
        void checkGetShouldThrowIndexOutOfBoundsForListSizeException() {
            // given
            CustomList<String> list = new CustomArrayList<>();
            list.add("0");
            list.add("1");

            // when, then
            assertAll(
                    () -> assertThrows(IndexOutOfBoundsForListSizeException.class, () -> list.get(2)),
                    () -> assertThrows(IndexOutOfBoundsForListSizeException.class, () -> list.get(-1))
            );
        }
    }

    @Nested
    class SetTest {
        @Test
        void checkSetShouldWorkCorrectly() {
            // given
            CustomList<String> list = new CustomArrayList<>();
            list.add("0");

            // when
            Object[] setResult = list.set(0, "1");

            // then
            assertAll(
                    () -> assertEquals("1", list.get(0)),
                    () -> assertEquals("0", setResult[0]),
                    () -> assertEquals("1", setResult[1])
            );
        }

        @Test
        void checkSetShouldThrowIndexOutOfBoundsForListSizeException() {
            // given
            CustomList<String> list = new CustomArrayList<>();
            list.add("0");
            list.add("1");

            // when, then
            assertAll(
                    () -> assertThrows(IndexOutOfBoundsForListSizeException.class, () -> list.set(2, "2")),
                    () -> assertThrows(IndexOutOfBoundsForListSizeException.class, () -> list.set(-1, "-1"))
            );
        }
    }

    @Nested
    class RemoveByIndexTest {
        @Test
        void checkRemoveShouldWorkCorrectly() {
            // given
            CustomList<String> list = new CustomArrayList<>();
            list.add("0");
            list.add("1");
            list.add("2");
            int listSizeBeforeRemoving = list.size();

            // when
            list.remove(1);
            int listSizeAfterRemoving = list.size();

            // then
            assertAll(
                    () -> assertEquals("0", list.get(0)),
                    () -> assertEquals("2", list.get(1)),
                    () -> assertEquals(3, listSizeBeforeRemoving),
                    () -> assertEquals(2, listSizeAfterRemoving)
            );
        }

        @Test
        void checkRemoveShouldThrowIndexOutOfBoundsForListSizeException() {
            // given
            CustomList<String> list = new CustomArrayList<>();
            list.add("0");
            list.add("1");

            // when, then
            assertAll(
                    () -> assertThrows(IndexOutOfBoundsForListSizeException.class, () -> list.remove(2)),
                    () -> assertThrows(IndexOutOfBoundsForListSizeException.class, () -> list.remove(-1))
            );
        }
    }

    @Nested
    class RemoveByObjectValueTest {
        @Test
        void checkRemoveShouldWorkCorrectly() {
            // given
            CustomList<String> list = new CustomArrayList<>();
            list.add("0");
            list.add("1");
            list.add("2");
            list.add("1");
            list.add("2");
            int listSizeBeforeRemoving = list.size();

            // when
            list.remove("1");
            int listSizeAfterRemoving = list.size();

            // then
            assertAll(
                    () -> assertEquals("0", list.get(0)),
                    () -> assertEquals("2", list.get(1)),
                    () -> assertEquals("1", list.get(2)),
                    () -> assertEquals("2", list.get(3)),
                    () -> assertEquals(5, listSizeBeforeRemoving),
                    () -> assertEquals(4, listSizeAfterRemoving)
            );
        }
    }

    @Nested
    class ClearTest {
        @Test
        void checkClearShouldWorkCorrectly() {
            // given
            CustomList<String> list = new CustomArrayList<>();
            list.add("0");
            list.add("1");
            list.add("2");
            list.add("3");

            // when
            list.clear();

            // then
            assertAll(
                    () -> assertEquals(0, list.size()),
                    () -> assertTrue(list.isEmpty())
            );
        }
    }

    @Nested
    class IsEmptyTest {
        @Test
        void checkIsEmptyShouldReturnTrue() {
            // given
            CustomList<String> list = new CustomArrayList<>();

            // when, then
            assertTrue(list.isEmpty());
        }

        @Test
        void checkIsEmptyShouldReturnFalse() {
            // given
            CustomList<String> list = new CustomArrayList<>();
            list.add("0");

            // when, then
            assertFalse(list.isEmpty());
        }
    }

    @Nested
    class SizeTest {
        @Test
        void checkSizeShouldReturnCorrectResult() {
            // given
            CustomList<String> list1 = new CustomArrayList<>();
            CustomList<String> list2 = new CustomArrayList<>();
            list2.add("0");
            list2.add("1");
            list2.add("2");

            // when, then
            assertAll(
                    () -> assertEquals(0, list1.size()),
                    () -> assertEquals(3, list2.size())
            );
        }
    }

    @Nested
    class CapacityTest {
        @Test
        void checkCapacityShouldReturnCorrectResult() {
            // given
            CustomList<Integer> list1 = new CustomArrayList<>();
            CustomList<Integer> list2 = new CustomArrayList<>(0);
            CustomList<Integer> list3 = new CustomArrayList<>(5);
            CustomList<Integer> list4 = new CustomArrayList<>();
            list4.add(0);
            list4.add(1);
            list4.add(2);
            list4.add(3);
            list4.add(4);
            list4.add(5);
            list4.add(6);
            list4.add(7);
            list4.add(8);
            list4.add(9);
            list4.add(10);

            // when, then
            assertAll(
                    () -> assertEquals(10, list1.capacity()),
                    () -> assertEquals(10, list2.capacity()),
                    () -> assertEquals(5, list3.capacity()),
                    () -> assertEquals(16, list4.capacity())
            );
        }
    }

    @Nested
    class SortTest {
        @Test
        void checkSortShouldWorkCorrectly() {
            // given
            CustomList<String> list = new CustomArrayList<>();
            list.add("0");
            list.add("9");
            list.add("2");
            list.add("6");
            list.add("3");
            list.add("7");
            list.add("1");
            list.add("5");
            list.add("8");
            list.add("4");

            // when
            list.sort(String::compareTo);

            // then
            assertAll(
                    () -> assertEquals("0", list.get(0)),
                    () -> assertEquals("1", list.get(1)),
                    () -> assertEquals("2", list.get(2)),
                    () -> assertEquals("3", list.get(3)),
                    () -> assertEquals("4", list.get(4)),
                    () -> assertEquals("5", list.get(5)),
                    () -> assertEquals("6", list.get(6)),
                    () -> assertEquals("7", list.get(7)),
                    () -> assertEquals("8", list.get(8)),
                    () -> assertEquals("9", list.get(9))
            );
        }
    }
}
