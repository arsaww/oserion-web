package com.oserion.framework.web.controllers;


import com.oserion.framework.api.Api418Facade;
import com.oserion.framework.web.exceptions.AdminLevelRequiredException;
import com.oserion.framework.web.util.AppConfig;
import com.oserion.framework.web.util.AuthenticationAccess;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ControllerOserion {

    protected void checkAdminAccess(HttpServletRequest req, HttpServletResponse resp) throws AdminLevelRequiredException {
        if(!AuthenticationAccess.isAdmin(req, resp)) throw new AdminLevelRequiredException();
    }

    protected ServletContext getContext(HttpServletRequest request){
        return request.getSession().getServletContext();
    }

    protected Api418Facade getApiFacade(HttpServletRequest request){
        return getSpringContext(request).getBean(Api418Facade.class);
    }

    protected AnnotationConfigApplicationContext getSpringContext(HttpServletRequest request){
        return (AnnotationConfigApplicationContext) getContext(request).getAttribute(AppConfig.PROPERTY_SPRING_CONTEXT_API);
    }
}
