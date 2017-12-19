package com.example.demo.controllers;

import com.example.demo.models.dto.BaseModel;
import com.example.demo.models.dto.MessageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.concurrent.Callable;

/**
 * Created by tito on 12/18/2017.
 */
public class BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    @GET
    @Path("/ping")
    @Produces("application/json")
    public Response ping() {
        return perform(() -> new MessageResponse("pong"));
    }

    protected <T extends BaseModel> Response perform(Callable<T> callable) {
        try {
            return Response.ok(callable.call()).build();
        } catch (Exception ex) {
            LOGGER.error("Exception occurred,", ex);
            return Response.status(Response.Status.BAD_REQUEST).entity(new MessageResponse(ex.getMessage())).build();
        }
    }
}
