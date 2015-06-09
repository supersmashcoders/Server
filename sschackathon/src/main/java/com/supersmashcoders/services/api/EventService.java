package com.supersmashcoders.services.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.googlecode.objectify.ObjectifyService;
import com.supersmashcoders.entities.EventEntity;
import com.supersmashcoders.pojos.ResultMessage;
import com.supersmashcoders.resources.URLResource;
import com.supersmashcoders.services.images.ImageHandlingService;

/** An endpoint class we are exposing */
@Api(name = "backtoback", version = "v1",
	title= "Back To Back API", namespace = @ApiNamespace(	ownerDomain = "sschackathon.appspot.com", 
															ownerName = "sschackathon", 
															packagePath = ""))
public class EventService {

    private ImageHandlingService imageService = new ImageHandlingService();

    @ApiMethod(name = "create", httpMethod = HttpMethod.POST, path="event")
    public ResultMessage create (EventEntity event) {
    	ObjectifyService.ofy().save().entity(event).now();
    	return new ResultMessage("Success", Long.toString(event.getId()));
    }
    
    @ApiMethod(name = "getEvent", httpMethod = HttpMethod.GET, path = "event/{id}")
    public EventEntity getEvent (@Named("id") String id) throws NotFoundException {
    	Key key = KeyFactory.createKey("EventEntity", Long.decode(id));
    	Entity entity = null;
		try {
			entity = DatastoreServiceFactory.getDatastoreService().get(key);
		} catch (EntityNotFoundException e) {
			throw new NotFoundException("No event exists with id " + id);
		}
    	EventEntity event = ObjectifyService.ofy().toPojo(entity);
    	return event;
    }
    
    @ApiMethod(name = "getEvents", httpMethod = HttpMethod.GET, path="events")
    public List<EventEntity> getEvents () throws NotFoundException {
    	Query query = new Query("EventEntity").addSort("startDate", Query.SortDirection.DESCENDING);
    	List<Entity> entities = DatastoreServiceFactory.getDatastoreService()
    											.prepare(query)
    											.asList(FetchOptions.Builder.withDefaults());
    	System.out.println(entities.size());
    	List<EventEntity> events = new ArrayList<EventEntity>();
    	for(Entity entity: entities){
    		events.add((EventEntity)ObjectifyService.ofy().toPojo(entity));
    	}
    	return events;
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
