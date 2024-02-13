package ru.clevertec.ecl.dao;

import ru.clevertec.ecl.entity.House;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CrudDao<T, V> {
    List<T> findAll(int pageNumber, int pageSize);

    Optional<T> findByUuid(V uuid);

    T create(T t);

    T update(T T);

    void delete(V uuid);
}
