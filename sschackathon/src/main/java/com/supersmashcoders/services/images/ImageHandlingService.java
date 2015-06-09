package com.supersmashcoders.services.images;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.googlecode.objectify.ObjectifyService;
import com.supersmashcoders.entities.images.ImageEntity;
import com.supersmashcoders.resources.URLResource;

public class ImageHandlingService {

    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    public URLResource getImageURL(String id) {

        String url = "/photos?" + id;
        return new URLResource(blobstoreService.createUploadUrl(url));
    }

    public void getImagesFromEvent(String eventId) {

    }

    public JSONObject postImage(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        List<BlobKey> blobs = blobstoreService.getUploads(req).get("image");
        BlobKey blobKey = blobs.get(0);

        ImagesService imagesService = ImagesServiceFactory.getImagesService();
        ServingUrlOptions servingOptions = ServingUrlOptions.Builder.withBlobKey(blobKey);

        String servingUrl = imagesService.getServingUrl(servingOptions);

        res.setStatus(HttpServletResponse.SC_OK);
        res.setContentType("application/json");
        JSONObject json = new JSONObject();
        try {
            json.put("servingUrl", servingUrl);
            json.put("blobKey", blobKey.getKeyString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ImageEntity entity = new ImageEntity();
        entity.setEventId(0);
        entity.setBlobKey(blobKey);
        entity.setServingURL(servingUrl);

        ObjectifyService.ofy().save().entity(entity).now();
        return json;
    }
}
