package com.supersmashcoders.services.api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.supersmashcoders.services.images.ImageHandlingService;

public class ImageHandlingServlet extends HttpServlet {

    private static final long serialVersionUID = -5311760696187364921L;
    private ImageHandlingService imageService = new ImageHandlingService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        JSONObject json = imageService.postImage(req, res);
        PrintWriter out = res.getWriter();
        out.print(json.toString());
        out.flush();
        out.close();

    }
}
