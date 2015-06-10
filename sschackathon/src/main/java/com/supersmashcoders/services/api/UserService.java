package com.supersmashcoders.services.api;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.supersmashcoders.entities.UserEntity;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.ObjectifyService;

public class UserService extends HttpServlet {

	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        UserEntity user;
        
        String username = req.getParameter("user");
        String password = req.getParameter("password");
        String bio = req.getParameter("bio");

        user = new UserEntity(username, password, bio);
        
        ObjectifyService.ofy().save().entity(user).now();

        res.sendRedirect("/login.jsp");
    }
}
