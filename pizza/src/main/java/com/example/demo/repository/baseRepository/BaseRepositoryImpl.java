package com.example.demo.repository.baseRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public abstract class BaseRepositoryImpl<T> implements CreateRepository<T>, UpdateRepository<T>, GetRepository<T> {
    @PersistenceContext
    private EntityManager entityManager;

    private final Class<T> domainClass;

    public BaseRepositoryImpl(Class<T> domainClass) {
        this.domainClass = domainClass;
    }

    @Override
    public Optional<T> findById(int id) {
        return Optional.ofNullable(entityManager.find(domainClass, id));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll() {
        return entityManager.createQuery("Select t from " + domainClass.getSimpleName() + " t ORDER BY t.id ASC").getResultList();
    }
    @Transactional
    @Override
    public T save(T value) {
        entityManager.persist(value);
        return value;
    }

    @Transactional
    @Override
    public void update(T value) {
        entityManager.merge(value);
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    protected Class<T> getDomainClass() {
        return domainClass;
    }
}
