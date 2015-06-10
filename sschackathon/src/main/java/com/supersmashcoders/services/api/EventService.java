package com.supersmashcoders.services.api;

import java.util.List;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.response.ConflictException;
import com.google.api.server.spi.response.NotFoundException;
import com.supersmashcoders.entities.EventEntity;
import com.supersmashcoders.entities.UserEntity;
import com.supersmashcoders.entities.images.ImageEntity;
import com.supersmashcoders.pojos.ResultMessage;
import com.supersmashcoders.pojos.URLResource;
import com.supersmashcoders.services.events.EventsHandlingService;
import com.supersmashcoders.services.images.ImageHandlingService;
import com.supersmashcoders.services.users.UsersHandlingService;

/** An endpoint class we are exposing */
@Api(name = "backtoback", version = "v1", title = "Back To Back API", namespace = @ApiNamespace(ownerDomain = "sschackathon.appspot.com", ownerName = "sschackathon", packagePath = ""))
public class EventService {

    private ImageHandlingService imageService = new ImageHandlingService();
    private EventsHandlingService eventService = new EventsHandlingService();
    private UsersHandlingService userService = new UsersHandlingService();

    @ApiMethod(name = "create", httpMethod = HttpMethod.POST, path = "event")
    public ResultMessage create(EventEntity event) {
        Long id = eventService.createEvent(event);
        return new ResultMessage("Success", Long.toString(id));
    }

    @ApiMethod(name = "getEvent", httpMethod = HttpMethod.GET, path = "event/{id}")
    public EventEntity getEvent(@Named("id") String id) throws NotFoundException {
        return eventService.getEvent(id);
    }

    @ApiMethod(name = "getEvents", httpMethod = HttpMethod.GET, path = "events")
    public List<EventEntity> getEvents() throws NotFoundException {
        return eventService.getEvents();
    }

    @ApiMethod(name = "getUrl", path = "images/url", httpMethod = HttpMethod.GET)
    public URLResource getURL(@Named("eventId") String eventId) {
        return imageService.getImageURL(eventId);
    }

    @ApiMethod(name = "getImages", path = "images/", httpMethod = HttpMethod.GET)
    public List<ImageEntity> getImages(@Named("eventId") String eventId) {
        return imageService.getImagesFromEvent(eventId);
    }

    @ApiMethod(name = "attendEvent", path = "event/attend", httpMethod = HttpMethod.GET)
    public void attendEvent(@Named("eventId") String eventId, @Named("userId") String userId) throws NotFoundException {
        eventService.attendEvent(eventId, null);
    }
    
    @ApiMethod(name = "createUser", path = "users/createUser", httpMethod = HttpMethod.POST)
    public ResultMessage createUser (@Named("username") String username, @Named("password") String password, @Named("bio") String bio, @Named("passions") List<String> passions) throws ConflictException{
		return userService.createUser(username, password, bio, passions);
    }
    
    @ApiMethod(name = "validateUser", path = "users/validateUser", httpMethod = HttpMethod.POST)
    public ResultMessage validateUser (@Named("username") String username, @Named("password") String password) throws NotFoundException{
    	return userService.validateUser(username, password);
    }
    
    @ApiMethod(name = "getUser", path = "users/getUser", httpMethod = HttpMethod.POST)
    public UserEntity getUser (@Named("username") String username) throws NotFoundException{
    	return userService.getUser(username);
    }

    @ApiMethod(name = "removeAttendance", path = "event/unAttend", httpMethod = HttpMethod.GET)
    public void removeAttendance(@Named("eventId") String eventId, @Named("userId") String userId)
            throws NotFoundException {
        eventService.removeAttendance(eventId, null);
    }

}
