package com.mysunshine.ms.exception;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;
import org.zalando.problem.ThrowableProblem;

public class BadRequestAlertException extends AbstractThrowableProblem {
    private final String entityName;
    private final String errorKey;
    private Integer attempt;

    public BadRequestAlertException(String defaultMessage, String entityName, String errorKey) {
        this(ErrorConstants.DEFAULT_TYPE, defaultMessage, entityName, errorKey);
    }

    public BadRequestAlertException(String defaultMessage, String entityName, String errorKey, Integer attempt) {
        this(ErrorConstants.DEFAULT_TYPE, defaultMessage, entityName, errorKey, attempt);
    }

    public BadRequestAlertException(String entityName, String errorKey) {
        this("Something went wrong", entityName, errorKey);
    }

    public BadRequestAlertException(URI type, String defaultMessage, String entityName, String errorKey) {
        super(type, defaultMessage, Status.BAD_REQUEST, (String)null, (URI)null, (ThrowableProblem)null, getAlertParameters(entityName, errorKey));
        this.entityName = entityName;
        this.errorKey = errorKey;
    }

    public BadRequestAlertException(URI type, String defaultMessage, String entityName, String errorKey, Integer attempt) {
        super(type, defaultMessage, Status.BAD_REQUEST, (String)null, (URI)null, (ThrowableProblem)null, getAlertParameters(entityName, errorKey, attempt));
        this.entityName = entityName;
        this.errorKey = errorKey;
    }

    public String getEntityName() {
        return this.entityName;
    }

    public String getErrorKey() {
        return this.errorKey;
    }

    public Integer getAttempt() {
        return this.attempt;
    }

    private static Map<String, Object> getAlertParameters(String entityName, String errorKey, Integer attempt) {
        Map<String, Object> parameters = new HashMap();
        parameters.put("message", "global.messages." + errorKey);
        parameters.put("params", entityName);
        parameters.put("attempt", attempt);
        return parameters;
    }

    private static Map<String, Object> getAlertParameters(String entityName, String errorKey) {
        Map<String, Object> parameters = new HashMap();
        parameters.put("message", "global.messages." + errorKey);
        parameters.put("params", entityName);
        return parameters;
    }
}
