package com.oserion.framework.web.controllers;

import com.oserion.framework.web.exceptions.AdminLevelRequiredException;
import com.oserion.framework.web.exceptions.InternalErrorException;
import com.oserion.framework.web.exceptions.NotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebMvc
@ControllerAdvice
public class OserionExceptionHandler {

    private static final String ADMIN_LOGIN_PAGE = "/admin";

    @ExceptionHandler(InternalErrorException.class)
    public @ResponseBody String internalError(HttpServletRequest req, HttpServletResponse resp) {
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return "Internal Server Error";
    }

    @ExceptionHandler(NotFoundException.class)
    public @ResponseBody String notFound(HttpServletRequest req, HttpServletResponse resp) {
        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return "Page not found";
    }

    @ExceptionHandler(AdminLevelRequiredException.class)
    public @ResponseBody String unauthorized(HttpServletRequest req, HttpServletResponse resp) {
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return "<html><body>Request unauthorized <script type='text/javascript'>document.location.href='/admin';</script></body></html>";
    }

}
