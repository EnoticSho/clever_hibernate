package ru.clevertec.ecl.entity.listeners;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import ru.clevertec.ecl.entity.Person;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public class PersonListener {

    @PrePersist
    protected void onCreate(Person person) {
        Timestamp now = Timestamp.from(Instant.now());
        person.setCreateDate(now);
        person.setUpdateDate(now);
        person.setUuid(UUID.randomUUID());
    }

    @PreUpdate
    protected void onUpdate(Person person) {
        person.setUpdateDate(Timestamp.from(Instant.now()));
    }
}
