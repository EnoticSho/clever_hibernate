package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.clevertec.ecl.dto.HouseDto;
import ru.clevertec.ecl.dto.HouseInfoDto;
import ru.clevertec.ecl.entity.House;

@Mapper
public interface HouseMapper {

    House toProduct(HouseDto houseDto);

    HouseInfoDto houseToHouseInfoDto(House house);

    House merge(@MappingTarget House house, HouseDto houseDto);
}
