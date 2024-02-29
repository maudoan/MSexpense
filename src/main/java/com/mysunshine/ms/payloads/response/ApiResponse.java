package com.mysunshine.ms.payloads.response;

public class ApiResponse {
    private Integer status;
    private String message;
    private Object data;

    public ApiResponse() {
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ApiResponse.Builder getBuilder() {
        return new ApiResponse.Builder();
    }

    public static class Builder {
        private Integer status;
        private String message;
        private Object data;

        public Builder() {
        }

        public ApiResponse.Builder withStatus(Integer status) {
            this.status = status;
            return this;
        }

        public ApiResponse.Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public ApiResponse.Builder withData(Object data) {
            this.data = data;
            return this;
        }

        public ApiResponse build() {
            ApiResponse apiOutput = new ApiResponse();
            apiOutput.setStatus(this.status);
            apiOutput.setMessage(this.message);
            apiOutput.setData(this.data);
            return apiOutput;
        }
    }
}
