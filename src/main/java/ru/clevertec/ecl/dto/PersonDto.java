package ru.clevertec.ecl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.ecl.entity.Passport;
import ru.clevertec.ecl.enums.Sex;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {
    private String name;
    private String surname;
    private Sex sex;
    private Passport passport;
}
