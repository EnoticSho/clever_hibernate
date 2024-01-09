package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import java.util.UUID;

@RestController
@RequestMapping("/api/houses")
@RequiredArgsConstructor
public class HouseController {

    private final HouseService houseService;

    @GetMapping("/{uuid}")
    public ResponseEntity<HouseInfoDto> getHouse(@PathVariable UUID uuid) {
        return ResponseEntity.ok(houseService.findById(uuid));
    }

    @GetMapping("/{uuid}/residents")
    public ResponseEntity<List<InfoPersonDto>> getHouseResidents(@PathVariable UUID uuid) {
        return ResponseEntity.ok(houseService.findResidentsByHouseId(uuid));
    }

    @GetMapping
    public ResponseEntity<List<HouseInfoDto>> getAllHouses(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {

        int pageNumber = (page != null && page > 0) ? page : 1;
        int pageSize = (size != null && size > 0) ? size : 10;

        return ResponseEntity.ok(houseService.findAll(pageNumber, pageSize));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<UUID> updateHouse(@PathVariable UUID uuid, @RequestBody HouseDto houseDto) {
        return ResponseEntity.ok(houseService.update(uuid, houseDto));
    }

    @PostMapping
    public ResponseEntity<UUID> createHouse(@RequestBody HouseDto houseDto) {
        return ResponseEntity.ok(houseService.create(houseDto));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteHouse(@PathVariable UUID uuid) {
        houseService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
