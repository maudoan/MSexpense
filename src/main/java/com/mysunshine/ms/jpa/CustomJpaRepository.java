package com.mysunshine.ms.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@NoRepositoryBean
public interface CustomJpaRepository<T extends IdEntity, ID extends Serializable> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
    List<T> findAllByIdIn(Set<Long> ids);

    T findFirstById(Long id);
}
