//package ru.clevertec.ecl.dao.impl;
//
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.persistence.TypedQuery;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//import ru.clevertec.ecl.dao.genericDao;
//import ru.clevertec.ecl.exception.ResourceNotFoundException;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Repository
//public class GenericDaoImpl<T> implements genericDao<T> {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    private final Class<T> type;
//
//    public GenericDaoImpl(Class<T> type) {
//        this.type = type;
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<T> findAll(int pageNumber, int pageSize) {
//        TypedQuery<T> query = entityManager.createQuery("SELECT t FROM " + type.getSimpleName() + " t", type);
//        query.setFirstResult((pageNumber - 1) * pageSize);
//        query.setMaxResults(pageSize);
//        return query.getResultList();
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public Optional<T> findByUuid(UUID uuid) {
//        try {
//            return Optional.ofNullable(entityManager.createQuery("SELECT t FROM " + type.getSimpleName() + " t WHERE t.uuid = :uuid", type)
//                    .setParameter("uuid", uuid)
//                    .getSingleResult());
//        } catch (jakarta.persistence.NoResultException e) {
//            return Optional.empty();
//        }
//    }
//
//    @Override
//    @Transactional
//    public T create(T t) {
//        entityManager.persist(t);
//        return t;
//    }
//
//    @Override
//    @Transactional
//    public T update(T t) {
//        entityManager.merge(t);
//        return t;
//    }
//
//    @Override
//    @Transactional
//    public void delete(UUID uuid) {
//        T t = findByUuid(uuid)
//                .orElseThrow(() -> new ResourceNotFoundException(uuid, type));
//        entityManager.remove(t);
//    }
//}
