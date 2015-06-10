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
import com.supersmashcoders.entities.UserEntity;
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
    public URLResource getURL(@Named("eventId") String eventId) {
        return imageService.getImageURL(eventId);
    }

    @ApiMethod(name = "getImages", path = "images/", httpMethod = HttpMethod.GET)
    public void getImages(@Named("eventId") String eventId) {
        imageService.getImagesFromEvent(eventId);
    }
    
    @ApiMethod(name = "createUser", path = "users/createUser", httpMethod = HttpMethod.POST)
    public ResultMessage createUser (@Named("username") String username, @Named("password") String password, @Named("bio") String bio, @Named("passions") List<String> passions){
		UserEntity user;
		
        user = new UserEntity(username, password, bio, passions);
        
        ObjectifyService.ofy().save().entity(user).now();
        return new ResultMessage("Success", Long.toString(user.getId()));
    }
    
    @ApiMethod(name = "validateUser", path = "users/validateUser", httpMethod = HttpMethod.POST)
    public ResultMessage validateUser (@Named("username") String username, @Named("password") String password) throws NotFoundException{
    	Query query = new Query("UserEntity").addFilter("username", Query.FilterOperator.EQUAL, username)
    			.addFilter("password", Query.FilterOperator.EQUAL, password);
    	List<Entity> entities = DatastoreServiceFactory.getDatastoreService()
    											.prepare(query)
    											.asList(FetchOptions.Builder.withDefaults());
    	System.out.println(entities.size());
    	
    	UserEntity user = null;
    	if (entities.size() == 1){
    		user = (UserEntity)ObjectifyService.ofy().toPojo(entities.get(0));
    	}
    	else {
    		throw new NotFoundException("No user/password combination exists with username " + username);
    	}
    	return new ResultMessage("Success", Long.toString(user.getId()));
    }
    
    @ApiMethod(name = "getUser", path = "users/getUser", httpMethod = HttpMethod.POST)
    public UserEntity getUser (@Named("username") String username) throws NotFoundException{
    	Query query = new Query("UserEntity").addFilter("username", Query.FilterOperator.EQUAL, username);
    	List<Entity> entities = DatastoreServiceFactory.getDatastoreService()
    											.prepare(query)
    											.asList(FetchOptions.Builder.withDefaults());
    	System.out.println(entities.size());
    	UserEntity user;
    	if (entities.size() == 1){
    		user = (UserEntity)ObjectifyService.ofy().toPojo(entities.get(0));
    		user.setPassword("");
    	}
    	else {
    		throw new NotFoundException("No user exists with username " + username);
    	}
    	return user;
    	
    }
    
}
