package com.example.demo;

import com.example.demo.models.dto.AuthRequest;
import com.example.demo.models.dto.MessageResponse;
import org.hsqldb.lib.HashMap;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import javax.ws.rs.core.Response;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void ping() {
        ResponseEntity<MessageResponse> entity = this.restTemplate.getForEntity("/auth/ping", MessageResponse.class);

        assertThat(entity.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
        assertThat(entity.getBody().getMessage()).isEqualTo("pong");
    }

    @Test
    public void unAuth() {
        ResponseEntity entity = this.restTemplate.getForEntity("/profile/info/zuck", Object.class);

        assertThat(entity.getStatusCode().value()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
    }

    @Test
    public void badLogin() {
        AuthRequest req = new AuthRequest();
        req.setUsername("user");
        req.setPassword("user1");

        ResponseEntity<MessageResponse> entity = this.restTemplate.postForEntity("/auth/login", req, MessageResponse.class);

        assertThat(entity.getStatusCode().value()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(entity.getBody().getMessage()).isEqualTo("Bad credentials");
    }

    @Test
    public void login() {
        AuthRequest req = new AuthRequest();
        req.setUsername("user");
        req.setPassword("user");

        ResponseEntity<MessageResponse> entity = this.restTemplate.postForEntity("/auth/login", req, MessageResponse.class);

        assertThat(entity.getStatusCode().value()).isEqualTo(HttpStatus.OK.value());
    }

}
