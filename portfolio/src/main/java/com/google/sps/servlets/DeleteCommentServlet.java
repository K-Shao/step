/**DeleteCommentServlet defines a Java servlet and attaches it to /delete-comment. 
The post method takes no parameters and deletes all comments in the datastore. 
*/
package com.google.sps.servlets;

import static com.google.common.collect.ImmutableList.toImmutableList;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;

@WebServlet("/delete-comment")
public class DeleteCommentServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Query query = new Query("Comment");
    PreparedQuery results = datastore.prepare(query);
    ImmutableList<Key> comments = 
        Streams.stream(results.asIterable())
        .map(Entity::getKey)
        .collect(toImmutableList());
    datastore.delete(comments);
  }

}