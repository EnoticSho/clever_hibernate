package ru.clevertec.ecl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseInfoDto {
    private UUID uuid;
    private Double area;
    private String country;
    private String city;
    private String street;
    private String number;
    private String createDate;
}
