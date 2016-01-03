package com.oserion.framework.web.beans.json;

/**
 * Created by Arsaww on 12/5/2015.
 */
public class JsonResponseMessage {

    private String status;
    private String message;

    public JsonResponseMessage(String status, String message){
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
