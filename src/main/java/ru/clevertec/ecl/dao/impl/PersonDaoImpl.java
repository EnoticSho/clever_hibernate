package ru.clevertec.ecl.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dao.PersonDao;
import ru.clevertec.ecl.entity.Person;
import ru.clevertec.ecl.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PersonDaoImpl implements PersonDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<Person> findAll(int pageNumber, int pageSize) {
        TypedQuery<Person> query = entityManager.createQuery("SELECT p FROM Person p", Person.class);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

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

    @Override
    @Transactional
    public Person create(Person person) {
        entityManager.persist(person);
        return person;
    }

    @Override
    @Transactional
    public Person update(Person person) {
        entityManager.merge(person);
        return person;
    }

    @Override
    @Transactional
    public void delete(UUID uuid) {
        Person person = findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException(uuid, Person.class));
        entityManager.remove(person);
    }
}
