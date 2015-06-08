package com.supersmashcoders.application;

import org.restlet.Context;
import org.restlet.ext.jaxrs.JaxRsApplication;

public class JaxRSApplication extends JaxRsApplication {

    public JaxRSApplication(Context context) {
        super(context);
        this.add(new BackToBackApplication());
    }

}