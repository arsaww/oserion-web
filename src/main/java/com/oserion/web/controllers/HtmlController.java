package com.oserion.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Arsaww on 12/5/2015.
 * Main controller for all expected HTML response
 */
@Controller
@RequestMapping("*")
public class HtmlController {

    private static final Logger LOG = Logger.getLogger(HtmlController.class.getName());

    //TODO: direct mapping to temp.html for the moment
    //@ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public String getHTMLPage(HttpServletRequest req) {
        LOG.log(Level.INFO, "getHTMLPage");
        //return "HTML PAGE";
        return "temp";
    }
}
