package ru.clevertec.ecl.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ecl.dao.PersonDao;
import ru.clevertec.ecl.data.HouseTestData;
import ru.clevertec.ecl.data.PersonTestData;
import ru.clevertec.ecl.dto.HouseInfoDto;
import ru.clevertec.ecl.dto.InfoPersonDto;
import ru.clevertec.ecl.dto.PersonDto;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PersonServiceImplTest {

    @Mock
    private PersonDao personDao;

    @Mock
    private PersonMapper personMapper;

    @Mock
    private HouseMapper houseMapper;

    @InjectMocks
    private PersonServiceImpl personService;

    @Test
    void testFindByIdSuccess() {
        // Given
        Person testPerson = PersonTestData.builder()
                .build()
                .buildPerson();
        UUID testUuid = testPerson.getUuid();
        InfoPersonDto testInfoPersonDto = PersonTestData.builder()
                .build()
                .buildPersonInfo();

        when(personDao.findByUuid(testUuid))
                .thenReturn(Optional.of(testPerson));
        when(personMapper.toInfoPersonDto(any(Person.class)))
                .thenReturn(testInfoPersonDto);

        // When
        InfoPersonDto result = personService.findById(testUuid);

        // Then
        assertNotNull(result);
        assertEquals(testUuid, result.getUuid());
    }


    @Test
    void testFindByIdNotFound() {
        // Given
        UUID testUuid = UUID.fromString("c249fc5b-4a25-4212-83ca-2c6ec0d57d0b");

        when(personDao.findByUuid(testUuid))
                .thenReturn(Optional.empty());

        // When & Then
        assertThrows(ResourceNotFoundException.class, () -> personService.findById(testUuid));
    }

    @Test
    void testFindAllWithResults() {
        // Given
        List<Person> people = Collections.singletonList(PersonTestData.builder()
                .build()
                .buildPerson());
        List<InfoPersonDto> infoPersonDto = Collections.singletonList(PersonTestData.builder()
                .build()
                .buildPersonInfo());

        when(personDao.findAll(anyInt(), anyInt()))
                .thenReturn(people);
        when(personMapper.toInfoPersonDto(any(Person.class)))
                .thenReturn(infoPersonDto.get(0));

        // When
        List<InfoPersonDto> result = personService.findAll(0, 10);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(infoPersonDto.size(), result.size());
    }

    @Test
    void testFindAllNoResults() {
        // Given
        when(personDao.findAll(anyInt(), anyInt()))
                .thenReturn(Collections.emptyList());

        // When
        List<InfoPersonDto> result = personService.findAll(0, 10);

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    void testUpdateSuccess() {
        // Given
        PersonDto personDto = PersonTestData.builder()
                .build()
                .buildPersonDto();
        Person testPerson = PersonTestData.builder()
                .build()
                .buildPerson();
        UUID testUuid = testPerson.getUuid();

        when(personDao.findByUuid(testUuid))
                .thenReturn(Optional.of(testPerson));
        when(personMapper.merge(testPerson, personDto))
                .thenReturn(testPerson);
        when(personDao.update(testPerson))
                .thenReturn(testPerson);

        // When
        UUID result = personService.update(testUuid, personDto);

        // Then
        assertEquals(testUuid, result);
    }

    @Test
    void testCreateSuccess() {
        // Given
        PersonDto personDto = PersonTestData.builder()
                .build()
                .buildPersonDto();
        Person testPerson = PersonTestData.builder()
                .build()
                .buildPerson();
        UUID testUuid = testPerson.getUuid();

        when(personMapper.toPerson(personDto))
                .thenReturn(testPerson);
        when(personDao.create(any(Person.class)))
                .thenReturn(testPerson);

        // When
        UUID result = personService.create(personDto);

        // Then
        assertEquals(testUuid, result);

        verify(personMapper)
                .toPerson(personDto);
        verify(personDao)
                .create(any(Person.class));
    }

    @Test
    void testDeleteVerifyCall() {
        // Given
        UUID uuid = UUID.fromString("c249fc5b-4a25-4212-83ca-2c6ec0d57d0b");

        // When
        personService.delete(uuid);

        // Then
        verify(personDao)
                .delete(uuid);
    }

    @Test
    void testFindHousesByPersonId_WithHouses() {
        // Given
        Person testPerson = PersonTestData.builder()
                .build()
                .buildPerson();
        List<HouseInfoDto> houseInfoDto = List.of(HouseTestData.builder()
                .build()
                .buildHouseInfo());
        UUID testUuid = testPerson.getUuid();

        when(personDao.findByUuid(testUuid)).thenReturn(Optional.of(testPerson));
        when(houseMapper.houseToHouseInfoDto(any(House.class))).thenReturn(houseInfoDto.get(0));

        // When
        List<HouseInfoDto> result = personService.findHousesByPersonId(testUuid);

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(houseInfoDto.size(), result.size());
    }
}
