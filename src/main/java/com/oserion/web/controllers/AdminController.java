package com.oserion.web.controllers;

import com.oserion.web.util.AuthenticationAccess;
import com.oserion.web.util.TemplateResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Arsaww on 12/5/2015.
 * Main controller for all expected Admin User Interface response
 */
@Controller
public class AdminController {

    private static final Logger LOG = Logger.getLogger(AdminController.class.getName());
    private static final String AUTHENTICATION_ACCESS = "osr-access";
    private static final String AUTHENTICATION_LOGIN = "login";
    private static final String AUTHENTICATION_PASSWORD = "password";

    /**
     * This method access to the User Admin Interface if the the session has enough access to reach it.
     *
     * @param request
     * @param response
     * @return HTML View
     */
    @RequestMapping(value = "/oserion/admin", method = RequestMethod.GET)
    public String accessAdminUserInterface(HttpServletRequest request, HttpServletResponse response) {
        LOG.log(Level.INFO, "accessAdminUserInterface");
        try {
            Object access = request.getSession().getAttribute(AUTHENTICATION_ACCESS);
            if (access != null) {
                switch ((AuthenticationAccess) access) {
                    case SUPERADMIN:
                        return TemplateResponse.getView(TemplateResponse.ADMIN, response);
                    case ADMIN:
                        return TemplateResponse.getView(TemplateResponse.ADMIN, response);
                    case USER:
                        return TemplateResponse.getView(TemplateResponse.ERROR_401, response);
                }
            }
            return TemplateResponse.getView(TemplateResponse.LOGIN, response);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "An error occurred while processing the request", e);
            return TemplateResponse.getView(TemplateResponse.ERROR_500, response);
        }
    }

    /**
     * Common sign in method checking login and password from the request attributes
     *
     * @param request
     * @param response
     * @return admin or login HTML View
     */
    @RequestMapping(value = "/oserion/signin")
    public String signIn(HttpServletRequest request, HttpServletResponse response) {
        try {
            String login = request.getParameter(AUTHENTICATION_LOGIN);
            String password = request.getParameter(AUTHENTICATION_PASSWORD);

            //TODO : Call a true authentication system
            if (login != null && password != null &&
                    login.equals("admin") && password.equals("admin"))
                request.getSession().setAttribute(AUTHENTICATION_ACCESS, AuthenticationAccess.SUPERADMIN);
                Cookie authCookie = new Cookie(AUTHENTICATION_ACCESS, AuthenticationAccess.SUPERADMIN.toString());
                authCookie.setPath("/");
                response.addCookie(authCookie);

            return accessAdminUserInterface(request, response);

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "An error occurred while processing the request", e);
            return TemplateResponse.getView(TemplateResponse.ERROR_500, response);
        }
    }

}
