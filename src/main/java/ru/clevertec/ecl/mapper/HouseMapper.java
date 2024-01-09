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

    @Mapping(target = "createDate", source = "createDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    HouseInfoDto houseToHouseInfoDto(House house);

    House merge(@MappingTarget House house, HouseDto houseDto);
}
