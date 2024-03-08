package com.mysunshine.ms.jpa.dto;

import java.util.Set;
import org.springframework.data.domain.Pageable;

public class SearchInfo {
    private String query;
    private int pageNumber;
    private int pageSize;
    private String orders;
    private Long parentId;
    private Set<Long> areaIds;
    private Set<Long> roleIds;

    public SearchInfo() {
    }

    public SearchInfo(String query, Pageable pageable) {
        this.query = query;
        this.pageNumber = pageable.getPageNumber();
        this.pageSize = pageable.getPageSize();
        if (pageable != null && pageable.getSort() != null && !pageable.getSort().toString().equals("UNSORTED")) {
            this.orders = pageable.getSort().toString();
        } else {
            this.orders = "";
        }

    }

    public SearchInfo(Long parentId, String query, Pageable pageable) {
        this.parentId = parentId;
        this.query = query;
        this.pageNumber = pageable.getPageNumber();
        this.pageSize = pageable.getPageSize();
        if (pageable != null && pageable.getSort() != null && !pageable.getSort().toString().equals("UNSORTED")) {
            this.orders = pageable.getSort().toString();
        } else {
            this.orders = "";
        }

    }

    public Set<Long> getAreaIds() {
        return this.areaIds;
    }

    public void setAreaIds(Set<Long> areaIds) {
        this.areaIds = areaIds;
    }

    public String getQuery() {
        return this.query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrders() {
        return this.orders;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Set<Long> getRoleIds() {
        return this.roleIds;
    }

    public void setRoleIds(Set<Long> roleIds) {
        this.roleIds = roleIds;
    }
}
