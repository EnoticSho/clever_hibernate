package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.dto.HouseDto;
import ru.clevertec.ecl.dto.HouseInfoDto;
import ru.clevertec.ecl.dto.InfoPersonDto;
import ru.clevertec.ecl.service.HouseService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Контроллер для управления домами в API.
 * Этот класс обрабатывает HTTP-запросы, связанные с домами, и предоставляет методы для получения информации о домах,
 * жителях домов, создания, обновления и удаления домов.
 */
@RestController
@RequestMapping("/api/houses")
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;

    /**
     * Обработчик HTTP GET-запроса для получения информации о доме по его уникальному идентификатору.
     *
     * @param uuid Уникальный идентификатор дома.
     * @return ResponseEntity с информацией о доме в виде объекта HouseInfoDto.
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<HouseInfoDto> getHouse(@PathVariable UUID uuid) {
        return ResponseEntity.ok(houseService.findById(uuid));
    }

    /**
     * Обработчик HTTP GET-запроса для получения списка жителей дома по уникальному идентификатору дома.
     *
     * @param uuid Уникальный идентификатор дома.
     * @return ResponseEntity со списком жителей в виде объектов InfoPersonDto.
     */
    @GetMapping("/{uuid}/residents")
    public ResponseEntity<List<InfoPersonDto>> getHouseResidents(@PathVariable UUID uuid) {
        return ResponseEntity.ok(houseService.findResidentsByHouseId(uuid));
    }

    /**
     * Обработчик HTTP GET-запроса для получения списка всех домов с возможностью пагинации.
     *
     * @param page Номер страницы (необязательно).
     * @param size Размер страницы (необязательно).
     * @return ResponseEntity со списком информации о домах в виде объектов HouseInfoDto.
     */
    @GetMapping
    public ResponseEntity<List<HouseInfoDto>> getAllHouses(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {

        int pageNumber = (page != null && page > 0) ? page : 1;
        int pageSize = (size != null && size > 0) ? size : 10;

        return ResponseEntity.ok(houseService.findAll(pageNumber, pageSize));
    }

    /**
     * Обработчик HTTP PUT-запроса для обновления информации о доме по его уникальному идентификатору.
     *
     * @param uuid     Уникальный идентификатор дома, который нужно обновить.
     * @param houseDto Объект HouseDto с обновленной информацией о доме.
     * @return ResponseEntity с уникальным идентификатором обновленного дома.
     */
    @PutMapping("/{uuid}")
    public ResponseEntity<UUID> updateHouse(@PathVariable UUID uuid, @RequestBody HouseDto houseDto) {
        return ResponseEntity.ok(houseService.update(uuid, houseDto));
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<UUID> updateHouse(@PathVariable UUID uuid, @RequestBody Map<String, Object> updates) {
        return ResponseEntity.ok(houseService.updateHouseByField(uuid, updates));
    }

    /**
     * Обработчик HTTP POST-запроса для создания нового дома с заданными данными.
     *
     * @param houseDto Объект HouseDto с данными для создания нового дома.
     * @return ResponseEntity с уникальным идентификатором созданного дома.
     */
    @PostMapping
    public ResponseEntity<UUID> createHouse(@RequestBody HouseDto houseDto) {
        return ResponseEntity.ok(houseService.create(houseDto));
    }

    /**
     * Обработчик HTTP DELETE-запроса для удаления дома по его уникальному идентификатору.
     *
     * @param uuid Уникальный идентификатор дома, который нужно удалить.
     * @return ResponseEntity с кодом "204 No Content" после успешного удаления.
     */
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteHouse(@PathVariable UUID uuid) {
        houseService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
