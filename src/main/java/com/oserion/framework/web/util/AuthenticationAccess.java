package com.oserion.framework.web.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Arsaww on 12/5/2015.
 */
public enum AuthenticationAccess {
    NO_RIGHTS, USER, ADMIN, SUPERADMIN;

    private static final String AUTHENTICATION_ACCESS = "osr-access";

    public static boolean isAdmin(HttpServletRequest req){
        Object access = req.getSession().getAttribute(AUTHENTICATION_ACCESS);
        if (access != null) {
            switch ((AuthenticationAccess) access) {
                case SUPERADMIN:
                case ADMIN:
                    return true;
            }
        }
        return false;
    }

    public static boolean isAuthenticate(HttpServletRequest req){
        Object access = req.getSession().getAttribute(AUTHENTICATION_ACCESS);
        if (access != null) {
            switch ((AuthenticationAccess) access) {
                case SUPERADMIN:
                case ADMIN:
                case USER:
                    return true;
            }
        }
        return false;
    }

    public static void setAccess(AuthenticationAccess access, HttpServletRequest req, HttpServletResponse resp){
        req.getSession().setAttribute(AUTHENTICATION_ACCESS, access);
        Cookie authCookie = new Cookie(AUTHENTICATION_ACCESS, access.toString());
        authCookie.setPath("/");
        resp.addCookie(authCookie);
    }

    public static void checkAccess(HttpServletRequest req, HttpServletResponse resp){
        if(!isAuthenticate(req)) setAccess(NO_RIGHTS,req,resp);
    }
}
