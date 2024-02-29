package com.mysunshine.ms.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

/**
 * Created by ms
 * Date: 27/02/2024
 * Time: 09:29 PM
 * for all issues, contact me: maudoanxb@gmail.com
 **/
public abstract class CrudEndpoint<T extends IdEntity, ID extends Serializable> {
    private static Logger logger = LoggerFactory.getLogger(CrudEndpoint.class);

    protected CrudService<T, ID> service;

    protected String baseUrl;

    public CrudEndpoint(CrudService service) {
        this.service = service;
    }

    @GetMapping(value = "{id}")
    public T get(@PathVariable(value = "id") ID id) {
        return service.get(id);
    }

    @PostMapping
    public T create(@RequestBody T entity) {
        return service.create(entity);
    }

}
