package com.mysunshine.ms.jpa.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(
    ignoreUnknown = true
)
public class PageInfo {
    private long totalCount;
    private String data;

    public PageInfo() {
    }

    public long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public String getData() {
        return this.data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
