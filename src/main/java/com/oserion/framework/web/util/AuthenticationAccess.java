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

    public static boolean isAdmin(HttpServletRequest req, HttpServletResponse resp){
        Object access = req.getSession().getAttribute(AUTHENTICATION_ACCESS);
        if (isAuthenticate(req, resp)) {
            switch ((AuthenticationAccess) access) {
                case SUPERADMIN:
                case ADMIN:
                    addCookieAccess((AuthenticationAccess) access, resp);
                    return true;
            }
        }else {
            removeookieAccess(resp);
        }
        return false;
    }

    public static boolean isAuthenticate(HttpServletRequest req, HttpServletResponse resp){
        Object access = req.getSession().getAttribute(AUTHENTICATION_ACCESS);
        if (access != null) {
            return true;
        }
        removeAccess(req, resp);
        return false;
    }

    public static void setAccess(AuthenticationAccess access, HttpServletRequest req, HttpServletResponse resp){
        req.getSession().setAttribute(AUTHENTICATION_ACCESS, access);
        addCookieAccess(access,resp);
    }

    public static void addCookieAccess(AuthenticationAccess access, HttpServletResponse resp){
        Cookie authCookie = new Cookie(AUTHENTICATION_ACCESS, access.toString());
        authCookie.setMaxAge(3600);
        authCookie.setPath("/");
        resp.addCookie(authCookie);
    }

    public static void checkAccess(HttpServletRequest req, HttpServletResponse resp){
        if(!isAuthenticate(req, resp)) setAccess(NO_RIGHTS,req,resp);
    }

    public static void removeAccess(HttpServletRequest req, HttpServletResponse resp){
        req.getSession().removeAttribute(AUTHENTICATION_ACCESS);
        removeookieAccess(resp);
    }

    public static void removeookieAccess(HttpServletResponse resp) {
        Cookie authCookie = new Cookie(AUTHENTICATION_ACCESS, "");
        authCookie.setPath("/");
        authCookie.setMaxAge(0);
        resp.addCookie(authCookie);
    }
}
