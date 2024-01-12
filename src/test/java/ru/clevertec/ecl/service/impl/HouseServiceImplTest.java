package ru.clevertec.ecl.service.impl;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.ecl.dao.HouseDao;
import ru.clevertec.ecl.data.HouseTestData;
import ru.clevertec.ecl.dto.HouseDto;
import ru.clevertec.ecl.dto.HouseInfoDto;
import ru.clevertec.ecl.entity.House;
import ru.clevertec.ecl.exception.ResourceNotFoundException;
import ru.clevertec.ecl.mapper.HouseMapper;
import ru.clevertec.ecl.service.HouseService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HouseServiceImplTest {

    @Mock
    private HouseMapper houseMapper;

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
        assertEquals(expectedDto, result);
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
//
//    @Test
//    public void findAllValidParametersReturnsListOfHouseInfoDto() {
//        // Given
//        int pageNumber = 1;
//        int pageSize = 1;
//        House house = HouseTestData.builder()
//                .build()
//                .buildHouse();
//        HouseInfoDto houseInfoDto = HouseTestData.builder()
//                .build()
//                .buildHouseInfo();
//
//        List<House> houses = Collections.singletonList(house);
//        List<HouseInfoDto> expectedList = Collections.singletonList(houseInfoDto);
//
//        when(houseDao.findAll(pageNumber, pageSize))
//                .thenReturn(houses);
//        when(houseMapper.houseToHouseInfoDto(any(House.class)))
//                .thenReturn(houseInfoDto);
//
//        // When
//        List<HouseInfoDto> result = houseService.findAll(pageNumber, pageSize);
//
//        // Assert
//        assertEquals(expectedList, result);
//    }
//
//    @Test
//    public void updateExistingUuidUpdatesHouse() {
//        // Arrange
//        UUID uuid = UUID.randomUUID();
//        HouseDto houseDto = HouseTestData.builder()
//                .build()
//                .buildHouseDto();
//        House existingHouse = // set properties
//                // House updatedHouse = // set properties after update
//                when(houseDao.findByUuid(uuid)).thenReturn(Optional.of(existingHouse));
//        when(houseMapper.merge(existingHouse, houseDto)).thenReturn(updatedHouse);
//        when(houseDao.update(updatedHouse)).thenReturn(updatedHouse);
//
//        // Act
//        UUID result = houseService.update(uuid, houseDto);
//
//        // Assert
//        assertEquals(uuid, result);
//    }
//
//    @Test(expected = ResourceNotFoundException.class)
//    public void update_NonExistingUuid_ThrowsException() {
//        // Arrange
//        UUID uuid = UUID.randomUUID();
//        HouseDto houseDto = // set properties
//                when(houseDao.findByUuid(uuid)).thenReturn(Optional.empty());
//
//        // Act
//        houseService.update(uuid, houseDto);
//    }
//
//    @Test
//    public void createValidHouseDtoReturnsUuid() {
//        // Arrange
//        HouseDto houseDto = // set properties
//                House house = // set properties
//                when(houseMapper.toProduct(houseDto)).thenReturn(house);
//        when(houseDao.create(house)).thenReturn(house);
//
//        // Act
//        UUID result = houseService.create(houseDto);
//
//        // Assert
//        assertNotNull(result);
//    }
//
//    @Test
//    public void delete_ExistingUuid_DeletesHouse() {
//        // Arrange
//        UUID uuid = UUID.randomUUID();
//
//        // Act
//        houseService.delete(uuid);
//
//        // Verify
//        verify(houseDao).delete(uuid);
//    }
//
//    @Test
//    public void findResidentsByHouseId_ExistingUuid_ReturnsListOfInfoPersonDto() {
//        // Arrange
//        UUID uuid = UUID.randomUUID();
//        House house = // set properties with residents
//                List < InfoPersonDto > expectedList = // convert residents to InfoPersonDto
//                        when(houseDao.findByUuid(uuid)).thenReturn(Optional.of
//    }
}
