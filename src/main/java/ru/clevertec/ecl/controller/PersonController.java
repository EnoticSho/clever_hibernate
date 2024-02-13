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

/**
 * Контроллер для управления персонами в API.
 * Этот класс обрабатывает HTTP-запросы, связанные с персонами, и предоставляет методы для получения информации о персонах,
 * домах, которыми владеют персоны, создания, обновления и удаления персон.
 */
@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    /**
     * Обработчик HTTP GET-запроса для получения информации о персоне по её уникальному идентификатору.
     *
     * @param uuid Уникальный идентификатор персоны.
     * @return ResponseEntity с информацией о персоне в виде объекта InfoPersonDto.
     */
    @GetMapping("/{uuid}")
    public ResponseEntity<InfoPersonDto> getPerson(@PathVariable UUID uuid) {
        return ResponseEntity.ok(personService.findById(uuid));
    }

    /**
     * Обработчик HTTP GET-запроса для получения списка домов, которыми владеет персона, по уникальному идентификатору персоны.
     *
     * @param uuid Уникальный идентификатор персоны.
     * @return ResponseEntity со списком домов в виде объектов HouseInfoDto.
     */
    @GetMapping("/{uuid}/owned-houses")
    public ResponseEntity<List<HouseInfoDto>> getHousesByPerson(@PathVariable UUID uuid) {
        return ResponseEntity.ok(personService.findHousesByPersonId(uuid));
    }

    /**
     * Обработчик HTTP GET-запроса для получения списка всех персон с возможностью пагинации.
     *
     * @param page Номер страницы (необязательно).
     * @param size Размер страницы (необязательно).
     * @return ResponseEntity со списком информации о персонах в виде объектов InfoPersonDto.
     */
    @GetMapping
    public ResponseEntity<List<InfoPersonDto>> getAllPersons(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "size", required = false) Integer size) {

        int pageNumber = (page != null && page > 0) ? page : 1;
        int pageSize = (size != null && size > 0) ? size : 10;

        return ResponseEntity.ok(personService.findAll(pageNumber, pageSize));
    }

    /**
     * Обработчик HTTP PUT-запроса для обновления информации о персоне по её уникальному идентификатору.
     *
     * @param uuid      Уникальный идентификатор персоны, которую нужно обновить.
     * @param personDto Объект PersonDto с обновленной информацией о персоне.
     * @return ResponseEntity с уникальным идентификатором обновленной персоны.
     */
    @PutMapping("/{uuid}")
    public ResponseEntity<UUID> updatePerson(@PathVariable UUID uuid, @RequestBody PersonDto personDto) {
        return ResponseEntity.ok(personService.update(uuid, personDto));
    }

    /**
     * Обработчик HTTP POST-запроса для создания новой персоны с заданными данными.
     *
     * @param personDto Объект PersonDto с данными для создания новой персоны.
     * @return ResponseEntity с уникальным идентификатором созданной персоны.
     */
    @PostMapping
    public ResponseEntity<UUID> createPerson(@RequestBody PersonDto personDto) {
        return ResponseEntity.ok(personService.create(personDto));
    }

    /**
     * Обработчик HTTP DELETE-запроса для удаления персоны по её уникальному идентификатору.
     *
     * @param uuid Уникальный идентификатор персоны, которую нужно удалить.
     * @return ResponseEntity с кодом "204 No Content" после успешного удаления.
     */
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deletePerson(@PathVariable UUID uuid) {
        personService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
