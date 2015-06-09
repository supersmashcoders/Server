package com.supersmashcoders.services.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.supersmashcoders.entities.EventEntity;
import com.supersmashcoders.entities.UserEntity;

/** An endpoint class we are exposing */
public class EventService {

	/** Ping the service */
    @ApiMethod(name = "ping", httpMethod = HttpMethod.GET)
    public UserEntity ping() {
    	UserEntity user = new UserEntity();
    	user.setUsername("calicaliche");
    	user.setPassword("mypass");
    	return user;
    }
    
    @ApiMethod(name = "create", httpMethod = HttpMethod.POST)
    public EventEntity create(EventEntity event) {
    	return event;
    }
}
