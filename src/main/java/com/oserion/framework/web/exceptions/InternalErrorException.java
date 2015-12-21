package com.oserion.framework.web.exceptions;

/**
 * Created by Arsaww on 12/17/2015.
 */
public class InternalErrorException extends Exception {

    public InternalErrorException(){super();}
    public InternalErrorException(String message){super(message);}
    public InternalErrorException(Throwable cause){super(cause);}
    public InternalErrorException(String message, Throwable cause){super(message, cause);}
    public InternalErrorException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace)
    {super(message, cause, enableSuppression, writableStackTrace);}

}
