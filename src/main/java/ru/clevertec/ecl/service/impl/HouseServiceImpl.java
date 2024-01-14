package ru.clevertec.ecl.service.impl;

import jakarta.validation.Valid;
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

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Реализация интерфейса HouseService для выполнения операций с объектами House.
 * Этот класс предоставляет методы для поиска, создания, обновления и удаления домов, а также для получения информации о жителях дома.
 */
@Service
@RequiredArgsConstructor
public class HouseServiceImpl implements HouseService {

    private final HouseDao houseDao;
    private final PersonMapper personMapper;
    private final HouseMapper houseMapper;

    /**
     * Находит информацию о доме по его уникальному идентификатору (UUID).
     *
     * @param uuid Уникальный идентификатор дома для поиска.
     * @return Объект HouseInfoDto с информацией о доме.
     * @throws ResourceNotFoundException Если дом с заданным UUID не найден, генерируется исключение ResourceNotFoundException.
     */
    @Override
    @Transactional
    public HouseInfoDto findById(UUID uuid) {
        return houseDao.findByUuid(uuid)
                .map(houseMapper::houseToHouseInfoDto)
                .orElseThrow(() -> ResourceNotFoundException.of(uuid, House.class));
    }

    /**
     * Получает список информации о домах с возможностью пагинации.
     *
     * @param pageNumber Номер страницы для пагинации.
     * @param pageSize   Размер страницы для пагинации.
     * @return Список объектов HouseInfoDto, соответствующих заданным параметрам пагинации.
     */
    @Override
    @Transactional
    public List<HouseInfoDto> findAll(int pageNumber, int pageSize) {
        return houseDao.findAll(pageNumber, pageSize).stream()
                .map(houseMapper::houseToHouseInfoDto)
                .toList();
    }

    /**
     * Обновляет информацию о доме с заданным UUID на основе данных из объекта HouseDto.
     *
     * @param uuid     Уникальный идентификатор дома, который нужно обновить.
     * @param houseDto Объект HouseDto с обновленной информацией о доме.
     * @return Уникальный идентификатор обновленного дома.
     * @throws ResourceNotFoundException Если дом с заданным UUID не найден, генерируется исключение ResourceNotFoundException.
     */
    @Override
    public UUID update(UUID uuid,@Valid HouseDto houseDto) {
        House house = houseDao.findByUuid(uuid)
                .orElseThrow(() -> ResourceNotFoundException.of(uuid, House.class));
        House merge = houseMapper.merge(house, houseDto);
        return houseDao.update(merge).getUuid();
    }

    /**
     * Создает новый дом на основе данных из объекта HouseDto.
     *
     * @param houseDto Объект HouseDto с данными для создания нового дома.
     * @return Уникальный идентификатор созданного дома.
     */
    @Override
    public UUID create(@Valid HouseDto houseDto) {
        House house = houseMapper.toProduct(houseDto);
        return houseDao.create(house).getUuid();
    }

    /**
     * Удаляет дом с заданным UUID.
     *
     * @param uuid Уникальный идентификатор дома, который нужно удалить.
     */
    @Override
    public void delete(UUID uuid) {
        houseDao.delete(uuid);
    }

    /**
     * Получает информацию о жителях дома с заданным UUID.
     *
     * @param uuid Уникальный идентификатор дома, для которого нужно получить информацию о жителях.
     * @return Список объектов InfoPersonDto с информацией о жителях дома.
     * @throws ResourceNotFoundException Если дом с заданным UUID не найден, генерируется исключение ResourceNotFoundException.
     */
    @Override
    @Transactional
    public List<InfoPersonDto> findResidentsByHouseId(UUID uuid) {
        House house = houseDao.findByUuid(uuid)
                .orElseThrow(() -> ResourceNotFoundException.of(uuid, House.class));
        return house.getResidents().stream()
                .map(personMapper::toInfoPersonDto)
                .toList();
    }

    /**
     * Получает информацию о жителях дома с заданным UUID.
     *
     * @param uuid Уникальный идентификатор дома, для которого нужно получить информацию о жителях.
     * @return Список объектов InfoPersonDto с информацией о жителях дома.
     * @throws ResourceNotFoundException Если дом с заданным UUID не найден, генерируется исключение ResourceNotFoundException.
     */
    @Override
    public UUID updateHouseByField(UUID uuid, Map<String, Object> updates) {
        House house = houseDao.findByUuid(uuid)
                .orElseThrow(() -> ResourceNotFoundException.of(uuid, House.class));
        updates.forEach((key, value) -> {
            try {
                Field field = House.class.getDeclaredField(key);
                field.setAccessible(true);
                field.set(house, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        return houseDao.update(house).getUuid();
    }
}
