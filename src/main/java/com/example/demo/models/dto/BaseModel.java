package com.example.demo.models.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class BaseModel {
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
