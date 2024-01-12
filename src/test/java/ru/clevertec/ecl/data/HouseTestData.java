package ru.clevertec.ecl.data;

import lombok.Builder;
import lombok.Data;
import ru.clevertec.ecl.dto.HouseDto;
import ru.clevertec.ecl.dto.HouseInfoDto;
import ru.clevertec.ecl.entity.House;
import ru.clevertec.ecl.entity.Person;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder(setterPrefix = "with")
public class HouseTestData {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private UUID uuid = UUID.fromString("c249fc5b-4a25-4212-83ca-2c6ec0d57d0b");

    @Builder.Default
    private Double area = 100.00;

    @Builder.Default
    private String country = "Belarus";

    @Builder.Default
    private String city = "Mogilev";

    @Builder.Default
    private String street = "Street 1";

    @Builder.Default
    private String number = "25";

    @Builder.Default
    private Timestamp createDate = Timestamp.valueOf(LocalDateTime.of(2023, 10, 15, 12, 34));

    @Builder.Default
    private List<Person> residents = List.of(new Person());

    public HouseInfoDto buildHouseInfo() {
        return new HouseInfoDto(uuid, area, country, city, street, number, createDate);
    }

    public HouseDto buildHouseDto() {
        return new HouseDto(area, country, city, street, number);
    }

    public House buildHouse() {
        return new House(id, uuid, area, country, city, street, number, createDate, residents);
    }
}
