package com.oserion.web.util;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by Arsaww on 12/5/2015.
 */
public enum TemplateResponse {
    LOGIN ("login"),
    ADMIN ("admin"),
    ERROR_401 ("unauthorized"),
    ERROR_404 ("error404"),
    ERROR_500 ("error500");

    private final String templateName;

    TemplateResponse(String templateName) {
        this.templateName = templateName;
    }

    public static final String getView(TemplateResponse v, HttpServletResponse r){
        switch(v){
            case ERROR_404 :
                r.setStatus(HttpServletResponse.SC_NOT_FOUND);
                break;
            case ERROR_500 :
                r.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                break;
            case ERROR_401 :
                r.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                break;
        }
        return v.toString();
    }

    @Override
    public String toString(){
        return this.templateName;
    }
}
