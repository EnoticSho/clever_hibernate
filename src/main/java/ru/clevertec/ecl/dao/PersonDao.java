package ru.clevertec.ecl.dao;

import ru.clevertec.ecl.entity.Person;

import java.util.UUID;

public interface PersonDao extends CrudDao<Person, UUID> {
}
