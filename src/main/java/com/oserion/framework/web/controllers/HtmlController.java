package com.oserion.framework.web.controllers;

import com.oserion.framework.api.Api418Facade;
import com.oserion.framework.api.business.IDBConnection;
import com.oserion.framework.web.util.AuthenticationAccess;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    private final String PROPERTY_DB_CONNECTION = "database.connection";

    //TODO: direct mapping to temp.html for the moment
    //@ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public String getHTMLPage(HttpServletRequest req,HttpServletResponse resp) {
        AuthenticationAccess.checkAccess(req, resp);
        LOG.log(Level.INFO, "getHTMLPage");

        //TODO Je viens de faire ca ce matin parce que ca me démangeait, bonne journée ;)
         Api418Facade facade = new Api418Facade(
                 (IDBConnection)req.getSession().getServletContext().getAttribute(PROPERTY_DB_CONNECTION));

        //TODO récupérer le vrai HTML ici à partir de facade

        return "temp";
    }
}

