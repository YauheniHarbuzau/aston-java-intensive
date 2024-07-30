# Кастомный ArrayList

(ASTON JAVA INTENSIVE)

Параметры приложения, библиотеки и зависимости:

- Java 17
- Gradle 8.8
- Lombok Plugin 6.5.1
- JUnit Jupiter 5.7.0

Реализованы методы:

```java
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

Iterator<E> iterator();

String toString();
```