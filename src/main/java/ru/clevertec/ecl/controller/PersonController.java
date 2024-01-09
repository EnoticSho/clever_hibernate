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
import ru.clevertec.ecl.dto.HouseInfoDto;
import ru.clevertec.ecl.dto.InfoPersonDto;
import ru.clevertec.ecl.dto.PersonDto;
import ru.clevertec.ecl.service.PersonService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @GetMapping("/{uuid}")
    public ResponseEntity<InfoPersonDto> getPerson(@PathVariable UUID uuid) {
        return ResponseEntity.ok(personService.findById(uuid));
    }

    @GetMapping("/{uuid}/owned-houses")
    public ResponseEntity<List<HouseInfoDto>> getHousesByPerson(@PathVariable UUID uuid) {
        return ResponseEntity.ok(personService.findHousesByPersonId(uuid));
    }

    @GetMapping
    public ResponseEntity<List<InfoPersonDto>> getAllPersons(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {

        int pageNumber = (page != null && page > 0) ? page : 1;
        int pageSize = (size != null && size > 0) ? size : 10;

        return ResponseEntity.ok(personService.findAll(pageNumber, pageSize));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<UUID> updatePerson(@PathVariable UUID uuid, @RequestBody PersonDto personDto) {
        return ResponseEntity.ok(personService.update(uuid, personDto));
    }

    @PostMapping
    public ResponseEntity<UUID> createPerson(@RequestBody PersonDto personDto) {
        return ResponseEntity.ok(personService.create(personDto));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deletePerson(@PathVariable UUID uuid) {
        personService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
