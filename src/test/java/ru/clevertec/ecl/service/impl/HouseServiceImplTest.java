package ru.clevertec.ecl.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ecl.dao.HouseDao;
import ru.clevertec.ecl.data.HouseTestData;
import ru.clevertec.ecl.dto.HouseDto;
import ru.clevertec.ecl.dto.HouseInfoDto;
import ru.clevertec.ecl.dto.InfoPersonDto;
import ru.clevertec.ecl.entity.House;
import ru.clevertec.ecl.entity.Person;
import ru.clevertec.ecl.exception.ResourceNotFoundException;
import ru.clevertec.ecl.mapper.HouseMapper;
import ru.clevertec.ecl.mapper.PersonMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HouseServiceImplTest {

    @Mock
    private HouseMapper houseMapper;

    @Mock
    private PersonMapper personMapper;

    @Mock
    private HouseDao houseDao;

    @InjectMocks
    private HouseServiceImpl houseService;


    @Test
    public void findByIdExistingUuidReturnsHouseInfoDto() {
        // Given
        House house = HouseTestData.builder()
                .build()
                .buildHouse();
        HouseInfoDto expectedDto = HouseTestData.builder()
                .build()
                .buildHouseInfo();
        UUID uuid = house.getUuid();

        when(houseDao.findByUuid(uuid))
                .thenReturn(Optional.of(house));
        when(houseMapper.houseToHouseInfoDto(house))
                .thenReturn(expectedDto);

        // When
        HouseInfoDto result = houseService.findById(uuid);

        // Then
        assertNotNull(result);
        assertEquals(uuid, result.getUuid());
    }

    @Test
    public void findByIdNonExistingUuidThrowsException() {
        // Given
        UUID uuid = UUID.fromString("c249fc5b-4a25-4212-83ca-2c6ec0d57d0b");
        when(houseDao.findByUuid(uuid))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> houseService.findById(uuid));
    }


    @Test
    void testFindAll() {
        // Given
        List<House> houses = Collections.singletonList(HouseTestData.builder()
                .build()
                .buildHouse());
        List<HouseInfoDto> houseInfoDto = Collections.singletonList(HouseTestData.builder()
                .build()
                .buildHouseInfo());

        when(houseDao.findAll(anyInt(), anyInt()))
                .thenReturn(houses);
        when(houseMapper.houseToHouseInfoDto(any(House.class)))
                .thenReturn(houseInfoDto.get(0));

        // When
        List<HouseInfoDto> result = houseService.findAll(0, 10);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(houseInfoDto.size(), result.size());
    }

    @Test
    public void updateExistingHouse() {
        // Given
        HouseDto houseDto = HouseTestData.builder()
                .build()
                .buildHouseDto();
        House existingHouse = HouseTestData.builder()
                .build()
                .buildHouse();
        UUID uuid = existingHouse.getUuid();

        when(houseDao.findByUuid(uuid))
                .thenReturn(Optional.of(existingHouse));
        when(houseMapper.merge(existingHouse, houseDto))
                .thenReturn(existingHouse);
        when(houseDao.update(existingHouse))
                .thenReturn(existingHouse);

        // When
        UUID result = houseService.update(uuid, houseDto);

        // Then
        assertEquals(uuid, result);
    }

    @Test
    public void createValidHouseDtoReturnsUuid() {
        // Given
        HouseDto houseDto = HouseTestData.builder()
                .build()
                .buildHouseDto();
        House existingHouse = HouseTestData.builder()
                .build()
                .buildHouse();

        when(houseMapper.toProduct(houseDto))
                .thenReturn(existingHouse);
        when(houseDao.create(existingHouse))
                .thenReturn(existingHouse);

        // When
        UUID result = houseService.create(houseDto);

        // Then
        assertNotNull(result);
    }

    @Test
    public void delete_ExistingUuid_DeletesHouse() {
        // Given
        UUID uuid = UUID.fromString("c249fc5b-4a25-4212-83ca-2c6ec0d57d0b");

        // When
        houseService.delete(uuid);

        // Then
        verify(houseDao)
                .delete(uuid);
    }

    @Test
    void testFindResidentsByHouseId_Success() {
        // Given
        House testHouse = HouseTestData.builder()
                .build()
                .buildHouse();
        UUID testUuid = testHouse.getUuid();
        List<Person> residents = testHouse.getResidents();
        List<InfoPersonDto> expectedInfoPersonDto = residents.stream()
                .map(person -> new InfoPersonDto())
                .toList();

        when(houseDao.findByUuid(testUuid))
                .thenReturn(Optional.of(testHouse));
        when(personMapper.toInfoPersonDto(any(Person.class)))
                .thenAnswer(invocation -> new InfoPersonDto());

        // When
        List<InfoPersonDto> actualInfoPersonDto = houseService.findResidentsByHouseId(testUuid);

        // Then
        assertNotNull(actualInfoPersonDto);
        assertEquals(expectedInfoPersonDto.size(), actualInfoPersonDto.size());

        verify(personMapper, times(residents.size()))
                .toInfoPersonDto(any(Person.class));
    }
}
