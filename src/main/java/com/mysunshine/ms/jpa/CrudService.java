package com.mysunshine.ms.jpa;

import com.mysunshine.ms.exception.ErrorKey;
import com.mysunshine.ms.jpa.dto.ErrorInfo;
import com.mysunshine.ms.jpa.dto.PageInfo;
import com.mysunshine.ms.jpa.dto.SearchInfo;
import com.mysunshine.ms.jpa.rsql.CustomRsqlVisitor;
import com.mysunshine.ms.util.ObjectMapperUtil;
import com.mysunshine.ms.util.SecurityUtils;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

@Transactional
public abstract class CrudService<T extends IdEntity, ID extends Serializable> {
    private static final Logger logger = LoggerFactory.getLogger(CrudService.class);
    protected CustomJpaRepository<T, ID> repository;
    protected final Class<T> typeParameterClass;
    @PersistenceContext
    protected EntityManager entityManager;
    protected MessageSource messageSource;

    public Class<T> getTypeParameterClass() {
        return typeParameterClass;
    }

    public CrudService(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public T get(ID id) {
        return repository.findById(id).orElse(null);
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
    public void delete(ID id) {
        try {
            T entity = get(id);
            repository.delete(entity);;
        } catch (DataIntegrityViolationException e) {
            ErrorInfo errorInfo = new ErrorInfo();
            errorInfo.setErrorKey(ErrorKey.CommonErrorKey.REMOVE_RELATIVE_ENTITY);
            ErrorKey.processError(errorInfo);
        }

    }

    public T update(ID id, T entity) {
        logger.info("Update Entity #{} with id #{}", typeParameterClass.getSimpleName(), id);
        entity.setUpdated(System.currentTimeMillis());
        entity.setUpdatedBy(SecurityUtils.getCurrentUserLogin());
        T old = get(id);
        if (old == null) {
            throw new EntityNotFoundException("No entity with id " + id);
        }

        if (entity.getCreated() == null) {
            entity.setCreated(old.getCreated());
        }

        if (entity.getCreatedBy() == null) {
            entity.setCreatedBy(old.getCreatedBy());
        }

        repository.save(entity);
        return entity;
    }
    public PageInfo search(String query, Pageable pageable) {
        logger.info("Search Entity #{} with query #{}", typeParameterClass.getSimpleName(), query);
        SearchInfo searchInfo = new SearchInfo(query, pageable);
        String orders = searchInfo.getOrders();
        if (orders == null || "".equals(orders)) {
            pageable = PageRequest.of(searchInfo.getPageNumber(), searchInfo.getPageSize());
        }
        Page<T> page = searchByQuery(searchInfo.getQuery(), pageable);
        PageInfo pageInfo = new PageInfo();
        pageInfo.setTotalCount(page.getTotalElements());
        pageInfo.setData(ObjectMapperUtil.toJsonString(page.getContent()));
        return pageInfo;
    }

    public Page<T> searchByQuery(String query, Pageable pageable) {
        if (StringUtils.isEmpty(query)) {
            return repository.findAll(pageable);
        }
        try {
            Node rootNode = new RSQLParser().parse(query);
            Specification<T> spec = rootNode.accept(new CustomRsqlVisitor<T>());
            return repository.findAll(spec, pageable);
        } catch (Exception e) {
            logger.error("SEARCH FAIL: {}", query);
            logger.error(e.getMessage(), e);
            return emptyPage();
        }
    }

    public Page<T> emptyPage() {
        return new Page<T>() {
            @Override
            public int getTotalPages() {
                return 0;
            }

            @Override
            public long getTotalElements() {
                return 0;
            }

            @Override
            public <U> Page<U> map(Function<? super T, ? extends U> function) {
                return null;
            }

            @Override
            public int getNumber() {
                return 0;
            }

            @Override
            public int getSize() {
                return 0;
            }

            @Override
            public int getNumberOfElements() {
                return 0;
            }

            @Override
            public List<T> getContent() {
                return new ArrayList<>();
            }

            @Override
            public boolean hasContent() {
                return false;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public boolean isFirst() {
                return false;
            }

            @Override
            public boolean isLast() {
                return false;
            }

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }

            @Override
            public Pageable nextPageable() {
                return null;
            }

            @Override
            public Pageable previousPageable() {
                return null;
            }

            @Override
            public Iterator<T> iterator() {
                return null;
            }
        };
    }
}
