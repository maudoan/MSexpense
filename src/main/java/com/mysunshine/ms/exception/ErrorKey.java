package com.mysunshine.ms.exception;

import com.mysunshine.ms.jpa.dto.ErrorInfo;
import com.mysunshine.ms.util.ObjectMapperUtil;

public class ErrorKey {
    private ErrorKey() {
    }

    public static final class CommonErrorKey {
        private CommonErrorKey() {
        }

        public static final String REMOVE_SYSTEM_ENTITY = "error.common.removeSystemEntity";
        public static final String TIME_OUT_REQUEST = "error.common.timeout";
        public static final String REMOVE_RELATIVE_ENTITY = "error.common.removeRelativeEntity";
        public static final String NOT_FOUND_ID = "error.common.notFoundId";
        public static final String ACCESS_DENIED = "error.common.accessDenied";
        public static final String EMPTY_DATA = "error.common.emptyData";
    }

    public static void processError(ErrorInfo errorInfo) {
        String key = errorInfo.getErrorKey();
        String idFailds = ObjectMapperUtil.toJsonString(errorInfo.getIdsFail());
        String params = errorInfo.getParams();
        Integer attempts = errorInfo.getAttempts();

        switch (key) {
            case CommonErrorKey.REMOVE_RELATIVE_ENTITY:
                throw new BadRequestAlertException("", "Entity has relative data", key);
            default:
                throw new BadRequestAlertException("", errorInfo.getErrorKey());
        }
    }
}
