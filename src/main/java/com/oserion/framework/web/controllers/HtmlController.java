package com.oserion.framework.web.controllers;

import com.oserion.framework.api.Api418Facade;
import com.oserion.framework.api.util.CentraleBean;
import com.oserion.framework.web.util.AuthenticationAccess;
import com.oserion.framework.web.util.LoadConfigFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

//    @Autowired
//    private ApplicationContext appContext;
    
    //TODO: direct mapping to temp.html for the moment
    //@ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public String getHTMLPage(HttpServletRequest req,HttpServletResponse resp) {
        AuthenticationAccess.checkAccess(req, resp);
        LOG.log(Level.INFO, "getHTMLPage");

        
        String variableJavaVM = System.getProperty("oserionWeb.config.file.path");
        LOG.log(Level.INFO , variableJavaVM);
        
        
//        LoadConfigFile lcf = new LoadConfigFile("oserionWeb.config.file.path");
//        String bddUrlBaseMongo = lcf.xmlConfiguration.getString("mongoDB.bddUrlBaseMongo");
//        String bddPortMongo = lcf.xmlConfiguration.getString("mongoDB.bddPortMongo");
//        String bddName = lcf.xmlConfiguration.getString("mongoDB.bddName");
//        LOG.log(Level.INFO, bddUrlBaseMongo);
//        LOG.log(Level.INFO, bddPortMongo);
//        LOG.log(Level.INFO, bddName);
        
        
//		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CentraleBean.class);
//		Api418Facade a418f = context.getBean(Api418Facade.class);
		
        //return ApiFace.getBeans(req);
        //return "HTML PAGE";
        return "temp";
    }
}

