package ru.clevertec.ecl.service;

import ru.clevertec.ecl.dto.HouseDto;
import ru.clevertec.ecl.dto.HouseInfoDto;
import ru.clevertec.ecl.dto.InfoPersonDto;
import ru.clevertec.ecl.entity.House;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface HouseService {
    HouseInfoDto findById(UUID uuid);

    List<HouseInfoDto> findAll(int pageNumber, int pageSize);

    UUID update(UUID uuid, HouseDto houseDto);

    UUID create(HouseDto houseDto);

    void delete(UUID uuid);

    List<InfoPersonDto> findResidentsByHouseId(UUID uuid);

    UUID updateHouseByField(UUID uuid, Map<String, Object> updates);
}
