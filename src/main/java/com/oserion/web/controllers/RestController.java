package com.oserion.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Arsaww on 12/5/2015.
 * Main controller for all expected Rest Services response
 */
@Controller
@RequestMapping("/oserion/services")
public class RestController {

    @ResponseBody
    @RequestMapping(value = "/content/{contentId}", method = RequestMethod.GET)
    public String setContent(@PathVariable String contentId) {
        return "REST PAGE "+ contentId;
    }
}
