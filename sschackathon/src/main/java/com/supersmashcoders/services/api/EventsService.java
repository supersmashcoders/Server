package com.supersmashcoders.services.api;



import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.supersmashcoders.entities.EventEntity;
import com.supersmashcoders.pojos.ResultMessage;
import com.supersmashcoders.services.images.ImageHandlingService;

@Api(name = "events",
version = "v1",
namespace = @ApiNamespace(ownerDomain = "sschackathon.appspot.com",
                           ownerName = "sschackathon",
                           packagePath=""))
public class EventsService {

    private ImageHandlingService imageService = new ImageHandlingService();
/*
    public String getURL(String id) {
        return imageService.getImageURL("3");
    }

    public void getImage(String id) {
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

    public void createImage(String id) {
        	System.out.println("Hola Mundo");

            //imageService.postImage(null, null);
    }
    
    public String getEvent(String id)  {
    	return "";
    }
*/
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
}