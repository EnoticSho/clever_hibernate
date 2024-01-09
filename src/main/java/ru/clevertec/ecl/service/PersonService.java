package ru.clevertec.ecl.service;

import ru.clevertec.ecl.dto.HouseInfoDto;
import ru.clevertec.ecl.dto.InfoPersonDto;
import ru.clevertec.ecl.dto.PersonDto;

import java.util.List;
import java.util.UUID;

public interface PersonService {
    InfoPersonDto findById(UUID uuid);

    List<InfoPersonDto> findAll(int pageNumber, int pageSize);

    UUID update(UUID uuid, PersonDto personDto);

    UUID create(PersonDto personDto);

    void delete(UUID uuid);

    List<HouseInfoDto> findHousesByPersonId(UUID uuid);
}
