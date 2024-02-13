Учет Домов и Жильцов - Веб-приложение
Обзор

Данное веб-приложение разработано для учета домов и жильцов. Оно предоставляет REST API для выполнения операций создания, чтения, обновления и удаления (CRUD) для сущностей House и Person.
Основные Сущности
House

    Описание: Дома с уникальными идентификаторами и адресами.
    Поля: id, uuid, area, country, city, street, number, create_date.
    Отношения: Может иметь множество жильцов и владельцев.

Person

    Описание: Люди с уникальными идентификаторами и паспортными данными.
    Поля: id, uuid, name, surname, sex, passport_series, passport_number, create_date, update_date.
    Отношения: Обязаны жить в одном доме; могут владеть несколькими домами.

Особенности REST API

    CRUD для House: Полная поддержка CRUD операций. В GET запросах информация о Person не выводится.
    CRUD для Person: Полная поддержка CRUD операций. В GET запросах информация о House не выводится.
    Pagination: Для GET операций используется пагинация с размером страницы по умолчанию - 15 элементов.

Дополнительные Функции

    Получение всех Person, проживающих в конкретном House.
    Получение всех House, владельцем которых является конкретный Person.

Работа с Репозиторием

    Используется JDBC Template для одного из методов.

Установка и запуск

Запустите Docker, используя команду docker-compose up. Это создаст базу данных и tomcat сервер.
Далее используйте запросы для получения информации:
- (GET) http://localhost:8080/myapp/api/houses получение списка домов
- (GET) http://localhost:8080/myapp/api/houses/{uuid} получение дома по uuid
- (DELETE) http://localhost:8080/myapp/api/houses/{uuid} удаление дома
- (POST) http://localhost:8080/myapp/products создание продукта
- (PUT) http://localhost:8080/myapp/products/{uuid} обновление дома
- (Patch) http://localhost:8080/myapp/products/{uuid} обновление определенного поля/полей
- (Get) http://localhost:8080/myapp/api/houses/{uuid}/residents получение списка жильцов дома

- (GET) http://localhost:8080/myapp/api/persons получение списка жильцов
- (GET) http://localhost:8080/myapp/api/persons/{uuid} получение жильца по uuid
- (Delete) http://localhost:8080/myapp/api/persons/{uuid} удаление жильца
- (Post) http://localhost:8080/myapp/api/persons создание жильца
- (Put) http://localhost:8080/myapp/api/persons/{uuid} Обновление жильца
- (Get) http://localhost:8080/myapp/api/persons/{uuid}/owned-houses получение списка домов которыми владеет человек
