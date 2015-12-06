package com.oserion.web.bean;

import com.oserion.web.util.ResponseUtil;

/**
 * Created by Arsaww on 12/6/2015.
 */
public class JsonMessageResponse {

    public JsonMessageResponse() {
    }

    public JsonMessageResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public JsonMessageResponse(ResponseUtil r) {
        this.status = r.getCode();
        this.message = r.getMessage();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;


}
