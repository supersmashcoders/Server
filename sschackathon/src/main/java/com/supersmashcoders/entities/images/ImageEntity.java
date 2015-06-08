package com.supersmashcoders.entities.images;

import com.google.appengine.api.blobstore.BlobKey;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class ImageEntity {
    @Id
    private Long id;
    private BlobKey blobKey;
    private String servingURL;
    private Integer eventId;

    public BlobKey getBlobKey() {
        return blobKey;
    }

    public void setBlobKey(BlobKey blobKey) {
        this.blobKey = blobKey;
    }

    public String getServingURL() {
        return servingURL;
    }

    public void setServingURL(String servingURL) {
        this.servingURL = servingURL;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

}
