package com.supersmashcoders.services.api;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.ServletException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.datastore.GeoPt;
import com.supersmashcoders.entities.EventEntity;
import com.supersmashcoders.entities.UserEntity;
import com.supersmashcoders.entities.images.ImageEntity;
import com.supersmashcoders.resources.URLResource;
import com.supersmashcoders.services.images.ImageHandlingService;

/** An endpoint class we are exposing */
@Api(name = "events", version = "v1", namespace = @ApiNamespace(ownerDomain = "sschackathon.appspot.com", ownerName = "sschackathon", packagePath = ""))
public class EventService {

    private ImageHandlingService imageService = new ImageHandlingService();

    /** Ping the service */
    @ApiMethod(name = "ping", httpMethod = HttpMethod.GET)
    public UserEntity ping() {
        UserEntity user = new UserEntity();
        user.setUsername("calicaliche");
        user.setPassword("mypass");
        return user;
    }

    @ApiMethod(name = "createEvent", httpMethod = HttpMethod.POST)
    public EventEntity createEvent(EventEntity event) {
        return event;
    }

    @ApiMethod(name = "getEvent", httpMethod = HttpMethod.GET)
    public EventEntity getEvent(@Named("id") String id) throws JsonProcessingException {
        EventEntity event = new EventEntity();
        // Primitive Values
        event.setId(983219L);
        event.setDescription("Simple description for testing");
        event.setName("Testing event");
        // Dates
        event.setEndDate(new Date());
        event.setStartDate(new Date());
        // Photos
        ImageEntity[] images = { new ImageEntity() };
        event.setPhotos(Arrays.asList(images));
        // Tags
        String[] tags = { "running", "extreme" };
        event.setTags(Arrays.asList(tags));
        // Attendants
        UserEntity[] attendants = { new UserEntity() };
        event.setAttendants(Arrays.asList(attendants));
        // GeoPt
        GeoPt geoPt = new GeoPt(50, 50);
        event.setGeoPoint(geoPt);

        return event;
    }

    @ApiMethod(name = "getUrl", path = "images/url", httpMethod = HttpMethod.GET)
    public URLResource getURL(@Named("id") String id) {
        return imageService.getImageURL(id);
    }

    @ApiMethod(name = "getImage", path = "images", httpMethod = HttpMethod.GET)
    public void getImage() {
        try {
            imageService.getImage(null, null);
        } catch (ServletException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @ApiMethod(name = "storeImage", path = "images", httpMethod = HttpMethod.POST)
    public void createImage() {
        try {
            imageService.postImage(null, null);
        } catch (ServletException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
