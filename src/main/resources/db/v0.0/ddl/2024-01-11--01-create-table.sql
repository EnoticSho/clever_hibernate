--liquibase formatted sql
--changeset sergey:1
CREATE TABLE House
(
    id          SERIAL PRIMARY KEY,
    uuid        UUID NOT NULL UNIQUE,
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
    uuid            UUID NOT NULL UNIQUE,
    name            VARCHAR(100)                                 NOT NULL,
    surname         VARCHAR(100)                                 NOT NULL,
    sex             VARCHAR(6) CHECK (sex IN ('MALE', 'FEMALE')) NOT NULL,
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
    FOREIGN KEY (house_id) REFERENCES House (id) ON DELETE CASCADE,
    FOREIGN KEY (person_id) REFERENCES Person (id) ON DELETE CASCADE
);

CREATE TABLE HouseResidents
(
    house_id  INT,
    person_id INT,
    PRIMARY KEY (house_id, person_id),
    FOREIGN KEY (house_id) REFERENCES House (id) ON DELETE CASCADE,
    FOREIGN KEY (person_id) REFERENCES Person (id) ON DELETE CASCADE
);
