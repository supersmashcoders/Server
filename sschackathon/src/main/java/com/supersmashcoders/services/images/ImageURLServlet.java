package com.supersmashcoders.services.images;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

public class ImageURLServlet extends HttpServlet {

    private static final long serialVersionUID = 3579937994452673560L;
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        // String request = req.getRequestURI();
        // String id = request.substring(8, request.indexOf("/photos/url"));
        // String url = "/events/" + id + "/photos";
        String url = "/photos/add";
        String blobUploadUrl = blobstoreService.createUploadUrl(url);

        res.setStatus(HttpServletResponse.SC_OK);
        res.setContentType("text/plain");

        PrintWriter out = res.getWriter();
        out.print(blobUploadUrl);
        out.flush();
        out.close();
    }

}
