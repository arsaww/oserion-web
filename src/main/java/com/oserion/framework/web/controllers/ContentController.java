package com.oserion.framework.web.controllers;

import com.oserion.framework.api.business.beans.ContentElement;
import com.oserion.framework.web.json.beans.JsonResponseMessage;
import com.oserion.framework.web.exceptions.AdminLevelRequiredException;
import com.oserion.framework.web.exceptions.InternalErrorException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.logging.Logger;

/**
 * Created by Arsaww on 12/5/2015.
 * Main controller for all expected Rest Services response
 */
@RestController
@RequestMapping("/oserion/services/content")
public class ContentController extends OserionController {

    private static final Logger LOG = Logger.getLogger(ContentController.class.getName());

    @RequestMapping(value = "/{type}/{contentId}", method = RequestMethod.GET)
    public Object setContent(@PathVariable String contentId, @PathVariable String type,
                             HttpServletRequest req, HttpServletResponse resp) throws AdminLevelRequiredException {
        checkAdminAccess(req, resp);
        return new JsonResponseMessage("OK", contentId);
    }

    @RequestMapping(value = "/{type}/{contentId}", method = RequestMethod.POST)
    public Object updateContent(@PathVariable String contentId, @PathVariable String type, @RequestBody ContentElement c,
                                HttpServletRequest req, HttpServletResponse resp)
            throws AdminLevelRequiredException, InternalErrorException {
        checkAdminAccess(req, resp);
        try {
            getApiFacade(req).setContentElementValue(c);
            return new JsonResponseMessage("OK", contentId + "updated");
        } catch (Exception e) {
            throw new InternalErrorException("Fail to update the content "+ contentId);
        }
    }
}
