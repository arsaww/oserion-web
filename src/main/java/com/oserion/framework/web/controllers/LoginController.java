package com.oserion.framework.web.controllers;

import com.oserion.framework.web.beans.json.JsonLogin;
import com.oserion.framework.web.beans.json.JsonResponseMessage;
import com.oserion.framework.web.exceptions.AdminLevelRequiredException;
import com.oserion.framework.web.exceptions.InternalErrorException;
import com.oserion.framework.web.util.AuthenticationAccess;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Arsaww on 12/5/2015.
 * Main controller for all expected Rest Services response
 */
@RestController
public class LoginController extends OserionController {

    private static final Logger LOG = Logger.getLogger(LoginController.class.getName());

    /**
     * Common sign in method checking login and password from the request attributes
     *
     * @param req
     * @param resp
     * @return admin or login HTML View
     */
    @RequestMapping(value = "/oserion/services/login", method = RequestMethod.POST)
    public Object signIn(@RequestBody JsonLogin l, HttpServletRequest req, HttpServletResponse resp)
            throws InternalErrorException {
        try {
            if ("admin".equals(l.getLogin()) && "admin".equals(l.getPassword())){
                AuthenticationAccess.setAccess(AuthenticationAccess.SUPERADMIN, req, resp);
                return new JsonResponseMessage("OK","Authentication successful");
            } else {
                throw new AdminLevelRequiredException();
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "An error occurred while processing the Login request", e);
            throw new InternalErrorException();
        }
    }

}
