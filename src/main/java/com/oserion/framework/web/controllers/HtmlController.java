package com.oserion.framework.web.controllers;

import com.oserion.framework.web.util.AuthenticationAccess;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

/**
 * Created by Arsaww on 12/5/2015.
 * Main controller for all expected HTML response
 */
@Controller
public class HtmlController extends OserionController {

    private static final Logger LOG = Logger.getLogger(HtmlController.class.getName());

    @ResponseBody
    @RequestMapping(value = "/**html", method = RequestMethod.GET)
    public String getHTMLPage(HttpServletRequest req,HttpServletResponse resp) {
        AuthenticationAccess.checkAccess(req, resp);
        return getApiFacade(req).getFullTemplateFromName(req.getRequestURI());
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getIndexPage(){
        return "redirect:/index.html";
    }
}

