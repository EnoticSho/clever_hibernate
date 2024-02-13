package ru.clevertec.ecl.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dao.PersonDao;
import ru.clevertec.ecl.entity.Person;
import ru.clevertec.ecl.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Реализация интерфейса PersonDao для взаимодействия с базой данных и сущностью Person.
 * Этот класс предоставляет методы для выполнения операций CRUD (создание, чтение, обновление, удаление)
 * с объектами Person в базе данных.
 */
@Repository
@RequiredArgsConstructor
public class PersonDaoImpl implements PersonDao {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Получает список персон с возможностью пагинации.
     *
     * @param pageNumber Номер страницы для пагинации.
     * @param pageSize   Размер страницы для пагинации.
     * @return Список объектов Person, соответствующих заданным параметрам пагинации.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Person> findAll(int pageNumber, int pageSize) {
        TypedQuery<Person> query = entityManager.createQuery("SELECT p FROM Person p", Person.class);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

    /**
     * Находит персону по её уникальному идентификатору (UUID).
     *
     * @param uuid Уникальный идентификатор персоны для поиска.
     * @return Optional объект Person, если персона найдена, или пустой Optional, если персона не найдена.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Person> findByUuid(UUID uuid) {
        try {
            return Optional.ofNullable(entityManager.createQuery("SELECT p FROM Person p WHERE p.uuid = :uuid", Person.class)
                    .setParameter("uuid", uuid)
                    .getSingleResult());
        } catch (jakarta.persistence.NoResultException e) {
            return Optional.empty();
        }
    }

    /**
     * Создает новую запись о персоне в базе данных.
     *
     * @param person Объект Person, который нужно создать.
     * @return Созданный объект Person.
     */
    @Override
    @Transactional
    public Person create(Person person) {
        entityManager.persist(person);
        return person;
    }

    /**
     * Обновляет запись о персоне в базе данных.
     *
     * @param person Объект Person, который нужно обновить.
     * @return Обновленный объект Person.
     */
    @Override
    @Transactional
    public Person update(Person person) {
        entityManager.merge(person);
        return person;
    }

    /**
     * Удаляет запись о персоне из базы данных по её уникальному идентификатору.
     *
     * @param uuid Уникальный идентификатор персоны, который нужно удалить.
     */
    @Override
    @Transactional
    public void delete(UUID uuid) {
        Person person = findByUuid(uuid)
                .orElseThrow(() -> ResourceNotFoundException.of(uuid, Person.class));
        entityManager.remove(person);
    }
}
