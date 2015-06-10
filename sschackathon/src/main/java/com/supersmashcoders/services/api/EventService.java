package com.supersmashcoders.services.api;

import java.util.ArrayList;
import java.util.List;

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
import com.supersmashcoders.resources.URLResource;
import com.supersmashcoders.services.images.ImageHandlingService;

/** An endpoint class we are exposing */
@Api(name = "backtoback", version = "v1",
	title= "Back To Back API", namespace = @ApiNamespace(	ownerDomain = "sschackathon.appspot.com", 
															ownerName = "sschackathon", 
															packagePath = ""))
public class EventService {

    private ImageHandlingService imageService = new ImageHandlingService();

    @ApiMethod(name = "createEvent", httpMethod = HttpMethod.POST, path="event")
    public EventEntity createEvent (EventEntity event) {
    	ObjectifyService.ofy().save().entity(event).now();
    	return event;
    }
    
    @ApiMethod(name = "getEvent", httpMethod = HttpMethod.GET, path = "event/{id}")
    public EventEntity getEvent (@Named("id") String id) throws NotFoundException {
		try {
			Key key = KeyFactory.createKey("EventEntity", Long.decode(id));
	    	Entity entity = DatastoreServiceFactory.getDatastoreService().get(key);
			EventEntity event = ObjectifyService.ofy().toPojo(entity);
	    	return event;
		} catch (EntityNotFoundException e) {
			throw new NotFoundException("No event exists with id " + id);
		}
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
    public URLResource getURL(@Named("eventId") String eventId) {
        return imageService.getImageURL(eventId);
    }

    @ApiMethod(name = "getImages", path = "images/", httpMethod = HttpMethod.GET)
    public void getImages(@Named("eventId") String eventId) {
        imageService.getImagesFromEvent(eventId);
    }
}
