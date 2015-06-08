package com.supersmashcoders.services.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.supersmashcoders.services.images.ImageHandlingService;

@Path("events/")
public class EventsService {

    private ImageHandlingService imageService = new ImageHandlingService();

    @GET
    @Produces("application/json")
    @Path("{id}/photos/url")
    public String getURL(@QueryParam("id") String id) {
        return imageService.getImageURL("3");
    }

    @GET
    @Produces("application/json")
    @Path("{id}/photos/")
    public void getImage(@QueryParam("id") String id) {
        try {
            imageService.getImage(null, null);
        } catch (ServletException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @POST
    @Consumes("application/json")
    @Path("{id}/photos/")
    public void createImage(@QueryParam("id") String id) {
        try {
            imageService.postImage(null, null);
        } catch (ServletException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}