package ru.clevertec.ecl.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dao.HouseDao;
import ru.clevertec.ecl.dto.HouseDto;
import ru.clevertec.ecl.dto.HouseInfoDto;
import ru.clevertec.ecl.dto.InfoPersonDto;
import ru.clevertec.ecl.entity.House;
import ru.clevertec.ecl.exception.ResourceNotFoundException;
import ru.clevertec.ecl.mapper.HouseMapper;
import ru.clevertec.ecl.mapper.PersonMapper;
import ru.clevertec.ecl.service.HouseService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseDao houseDao;
    private final PersonMapper personMapper;
    private final HouseMapper houseMapper;

    @Override
    public HouseInfoDto findById(UUID uuid) {
        return houseDao.findByUuid(uuid)
                .map(houseMapper::houseToHouseInfoDto)
                .orElseThrow(() -> new ResourceNotFoundException(uuid, House.class));
    }

    @Override
    public List<HouseInfoDto> findAll(int pageNumber, int pageSize) {
        return houseDao.findAll(pageNumber, pageSize).stream()
                .map(houseMapper::houseToHouseInfoDto)
                .toList();
    }

    @Override
    public UUID update(UUID uuid, HouseDto houseDto) {
        House house = houseDao.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException(uuid, House.class));
        House merge = houseMapper.merge(house, houseDto);
        return houseDao.update(merge).getUuid();
    }

    @Override
    public UUID create(HouseDto houseDto) {
        House house = houseMapper.toProduct(houseDto);
        house.setUuid(UUID.randomUUID());
        house.setCreateDate(Timestamp.from(Instant.now()));
        return houseDao.create(house).getUuid();
    }

    @Override
    public void delete(UUID uuid) {
        houseDao.delete(uuid);
    }

    @Override
    @Transactional
    public List<InfoPersonDto> findResidentsByHouseId(UUID uuid) {
        House house = houseDao.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException(uuid, House.class));
        return house.getResidents().stream()
                .map(personMapper::toInfoPersonDto)
                .toList();
    }
}
