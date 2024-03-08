package com.mysunshine.ms.jpa.dto;

import com.mysunshine.ms.exception.BadRequestAlertException;

import java.net.URI;
import java.util.List;


public class ErrorInfo {
    private URI type;
    private String defaultMessage;
    private String entityName;
    private String errorKey;
    private List<Long> idsFail;
    private String params;
    private Integer attempts;

    public ErrorInfo() {
    }

    public ErrorInfo(String errorKey) {
        this.errorKey = errorKey;
    }

    public ErrorInfo(URI type, String defaultMessage, String entityName, String errorKey) {
        this.type = type;
        this.defaultMessage = defaultMessage;
        this.entityName = entityName;
        this.errorKey = errorKey;
    }

    public BadRequestAlertException toException() {
        return new BadRequestAlertException(this.type, this.defaultMessage, this.entityName, this.errorKey, this.attempts);
    }

    public URI getType() {
        return this.type;
    }

    public void setType(URI type) {
        this.type = type;
    }

    public String getDefaultMessage() {
        return this.defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public String getEntityName() {
        return this.entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getErrorKey() {
        return this.errorKey;
    }

    public void setErrorKey(String errorKey) {
        this.errorKey = errorKey;
    }

    public List<Long> getIdsFail() {
        return this.idsFail;
    }

    public void setIdsFail(List<Long> idsFail) {
        this.idsFail = idsFail;
    }

    public String getParams() {
        return this.params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public Integer getAttempts() {
        return this.attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }
}
