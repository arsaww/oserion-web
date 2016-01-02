package com.oserion.framework.web.controllers;

import com.oserion.framework.api.business.ITemplate;
import com.oserion.framework.api.business.impl.jsoup.JsoupTemplate;
import com.oserion.framework.web.beans.json.TemplatePage;
import com.oserion.framework.web.exceptions.AdminLevelRequiredException;
import com.oserion.framework.web.exceptions.InternalErrorException;
import com.oserion.framework.web.beans.json.ResponseMessage;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/oserion/templates")
public class TemplatesController extends ControllerOserion {

    private static final Logger LOG = Logger.getLogger(TemplatesController.class.getName());

    @RequestMapping(value="/upload", method = RequestMethod.POST)
    public Object addTemplate(@RequestParam("file") MultipartFile file,
                         MultipartHttpServletRequest req,
                         HttpServletResponse resp) throws AdminLevelRequiredException, InternalErrorException {
        checkAdminAccess(req, resp);
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes());
            String strTemplate = IOUtils.toString(stream, "UTF-8");

            getApiFacade(req).insertTemplate(
                    FilenameUtils.removeExtension(file.getOriginalFilename()),
                    strTemplate
            );

            LOG.log(Level.INFO, file.getOriginalFilename() + "was successfully inserted as a template");
            return new ResponseMessage("OK",file.getOriginalFilename() + "was successfully inserted as a template");

        }catch (Exception e){
            throw new InternalErrorException(e);
        }
    }

    @RequestMapping(value="/addPage", method = RequestMethod.POST)
    public Object addPage(@RequestBody TemplatePage tp,
                              HttpServletRequest req,
                              HttpServletResponse resp) throws AdminLevelRequiredException, InternalErrorException {
        checkAdminAccess(req, resp);
        try {
            getApiFacade(req).addPageUrl(tp.getTemplate(),tp.getValue());
            return new ResponseMessage("OK","the page '" +tp.getValue()+"' was successfully created");
        }catch (Exception e){
            throw new InternalErrorException(e);
        }
    }

    @RequestMapping(value="", method = RequestMethod.GET)
    public Object getTemplates(HttpServletRequest req,
                              HttpServletResponse resp) throws AdminLevelRequiredException, InternalErrorException {
        checkAdminAccess(req, resp);
        try {

            //TODO JFE
            return getApiFacade(req).selectTemplates();

        }catch (Exception e){
            throw new InternalErrorException(e);
        }
    }

}