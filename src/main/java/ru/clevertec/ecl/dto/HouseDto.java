package ru.clevertec.ecl.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HouseDto {

    @NotEmpty(message = "Площадь не может быть пустым")
    @DecimalMin(value = "0.0", inclusive = false, message = "Площадь должна быть больше 0")
    private Double area;

    @NotEmpty(message = "Страна не может быть пустым")
    private String country;

    @NotEmpty(message = "Город не может быть пустым")
    private String city;

    @NotEmpty(message = "Улица не может быть пустой")
    private String street;

    @NotEmpty(message = "Номер не может быть пустым")
    private String number;

    private List<PersonDto> residents;
}
