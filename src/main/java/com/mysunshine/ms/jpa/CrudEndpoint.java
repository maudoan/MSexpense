package com.mysunshine.ms.jpa;

import com.mysunshine.ms.jpa.dto.PageInfo;
import com.mysunshine.ms.util.ObjectMapperUtil;
import com.mysunshine.ms.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

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

    @DeleteMapping(value = "{id}")
    public void delete(@PathVariable(value = "id") ID id){
        service.delete(id);
    }

    @PutMapping(value = "{id}")
    public T update(@PathVariable(value = "id") ID id, @RequestBody T entity){
        return service.update(id, entity);
    }

    @GetMapping(path = "/search")
    public ResponseEntity<List<T>> get(@RequestParam("query") String query, @PageableDefault(size = 20) Pageable pageable) {
        logger.info("#{}", query);
        try {
            PageInfo pageInfo = service.search(query, pageable);
            List<T> list = ObjectMapperUtil.listMapper(pageInfo.getData(), service.getTypeParameterClass());
            Page<T> page = new PageImpl<>(list, pageable, pageInfo.getTotalCount());
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, baseUrl);
            return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
