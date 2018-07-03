package com.gmail.gak.artem.backend.service;

import com.gmail.gak.artem.backend.entity.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

public interface CrudService<T extends AbstractEntity> {
    JpaRepository<T, Long> getRepository();

    @Transactional
    default T save(T entity) {
        return getRepository().saveAndFlush(entity);
    }

    @Transactional
    default void delete(T entity) {
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        getRepository().delete(entity);
    }

    @Transactional
    default void delete(long id) {
        delete(load(id));
    }

    default long count() {
        return getRepository().count();
    }

    default T load(long id) {
        T entity = getRepository().findById(id).orElse(null);
        if (entity == null) {
            throw new EntityNotFoundException();
        }
        return entity;
    }

    T createNew();
}
