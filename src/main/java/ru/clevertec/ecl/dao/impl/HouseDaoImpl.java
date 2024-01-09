package ru.clevertec.ecl.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dao.HouseDao;
import ru.clevertec.ecl.entity.House;
import ru.clevertec.ecl.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class HouseDaoImpl implements HouseDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<House> findAll(int pageNumber, int pageSize) {
        TypedQuery<House> query = entityManager.createQuery("SELECT h FROM House h", House.class);
        query.setFirstResult((pageNumber - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }

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

    @Override
    @Transactional
    public House create(House house) {
        entityManager.persist(house);
        return house;
    }

    @Override
    @Transactional
    public House update(House house) {
        entityManager.merge(house);
        return house;
    }

    @Override
    @Transactional
    public void delete(UUID uuid) {
        House house = findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException(uuid, House.class));
        entityManager.remove(house);
    }
}
