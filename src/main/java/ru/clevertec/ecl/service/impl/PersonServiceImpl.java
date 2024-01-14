package ru.clevertec.ecl.service.impl;

import jakarta.validation.Valid;
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

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonDao personDao;
    private final PersonMapper personMapper;
    private final HouseMapper houseMapper;

    /**
     * Находит информацию о жителе по уникальному идентификатору.
     *
     * @param uuid Уникальный идентификатор жителя, для которого нужно получить информацию.
     * @return Объект InfoPersonDto с информацией о жителе.
     * @throws ResourceNotFoundException Если житель с заданным UUID не найден, генерируется исключение ResourceNotFoundException.
     */
    @Override
    @Transactional
    public InfoPersonDto findById(UUID uuid) {
        return personDao.findByUuid(uuid)
                .map(personMapper::toInfoPersonDto)
                .orElseThrow(() -> ResourceNotFoundException.of(uuid, InfoPersonDto.class));
    }

    /**
     * Находит и возвращает список информации о жителях с пагинацией.
     *
     * @param pageNumber Номер страницы.
     * @param pageSize   Размер страницы.
     * @return Список объектов InfoPersonDto с информацией о жителях на указанной странице.
     */
    @Override
    @Transactional
    public List<InfoPersonDto> findAll(int pageNumber, int pageSize) {
        return personDao.findAll(pageNumber, pageSize).stream()
                .map(personMapper::toInfoPersonDto)
                .toList();
    }

    /**
     * Обновляет информацию о жителе с заданным UUID.
     *
     * @param uuid       Уникальный идентификатор жителя, который нужно обновить.
     * @param personDto  Объект PersonDto с обновленными данными о жителе.
     * @return Уникальный идентификатор обновленного жителя.
     * @throws ResourceNotFoundException Если житель с заданным UUID не найден, генерируется исключение ResourceNotFoundException.
     */
    @Override
    public UUID update(UUID uuid,@Valid PersonDto personDto) {
        Person person = personDao.findByUuid(uuid)
                .orElseThrow(() -> ResourceNotFoundException.of(uuid, HouseInfoDto.class));
        Person merge = personMapper.merge(person, personDto);
        return personDao.update(merge).getUuid();
    }

    /**
     * Создает нового жителя с указанными данными.
     *
     * @param personDto Объект PersonDto с данными нового жителя.
     * @return Уникальный идентификатор созданного жителя.
     */
    @Override
    public UUID create(@Valid PersonDto personDto) {
        Person person = personMapper.toPerson(personDto);
        return personDao.create(person).getUuid();
    }

    /**
     * Удаляет жителя с заданным UUID.
     *
     * @param uuid Уникальный идентификатор жителя, который нужно удалить.
     */
    @Override
    public void delete(UUID uuid) {
        personDao.delete(uuid);
    }

    /**
     * Находит и возвращает список информации о домах, принадлежащих жителю с заданным UUID.
     *
     * @param uuid Уникальный идентификатор жителя, для которого нужно найти информацию о домах.
     * @return Список объектов HouseInfoDto с информацией о домах, принадлежащих жителю.
     * @throws ResourceNotFoundException Если житель с заданным UUID не найден, генерируется исключение ResourceNotFoundException.
     */
    @Override
    @Transactional
    public List<HouseInfoDto> findHousesByPersonId(UUID uuid) {
        Person person = personDao.findByUuid(uuid)
                .orElseThrow(() -> ResourceNotFoundException.of(uuid, Person.class));
        return person.getHouseList().stream()
                .map(houseMapper::houseToHouseInfoDto)
                .toList();
    }
}
