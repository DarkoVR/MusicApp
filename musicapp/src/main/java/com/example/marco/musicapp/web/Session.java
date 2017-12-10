package com.example.marco.musicapp.web;

/**
 * Created by marco on 9/12/17.
 */

public class Session {
    String user,password,token;

    public Session() {
    }

    public Session(String user, String password, String token) {
        this.user = user;
        this.password = password;
        this.token = token;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
