package com.oserion.framework.web.controllers;

import com.oserion.framework.api.Api418Facade;
import com.oserion.framework.web.exceptions.AdminLevelRequiredException;
import com.oserion.framework.web.exceptions.InternalErrorException;
import com.oserion.framework.web.beans.json.ResponseMessage;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/oserion/templates")
public class TemplatesController extends ControllerOserion {

    private static final Logger LOG = Logger.getLogger(TemplatesController.class.getName());

    @RequestMapping(value="/templates", method = RequestMethod.POST)
    public Object addTemplate(@RequestParam("file") MultipartFile file,
                         MultipartHttpServletRequest req,
                         HttpServletResponse resp) throws AdminLevelRequiredException, InternalErrorException {
        checkAdminAccess(req, resp);
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes());
            String strTemplate = IOUtils.toString(stream, "UTF-8");
            new Api418Facade().uploadTemplateFromHtml(strTemplate,
                    FilenameUtils.removeExtension(file.getOriginalFilename()));
            LOG.log(Level.INFO, file.getOriginalFilename() + "was successfully inserted as a template");
            return new ResponseMessage("OK",file.getOriginalFilename() + "was successfully inserted as a template");

        }catch (Exception e){
            throw new InternalErrorException(e);
        }
    }

}