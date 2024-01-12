package ru.clevertec.ecl.entity.listeners;

import jakarta.persistence.PrePersist;
import ru.clevertec.ecl.entity.House;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public class HouseListener {

    @PrePersist
    protected void onCreate(House house) {
        house.setUuid(UUID.randomUUID());
        house.setCreateDate(Timestamp.from(Instant.now()));
    }
}
