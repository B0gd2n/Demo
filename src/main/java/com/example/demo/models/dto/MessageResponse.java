package com.example.demo.models.dto;

/**
 * Created by tito on 12/18/2017.
 */
public class MessageResponse extends BaseModel {
    private String message;

    public MessageResponse() {
    }

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
