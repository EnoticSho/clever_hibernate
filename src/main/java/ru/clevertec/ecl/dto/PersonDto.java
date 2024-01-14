package ru.clevertec.ecl.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.clevertec.ecl.entity.Passport;
import ru.clevertec.ecl.enums.Sex;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 50)
    private String surname;

    @NotNull
    private Sex sex;

    @NotNull
    private Passport passport;
}
