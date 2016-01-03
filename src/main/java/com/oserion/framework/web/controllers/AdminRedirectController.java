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
public class AdminRedirectController extends OserionController {

    private static final Logger LOG = Logger.getLogger(AdminRedirectController.class.getName());

    /**
     * This method access to the User Admin Interface if the the session has enough access to reach it.
     *
     * @return HTML View
     */
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String accessAdminUserInterface(HttpServletRequest req, HttpServletResponse resp) throws AdminLevelRequiredException {
        return "redirect:/admin/index.html";
    }

}
