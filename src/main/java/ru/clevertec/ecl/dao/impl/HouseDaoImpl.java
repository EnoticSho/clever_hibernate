package ru.clevertec.ecl.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dao.HouseDao;
import ru.clevertec.ecl.entity.House;
import ru.clevertec.ecl.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Реализация интерфейса HouseDao для взаимодействия с базой данных и сущностью House.
 * Этот класс предоставляет методы для выполнения операций CRUD (создание, чтение, обновление, удаление)
 * с объектами House в базе данных.
 */
@Repository
public class HouseDaoImpl implements HouseDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Реализация интерфейса HouseDao для взаимодействия с базой данных и сущностью House.
     * Этот класс предоставляет методы для выполнения операций CRUD (создание, чтение, обновление, удаление)
     * с объектами House в базе данных.
     */
    @Override
    @Transactional(readOnly = true)
    public List<House> findAll(int pageNumber, int pageSize) {
        TypedQuery<House> query = entityManager.createQuery("SELECT h FROM House h", House.class);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    /**
     * Находит дом по его уникальному идентификатору (UUID).
     *
     * @param uuid Уникальный идентификатор дома для поиска.
     * @return Optional объект House, если дом найден, или пустой Optional, если дом не найден.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<House> findByUuid(UUID uuid) {
        try {
            return Optional.ofNullable(entityManager.createQuery("SELECT h FROM House h WHERE h.uuid = :uuid", House.class)
                    .setParameter("uuid", uuid)
                    .getSingleResult());
        } catch (jakarta.persistence.NoResultException e) {
            return Optional.empty();
        }
    }

    /**
     * Создает новую запись о доме в базе данных.
     *
     * @param house Объект House, который нужно создать.
     * @return Созданный объект House.
     */
    @Override
    @Transactional
    public House create(House house) {
        String sql = "INSERT INTO House (uuid, area, country, city, street, number, create_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                house.getUuid(),
                house.getArea(),
                house.getCountry(),
                house.getCity(),
                house.getStreet(),
                house.getNumber(),
                house.getCreateDate()
        );
        return house;
    }

    /**
     * Обновляет запись о доме в базе данных.
     *
     * @param house Объект House, который нужно обновить.
     * @return Обновленный объект House.
     */
    @Override
    @Transactional
    public House update(House house) {
        entityManager.merge(house);
        return house;
    }

    /**
     * Удаляет запись о доме из базы данных по его уникальному идентификатору.
     *
     * @param uuid Уникальный идентификатор дома, который нужно удалить.
     */
    @Override
    @Transactional
    public void delete(UUID uuid) {
        House house = findByUuid(uuid)
                .orElseThrow(() -> ResourceNotFoundException.of(uuid, House.class));
        entityManager.remove(house);
    }
}
