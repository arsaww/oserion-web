package com.oserion.framework.web.controllers;


import com.oserion.framework.web.util.ConfigWebApp;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Arsaww on 12/10/2015.
 */
public class OserionController {
    protected ServletContext getContext(HttpServletRequest request){
        return request.getSession().getServletContext();
    }
    protected AnnotationConfigApplicationContext getSpringContext(HttpServletRequest request){
        return (AnnotationConfigApplicationContext) getContext(request).getAttribute(ConfigWebApp.PROPERTY_SPRING_CONTEXT);
    }

}
