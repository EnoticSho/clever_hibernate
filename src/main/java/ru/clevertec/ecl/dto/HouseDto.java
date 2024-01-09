package ru.clevertec.ecl.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HouseDto {
    private Double area;
    private String country;
    private String city;
    private String street;
    private String number;
}
