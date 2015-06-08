package com.supersmashcoders.services.api;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.ServletException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.appengine.api.datastore.GeoPt;
import com.supersmashcoders.entities.EventEntity;
import com.supersmashcoders.entities.UserEntity;
import com.supersmashcoders.entities.images.ImageEntity;
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
        	System.out.println("Hola Mundo");

            //imageService.postImage(null, null);
    }
    
    @Get("json")
    @Path("{id}/")
    public String getEvent(@QueryParam("id") String id) throws JsonProcessingException {
    	EventEntity event = new EventEntity();
    	// Primitive Values
    	event.setId(983219L);
    	event.setDescription("Simple description for testing");
    	event.setName("Testing event");
    	// Dates
    	event.setEndDate(new Date());
    	event.setStartDate(new Date());
    	// Photos
    	ImageEntity[] images = {new ImageEntity()};
    	event.setPhotos(Arrays.asList(images));
    	// Tags
    	String[] tags = {"running","extreme"};
    	event.setTags(Arrays.asList(tags));
    	// Attendants
    	UserEntity[] attendants = {new UserEntity()};
    	event.setAttendants(Arrays.asList(attendants));
    	// GeoPt
    	GeoPt geoPt = new GeoPt(50, 50);
    	event.setGeoPoint(geoPt);
    	// Jackson
    	ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(event);
    }
    /*
    @Post
    @Path("create/")
    @Produces("application/json")
    public String createEvent() throws IOException {	
    	/*
    	System.out.println(entity.getText());
    	ObjectMapper mapper = new ObjectMapper();
    	EventEntity event = mapper.readValue(entity.getText(), EventEntity.class);
        return mapper.writeValueAsString(event);
    } */
}