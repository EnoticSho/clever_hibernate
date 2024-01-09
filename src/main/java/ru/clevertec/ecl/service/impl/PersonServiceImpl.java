package ru.clevertec.ecl.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dao.PersonDao;
import ru.clevertec.ecl.dto.HouseInfoDto;
import ru.clevertec.ecl.dto.InfoPersonDto;
import ru.clevertec.ecl.dto.PersonDto;
import ru.clevertec.ecl.entity.Person;
import ru.clevertec.ecl.exception.ResourceNotFoundException;
import ru.clevertec.ecl.mapper.HouseMapper;
import ru.clevertec.ecl.mapper.PersonMapper;
import ru.clevertec.ecl.service.PersonService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonDao personDao;
    private final PersonMapper personMapper;
    private final HouseMapper houseMapper;

    @Override
    public InfoPersonDto findById(UUID uuid) {
        return personDao.findByUuid(uuid)
                .map(personMapper::toInfoPersonDto)
                .orElseThrow(() -> new ResourceNotFoundException(uuid, InfoPersonDto.class));
    }

    @Override
    public List<InfoPersonDto> findAll(int pageNumber, int pageSize) {
        return personDao.findAll(pageNumber, pageSize).stream()
                .map(personMapper::toInfoPersonDto)
                .toList();
    }

    @Override
    public UUID update(UUID uuid, PersonDto personDto) {
        Person person = personDao.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException(uuid, HouseInfoDto.class));
        Person merge = personMapper.merge(person, personDto);
        person.setUpdateDate(Timestamp.from(Instant.now()));
        return personDao.update(merge).getUuid();
    }

    @Override
    public UUID create(PersonDto personDto) {
        Person person = personMapper.toPerson(personDto);
        person.setUuid(UUID.randomUUID());
        person.setCreateDate(Timestamp.from(Instant.now()));
        return personDao.create(person).getUuid();
    }

    @Override
    public void delete(UUID uuid) {
        personDao.delete(uuid);
    }

    @Override
    @Transactional
    public List<HouseInfoDto> findHousesByPersonId(UUID uuid) {
        Person person = personDao.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException(uuid, Person.class));
        return person.getHouseList().stream()
                .map(houseMapper::houseToHouseInfoDto)
                .toList();
    }
}
