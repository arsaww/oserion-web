package com.oserion.web.controllers;

import com.oserion.web.bean.Content;
import com.oserion.web.bean.JsonMessageResponse;
import com.oserion.web.util.AuthenticationAccess;
import com.oserion.web.util.JsonBeanBuilder;
import com.oserion.web.util.ResponseUtil;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
public class ServicesController {

    private static final Logger LOG = Logger.getLogger(ServicesController.class.getName());

    @RequestMapping(value = "/content/{contentId}", method = RequestMethod.GET)
    public Object setContent(@PathVariable String contentId) {
        return new JsonMessageResponse(ResponseUtil.REST);
    }

    @RequestMapping(value = "/content/{contentId}", method = RequestMethod.PUT)
    public Object updateContent(@PathVariable String contentId,HttpServletRequest req, HttpServletResponse resp) {
        try {
            if (AuthenticationAccess.isAdmin(req)) {
                Content c = (Content) (new JsonBeanBuilder()).createBean(req, Content.class);
                LOG.log(Level.INFO, "Call to rest Service Content PUT : " + c);
                return new JsonMessageResponse(ResponseUtil.REST);
            } else {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return new JsonMessageResponse(ResponseUtil.ERROR_401);
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return new JsonMessageResponse(ResponseUtil.ERROR_500);
        }
    }
}
