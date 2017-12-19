package com.example.demo.config;

import com.example.demo.controllers.AuthController;
import com.example.demo.controllers.ProfileController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        register(AuthController.class);
        register(ProfileController.class);
    }
}

