package com.zhezhu.commons.port.adaptor.http.controller;

/**
 * Http适配器响应
 *
 * @author Liguiqing
 * @since V3.0
 */

public class HttpAdaptorResponse {
    public final static String ModelName = "status";

    private boolean success = true;

    private String code = "000000";

    private String msg = "";

    private HttpAdaptorResponse(){

    }
    public static class Builder {
        private HttpAdaptorResponse response;

        private MessageSource messageSource;

        public Builder() {
            this.response = new HttpAdaptorResponse();
        }

        public Builder success() {
            this.response.success = Boolean.TRUE;
            return this;
        }

        public Builder failure() {
            this.response.success = Boolean.FALSE;
            return this;
        }

        public Builder msg(MessageSource messageSource){
            this.messageSource = messageSource;
            return this;
        }

        public Builder code(String code) {
            this.response.code = code;
            return this;
        }

        public HttpAdaptorResponse create() {
            if(this.messageSource != null){
                this.response.msg = this.messageSource.getMessage(this.response.code);
            }
            return this.response;
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}