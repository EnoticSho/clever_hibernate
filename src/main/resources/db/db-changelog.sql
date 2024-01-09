--liquibase formatted sql

--changeset sergey:1
CREATE TABLE House
(
    id          SERIAL PRIMARY KEY,
    uuid UUID NOT NULL,
    area        FLOAT        NOT NULL,
    country     VARCHAR(100) NOT NULL,
    city        VARCHAR(100) NOT NULL,
    street      VARCHAR(100) NOT NULL,
    number      VARCHAR(10)  NOT NULL,
    create_date TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Person
(
    id              SERIAL PRIMARY KEY,
    uuid UUID NOT NULL,
    name            VARCHAR(100)                                 NOT NULL,
    surname         VARCHAR(100)                                 NOT NULL,
    sex             VARCHAR(6) CHECK (sex IN ('Male', 'Female')) NOT NULL,
    passport_series VARCHAR(10)                                  NOT NULL,
    passport_number VARCHAR(10)                                  NOT NULL,
    create_date     TIMESTAMP                                    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_date     TIMESTAMP                                    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (passport_series, passport_number)
);

CREATE TABLE HouseOwners
(
    house_id  INT,
    person_id INT,
    PRIMARY KEY (house_id, person_id),
    FOREIGN KEY (house_id) REFERENCES House (id),
    FOREIGN KEY (person_id) REFERENCES Person (id)
);

CREATE TABLE HouseResidents
(
    house_id  INT,
    person_id INT,
    PRIMARY KEY (house_id, person_id),
    FOREIGN KEY (house_id) REFERENCES House (id),
    FOREIGN KEY (person_id) REFERENCES Person (id)
);

--changeset sergey:2
INSERT INTO House (uuid, area, country, city, street, number, create_date)
VALUES ('a100abff-1b7d-4283-9a28-a1f3de605ee1', 100.0, 'Беларусь', 'Минск', 'Улица 1', '1', NOW()),
       ('189f0fc4-914c-4f66-a740-2d1745da0b99', 120.0, 'Беларусь', 'Минск', 'Улица 2', '2', NOW()),
       ('4c7a0cf7-4c19-46ba-9e71-f9a0136e66d1', 90.0, 'Беларусь', 'Минск', 'Улица 3', '3', NOW()),
       ('d3467f22-298d-4f65-b8dd-35558d07fc4f', 110.0, 'Беларусь', 'Минск', 'Улица 4', '4', NOW()),
       ('75596193-0a9a-497c-b257-34f8a676bba8', 130.0, 'Беларусь', 'Минск', 'Улица 5', '5', NOW());

INSERT INTO Person (uuid, name, surname, sex, passport_series, passport_number, create_date, update_date)
VALUES ('234f5678-f89c-23e4-b567-527765550000', 'Иван', 'Иванов', 'Male', 'AB', '123456', NOW(), NOW()),
       ('234f5678-f89c-23e4-b567-527765550001', 'Мария', 'Петрова', 'Female', 'CD', '234567', NOW(), NOW()),
       ('234f5678-f89c-23e4-b567-527765550002', 'Олег', 'Сидоров', 'Male', 'EF', '345678', NOW(), NOW()),
       ('234f5678-f89c-23e4-b567-527765550003', 'Анна', 'Васильева', 'Female', 'GH', '456789', NOW(), NOW()),
       ('234f5678-f89c-23e4-b567-527765550004', 'Сергей', 'Алексеев', 'Male', 'IJ', '567890', NOW(), NOW()),
       ('234f5678-f89c-23e4-b567-527765550005', 'Елена', 'Дмитриева', 'Female', 'KL', '678901', NOW(), NOW()),
       ('234f5678-f89c-23e4-b567-527765550006', 'Алексей', 'Николаев', 'Male', 'MN', '789012', NOW(), NOW()),
       ('234f5678-f89c-23e4-b567-527765550007', 'Наталья', 'Максимова', 'Female', 'OP', '890123', NOW(), NOW()),
       ('234f5678-f89c-23e4-b567-527765550008', 'Дмитрий', 'Ларионов', 'Male', 'QR', '901234', NOW(), NOW()),
       ('234f5678-f89c-23e4-b567-527765550009', 'Светлана', 'Кузнецова', 'Female', 'ST', '012345', NOW(), NOW());

INSERT INTO HouseOwners (house_id, person_id)
VALUES (2, 1),
       (3, 2),
       (3, 3),
       (4, 4);

INSERT INTO HouseResidents (house_id, person_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (3, 4),
       (4, 5),
       (1, 6),
       (2, 7),
       (4, 8),
       (1, 9),
       (2, 10);
