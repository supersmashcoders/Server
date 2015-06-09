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
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.supersmashcoders.entities.EventEntity;
import com.supersmashcoders.entities.UserEntity;
import com.supersmashcoders.entities.images.ImageEntity;
import com.supersmashcoders.pojos.ResultMessage;
import com.supersmashcoders.resources.URLResource;
import com.supersmashcoders.services.images.ImageHandlingService;

/** An endpoint class we are exposing */
@Api(name = "events", version = "v1", namespace = @ApiNamespace(ownerDomain = "sschackathon.appspot.com", 
																ownerName = "sschackathon", 
																packagePath = ""))
public class EventService {

    private ImageHandlingService imageService = new ImageHandlingService();

    @ApiMethod(name = "create", httpMethod = HttpMethod.POST)
    public ResultMessage create (EventEntity event) {
    	ObjectifyService.ofy().save().entity(event).now();
    	return new ResultMessage("Success", Long.toString(event.getId()));
    }
    
    @ApiMethod(name = "getEvent", httpMethod = HttpMethod.GET, path = "{id}")
    public EventEntity getEvent (@Named("id") String id) throws EntityNotFoundException {
    	Key key = KeyFactory.createKey("EventEntity", Long.decode(id));
    	Entity entity = DatastoreServiceFactory.getDatastoreService().get(key);
    	EventEntity event = ObjectifyService.ofy().toPojo(entity);
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
