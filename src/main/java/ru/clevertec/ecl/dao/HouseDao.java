package ru.clevertec.ecl.dao;

import ru.clevertec.ecl.entity.House;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HouseDao {
    List<House> findAll(int pageNumber, int pageSize);

    Optional<House> findByUuid(UUID uuid);

    House create(House house);

    House update(House house);

    void delete(UUID uuid);
}
