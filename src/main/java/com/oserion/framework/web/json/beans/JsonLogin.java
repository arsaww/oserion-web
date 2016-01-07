package com.oserion.framework.web.json.beans;

/**
 * Created by Arsaww on 12/6/2015.
 */
public class JsonLogin {

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String login;
    private String password;

}
