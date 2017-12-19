package com.example.demo.models.dto;

import java.io.Serializable;

/**
 * Created by tito on 12/18/2017.
 */
public class AuthResponse extends BaseModel implements Serializable {

    private String token;

    public AuthResponse() {
        super();
    }

    public AuthResponse(String token) {
        this.setToken(token);
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
