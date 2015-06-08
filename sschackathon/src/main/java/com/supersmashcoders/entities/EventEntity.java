package com.supersmashcoders.entities;

import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.GeoPt;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.supersmashcoders.entities.images.ImageEntity;

@Entity
public class EventEntity {
	
	@Id
	private Long id;
	private String name;
	private String description;
	private Date startDate;
	private Date endDate;
	private GeoPt geoPoint;
	private UserEntity owner;
	private List<String> tags;
	private List<UserEntity> attendants;
	private List<ImageEntity> photos;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public GeoPt getGeoPoint() {
		return geoPoint;
	}
	public void setGeoPoint(GeoPt geoPoint) {
		this.geoPoint = geoPoint;
	}
	public UserEntity getOwner() {
		return owner;
	}
	public void setOwner(UserEntity owner) {
		this.owner = owner;
	}
	public List<String> getTags() {
		return tags;
	}
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	public List<UserEntity> getAttendants() {
		return attendants;
	}
	public void setAttendants(List<UserEntity> attendants) {
		this.attendants = attendants;
	}
	public List<ImageEntity> getPhotos() {
		return photos;
	}
	public void setPhotos(List<ImageEntity> photos) {
		this.photos = photos;
	}
}
