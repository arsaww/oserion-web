package com.oserion.framework.web.controllers;

import com.oserion.framework.web.beans.Content;
import com.oserion.framework.web.beans.Login;
import com.oserion.framework.web.beans.MessageResponse;
import com.oserion.framework.web.exceptions.AdminLevelRequiredException;
import com.oserion.framework.web.exceptions.InternalErrorException;
import com.oserion.framework.web.util.AuthenticationAccess;
import com.oserion.framework.web.util.JsonBeanBuilder;
import com.oserion.framework.web.util.JsonResponseMessage;
import com.oserion.framework.web.util.ResponseUtil;
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
@RequestMapping("/oserion/services")
public class ServicesOserion extends ControllerOserion {

    private static final Logger LOG = Logger.getLogger(ServicesOserion.class.getName());

    @RequestMapping(value = "/content/{contentId}", method = RequestMethod.GET)
    public Object setContent(@PathVariable String contentId) {
        return new MessageResponse(ResponseUtil.REST_200);
    }

    @RequestMapping(value = "/content/{contentId}", method = RequestMethod.PUT)
    public Object updateContent(@PathVariable String contentId,HttpServletRequest req, HttpServletResponse resp) {
        try {
            if (AuthenticationAccess.isAdmin(req, resp)) {
                Content c = (Content) (new JsonBeanBuilder()).createBean(req, Content.class);
                LOG.log(Level.INFO, "Call to rest Service Content PUT : " + c);
                return new MessageResponse(ResponseUtil.REST_200);
            } else {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return new MessageResponse(ResponseUtil.ERROR_401);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new MessageResponse(ResponseUtil.ERROR_500);
        }
    }

    /**
     * Common sign in method checking login and password from the request attributes
     *
     * @param req
     * @param resp
     * @return admin or login HTML View
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object signIn(HttpServletRequest req, HttpServletResponse resp) throws InternalErrorException {
        try {
            Login l = (Login) (new JsonBeanBuilder()).createBean(req, Login.class);
            if ("admin".equals(l.getLogin()) && "admin".equals(l.getPassword())){
                AuthenticationAccess.setAccess(AuthenticationAccess.SUPERADMIN, req, resp);
                return new JsonResponseMessage("OK","Authentication successfull");
            } else {
                throw new AdminLevelRequiredException();
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "An error occurred while processing the Login request", e);
            throw new InternalErrorException();
        }
    }

}
