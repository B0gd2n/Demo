package com.example.demo.models.dto;

import java.io.Serializable;

/**
 * Created by tito on 12/18/2017.
 */
public class AuthRequest  extends BaseModel implements Serializable {

    private String username;
    private String password;

    public AuthRequest() {
        super();
    }

    public AuthRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

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
