package ru.clevertec.ecl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InfoPersonDto {
    private UUID uuid;
    private String name;
    private String surname;
    private String sex;
    private String passportSeries;
    private String passportNumber;
    private String createDate;
    private String updateDate;
}
