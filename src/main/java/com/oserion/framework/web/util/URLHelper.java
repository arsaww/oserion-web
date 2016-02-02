package com.oserion.framework.web.util;


import com.sun.org.apache.xerces.internal.util.URI;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Arsaww on 1/29/2016.
 */
public class URLHelper {

    public static String replaceLast(String string, String substring, String replacement)
    {
        int index = string.lastIndexOf(substring);
        if (index == -1)
            return string;
        return string.substring(0, index) + replacement
                + string.substring(index+substring.length());
    }

    public static String templateURIToHtml(String uri){
        return replaceLast(uri,".template",".html");
    }

    public static String htmlURIToTemplate(String uri){
        return replaceLast(uri,".html",".template");
    }

    public static String getReferrerURI(HttpServletRequest req) throws URI.MalformedURIException {
        return new URI(req.getHeader("referer")).getPath();
    }
}
