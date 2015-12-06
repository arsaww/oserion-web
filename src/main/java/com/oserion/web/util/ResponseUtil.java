package com.oserion.web.util;

/**
 * Created by Arsaww on 12/5/2015.
 */
public enum ResponseUtil {
    LOGIN ("login","200","OK"),
    ADMIN ("admin","200","OK"),
    REST (null,"200","OK"),
    ERROR_401 ("error401","401","You are not allowed to access this page"),
    ERROR_404 ("error404","404","Page not found"),
    ERROR_500 ("error500","500","Internal server error");

    private final String templateName;
    public String getTemplateName() {
        return templateName;
    }

    private final String code;
    public String getCode() {
        return code;
    }

    private final String message;
    public String getMessage() {
        return message;
    }

    ResponseUtil(String templateName, String code, String message) {
        this.templateName = templateName;
        this.code = code;
        this.message = message;
    }
}
