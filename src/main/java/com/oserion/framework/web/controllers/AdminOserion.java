package com.oserion.framework.web.controllers;

import com.oserion.framework.web.exceptions.AdminLevelRequiredException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

/**
 * Created by Arsaww on 12/5/2015.
 * Main controller for all expected Admin User Interface response
 */
@Controller
public class AdminOserion extends ControllerOserion {

    private static final Logger LOG = Logger.getLogger(AdminOserion.class.getName());

    private static final String AUTHENTICATION_LOGIN = "login";
    private static final String AUTHENTICATION_PASSWORD = "password";

    /**
     * This method access to the User Admin Interface if the the session has enough access to reach it.
     *
     * @return HTML View
     */
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String accessAdminUserInterface(HttpServletRequest req, HttpServletResponse resp) throws AdminLevelRequiredException {
        return "redirect:/admin/index.html";
    }

    /**
     * Common sign in method checking login and password from the request attributes
     *
     * @param request
     * @param response
     * @return admin or login HTML View
     */
   /* @RequestMapping(value = "/oserion/signin")
    public String signIn(HttpServletRequest request, HttpServletResponse response) {
        try {
            String login = request.getParameter(AUTHENTICATION_LOGIN);
            String password = request.getParameter(AUTHENTICATION_PASSWORD);

            //TODO : Call a true authentication system
            if (login != null && password != null && login.equals("admin") && password.equals("admin")){
                AuthenticationAccess.setAccess(AuthenticationAccess.SUPERADMIN, request, response);
                return accessAdminUserInterface(request, response);
            }else {
                return ResponseUtil.LOGIN.getTemplateName();
            }

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "An error occurred while processing the request", e);
            return ResponseUtil.ERROR_500.getTemplateName();
        }
    }*/

}
