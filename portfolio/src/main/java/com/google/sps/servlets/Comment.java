package com.google.sps.servlets;

import com.google.appengine.api.datastore.Entity;

public class Comment {
    private String email;
    private String content;

    public Comment (Entity entity) {
        this.email = entity.getProperty("email").toString();
        this.content = entity.getProperty("content").toString();
    }
}