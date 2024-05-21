package com.mysunshine.ms.constant.constants;

public class Constants {
    public static final String AUTH_TOKEN_PREFIX = "Bearer ";
    public static final String AUTH_HEADER_STRING = "Authorization";
    public static final String ROLE_SYSTEM_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_SYSTEM_USER = "ROLE_USER";
    public static final String ACCESS_DENIED = "error.ms.accessDenied";
    public static final String ACCESS_UNAUTHORIZED = "error.ms.unauthorized";

    public static final class ResponseStatus {
        public static final int TIME_OUT = -1;
        public static final int SUCCESS = 0;
        public static final int ERROR = 1;

        private ResponseStatus() {
        }
    }
}
