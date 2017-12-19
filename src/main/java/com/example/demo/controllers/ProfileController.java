package com.example.demo.controllers;

import com.example.demo.models.dto.ProfileInfo;
import com.example.demo.services.IProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.demo.annotations.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Component
@Path("/profile")
@Api(value = "profile resource", produces = "application/json")
public class ProfileController extends BaseController {
    @Autowired
    private IProfileService profileService;

    @GET
    @Path("/info/{id}")
    @Produces("application/json")
    @ApiOperation(value = "Gets external profile info from.", response = ProfileInfo.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "info found")
    })

    public Response info(@PathParam("id") String id) {
        return perform(() -> profileService.getInfo(id));
    }
}
