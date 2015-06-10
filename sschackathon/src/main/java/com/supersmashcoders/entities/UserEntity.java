package com.supersmashcoders.entities;

import java.util.List;

import com.googlecode.objectify.annotation.Embed;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Embed
public class UserEntity {
	
	@Id
	private Long id;
	@Index
	private String username;
	private String password;
	private String bio;
	private List<String> passions;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBio() {
		return bio;
	}
	public void setBio(String bio) {
		this.bio = bio;
	}
	public List<String> getPassions() {
		return passions;
	}
	public void setPassions(List<String> passions) {
		this.passions = passions;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUsername() {
		return username;
	}
	public UserEntity (String username, String password, String bio) {
		this.username = username;
		this.password = password;
		this.bio = bio;
	}
	public UserEntity (){
		
	}
	
}
