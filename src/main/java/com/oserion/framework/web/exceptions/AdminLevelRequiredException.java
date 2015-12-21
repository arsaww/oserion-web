package com.oserion.framework.web.exceptions;

/**
 * Created by Arsaww on 12/17/2015.
 */
public class AdminLevelRequiredException extends Exception {

    public AdminLevelRequiredException(){super();}
    public AdminLevelRequiredException(String message){super(message);}
    public AdminLevelRequiredException(Throwable cause){super(cause);}
    public AdminLevelRequiredException(String message, Throwable cause){super(message, cause);}
    public AdminLevelRequiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {super(message, cause, enableSuppression, writableStackTrace);}

}
