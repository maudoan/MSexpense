package com.mysunshine.ms.jpa;

import com.mysunshine.ms.util.SecurityUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import java.io.Serializable;

@Transactional
public abstract class CrudService<T extends IdEntity, ID extends Serializable> {
    private static final Logger logger = LoggerFactory.getLogger(CrudService.class);
    protected CustomJpaRepository<T, ID> repository;
    protected final Class<T> typeParameterClass;
    @PersistenceContext
    protected EntityManager entityManager;
    protected MessageSource messageSource;

    public CrudService(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public T get(ID id) {
        T entity = repository.findById(id).orElse(null);
        return entity;
    }
    public T create(T entity) {
        entity.setCreated(System.currentTimeMillis());
        if (entity.getCreatedBy() == null) {
            String currentUsername = SecurityUtils.getCurrentUserLogin();
            entity.setCreatedBy(currentUsername);
        }
        repository.save(entity);
        return entity;
    }
}
