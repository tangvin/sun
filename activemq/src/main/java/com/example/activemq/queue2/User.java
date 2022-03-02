package com.example.activemq.queue2;

import java.io.Serializable;

public class User implements Serializable {


    private static final long serialVersionUID = 3877757044166259602L;

//    private static final long serialVersionUID = 2037579749946183371L;


    private String username;

    private String password;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
