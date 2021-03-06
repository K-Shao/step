package com.google.sps.servlets;

import com.google.appengine.api.datastore.Entity;

public class Comment {
  private String email;
  private String content;
  private float score;

  public Comment (Entity entity) {
    this.email = entity.getProperty("email").toString();
    this.content = entity.getProperty("content").toString();
    this.score = Float.parseFloat(entity.getProperty("sentiment").toString());
  }
}