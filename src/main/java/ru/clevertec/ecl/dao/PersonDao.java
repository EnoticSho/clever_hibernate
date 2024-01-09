package ru.clevertec.ecl.dao;

import ru.clevertec.ecl.entity.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonDao {
    List<Person> findAll(int pageNumber, int pageSize);

    Optional<Person> findByUuid(UUID uuid);

    Person create(Person person);

    Person update(Person person);

    void delete(UUID uuid);
}
