# PUBS BASE

(ASTON JAVA INTENSIVE)

***

### Параметры приложения, библиотеки и зависимости

- Java 17
- Gradle 8.8
- PostgreSQL JDBC Driver
- Apache Tomcat Embed 10.1.18
- SnakeYAML 2.2
- FasterXML Jackson 2.17.2
- MapStruct 1.5.5.Final
- Lombok Plugin 6.5.1
- JUnit Jupiter 5.7.0
- Mockito JUnit Jupiter 5.12.0
- AssertJ Core 3.24.2

```
! Может потребоваться настройка среды разработки под актуальные версии - Java 17, Gradle 8.8.
```

***

### Локальные порты

Для работы приложения требуются следующие локальные порты:

- **5432** - порт зарезервирован под базу данных.
- **8080** - порт зарезервирован под контейнер сервлетов Apache Tomcat Embed.

***

### Запуск и работа с приложением

Для работы приложения требуется СУБД PostgreSQL.

Точка входа в приложение - [Main-класс](src/main/java/com/aston/Main.java "Main.java")
(точка запуска приложения в работу).

При старте приложения база данных создается автоматически и
запускается контейнер сервлетов Apache Tomcat Embed.

***

### Примеры HTTP-запросов для работы с приложением

- [pubs.http](src/main/resources/http/pubs.http "pubs.http")
- [beers.http](src/main/resources/http/beers.http "beers.http")

***