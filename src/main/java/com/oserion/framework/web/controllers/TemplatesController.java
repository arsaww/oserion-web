package com.oserion.framework.web.controllers;

import com.oserion.framework.web.json.beans.JsonTemplatePage;
import com.oserion.framework.web.exceptions.AdminLevelRequiredException;
import com.oserion.framework.web.exceptions.InternalErrorException;
import com.oserion.framework.web.json.beans.JsonResponseMessage;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/oserion/templates")
public class TemplatesController extends OserionController {

    private static final Logger LOG = Logger.getLogger(TemplatesController.class.getName());

    @RequestMapping(value="/upload", method = RequestMethod.POST)
    public Object addTemplate(@RequestParam("file") MultipartFile file,
                         MultipartHttpServletRequest req,
                         HttpServletResponse resp) throws AdminLevelRequiredException, InternalErrorException {
        checkAdminAccess(req, resp);
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes());
             String strTemplate = IOUtils.toString(stream, "UTF-8");

            getApiFacade(req).createTemplate(
                    FilenameUtils.removeExtension(file.getOriginalFilename()),
                    strTemplate
            );

            LOG.log(Level.INFO, file.getOriginalFilename() + "was successfully inserted as a template");
            return new JsonResponseMessage("OK",file.getOriginalFilename() + "was successfully inserted as a template");

        }catch (Exception e){
            throw new InternalErrorException(e);
        }
    }

    @RequestMapping(value="/addPage", method = RequestMethod.POST)
    public Object addPage(@RequestBody JsonTemplatePage tp,
                              HttpServletRequest req,
                              HttpServletResponse resp) throws AdminLevelRequiredException, InternalErrorException {
        checkAdminAccess(req, resp);
        try {
            //TODO JFE
            getApiFacade(req).addPageUrl(tp.getTemplate(),tp.getValue());
            return new JsonResponseMessage("OK","the page '" +tp.getValue()+"' was successfully created");
        }catch (Exception e){
            throw new InternalErrorException(e);
        }
    }

    @RequestMapping(value="", method = RequestMethod.GET)
    public Object getTemplates(HttpServletRequest req,
                              HttpServletResponse resp) throws AdminLevelRequiredException, InternalErrorException {
        checkAdminAccess(req, resp);
        try {

            return  getApiFacade(req).getTemplates();

        }catch (Exception e){
            throw new InternalErrorException(e);
        }
    }

}