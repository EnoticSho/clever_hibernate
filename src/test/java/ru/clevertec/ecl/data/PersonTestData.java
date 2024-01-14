package ru.clevertec.ecl.data;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.ecl.dto.InfoPersonDto;
import ru.clevertec.ecl.dto.PersonDto;
import ru.clevertec.ecl.entity.House;
import ru.clevertec.ecl.entity.Passport;
import ru.clevertec.ecl.entity.Person;
import ru.clevertec.ecl.enums.Sex;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder(setterPrefix = "with")
public class PersonTestData {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private UUID uuid = UUID.fromString("c249fc5b-4a25-4212-83ca-2c6ec0d57d0b");

    @Builder.Default
    private String name = "Иван";

    @Builder.Default
    private String surname = "Иванов";

    @Builder.Default
    private Sex sex = Sex.FEMALE;

    @Builder.Default
    private Passport passport = new Passport("AK", "12355");

    @Builder.Default
    private Timestamp createDate = Timestamp.valueOf(LocalDateTime.of(2023, 10, 15, 12, 34));

    @Builder.Default
    private Timestamp updateDate = Timestamp.valueOf(LocalDateTime.of(2023, 10, 15, 12, 34));

    @Builder.Default
    private List<House> houseList = List.of(House.builder()
            .id(1L)
            .country("Belarus")
            .build());

    public InfoPersonDto buildPersonInfo() {
        return new InfoPersonDto(uuid, name, surname, sex, passport, createDate, updateDate);
    }

    public PersonDto buildPersonDto() {
        return new PersonDto(name, surname, sex, passport);
    }

    public Person buildPerson() {
        return new Person(id, uuid, name, surname, sex, passport, createDate, updateDate, houseList);
    }
}
