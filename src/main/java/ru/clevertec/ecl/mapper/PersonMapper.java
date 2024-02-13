package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.clevertec.ecl.dto.InfoPersonDto;
import ru.clevertec.ecl.dto.PersonDto;
import ru.clevertec.ecl.entity.Person;

@Mapper
public interface PersonMapper {

    Person toPerson(PersonDto personDto);

    InfoPersonDto toInfoPersonDto(Person person);

    Person merge(@MappingTarget Person person, PersonDto personDto);
}
