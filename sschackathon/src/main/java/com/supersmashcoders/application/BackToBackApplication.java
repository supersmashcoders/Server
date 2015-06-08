package com.supersmashcoders.application;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import com.supersmashcoders.services.api.EventsService;

public class BackToBackApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(EventsService.class);
        return classes;
    }
}
