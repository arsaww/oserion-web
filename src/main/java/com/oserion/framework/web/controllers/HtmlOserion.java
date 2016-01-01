package com.oserion.framework.web.controllers;

import com.oserion.framework.api.Api418Facade;
import com.oserion.framework.web.util.AuthenticationAccess;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Arsaww on 12/5/2015.
 * Main controller for all expected HTML response
 */
@Controller
@RequestMapping("/^(?!admin)(?!oserion)")
public class HtmlOserion extends ControllerOserion {

    private static final Logger LOG = Logger.getLogger(HtmlOserion.class.getName());

    //TODO: direct mapping to temp.html for the moment
    @ResponseBody
    @RequestMapping( method = RequestMethod.GET)
    public String getHTMLPage(HttpServletRequest req,HttpServletResponse resp) {
        AuthenticationAccess.checkAccess(req, resp);


        LOG.log(Level.INFO, "getHTMLPage");

        Api418Facade facade = getSpringContext(req).getBean(Api418Facade.class);

        facade.addPageUrl("premierTemplate","/toto");

        return facade.getHTMLPage("premierTemplate");

        //TODO récupérer le vrai HTML ici à partir de facade

        //return "temp";
    }
}

