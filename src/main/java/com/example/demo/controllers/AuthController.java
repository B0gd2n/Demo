package com.example.demo.controllers;

import com.example.demo.models.dto.AuthRequest;
import com.example.demo.models.dto.AuthResponse;
import com.example.demo.models.dto.ProfileInfo;
import com.example.demo.security.TokenUtils;
import com.example.demo.services.impl.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;

import javax.ws.rs.core.Response;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Component
@Path("/auth")
@Api(value = "auth resource", produces = "application/json")
public class AuthController extends BaseController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserService userService;
    @POST
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    @Path("/login")
    @ApiOperation(value = "Gets a token for user cred.", response = String.class)
    public Response authenticationRequest(AuthRequest authRequest) {
        return perform(() -> {
            Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername(), authRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Reload password post-authentication so we can generate token
            String token = this.tokenUtils.generateToken(this.userService.loadUserByUsername(authRequest.getUsername()));
            return new AuthResponse(token);
        });
    }
}
