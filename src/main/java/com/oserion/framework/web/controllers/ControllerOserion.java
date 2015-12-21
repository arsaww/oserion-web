package com.oserion.framework.web.controllers;


import com.oserion.framework.web.exceptions.AdminLevelRequiredException;
import com.oserion.framework.web.util.AppConfig;
import com.oserion.framework.web.util.AuthenticationAccess;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerOserion {

    protected void checkAdminAccess(HttpServletRequest req, HttpServletResponse resp) throws AdminLevelRequiredException {
        if(!AuthenticationAccess.isAdmin(req, resp)) throw new AdminLevelRequiredException();
    }

    protected ServletContext getContext(HttpServletRequest request){
        return request.getSession().getServletContext();
    }

    protected AnnotationConfigApplicationContext getSpringContext(HttpServletRequest request){
        return (AnnotationConfigApplicationContext) getContext(request).getAttribute(AppConfig.PROPERTY_SPRING_CONTEXT_API);
    }

}
