package com.supersmashcoders.services.users;

import java.util.List;

import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;
import com.googlecode.objectify.ObjectifyService;
import com.supersmashcoders.entities.UserEntity;
import com.supersmashcoders.pojos.ResultMessage;

public class UsersHandlingService {
	
    public ResultMessage createUser (@Named("username") String username, @Named("password") String password, @Named("bio") String bio, @Named("passions") List<String> passions){
		UserEntity user;
		
        user = new UserEntity(username, password, bio, passions);
        
        ObjectifyService.ofy().save().entity(user).now();
        return new ResultMessage("Success", Long.toString(user.getId()));
    }
    
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
