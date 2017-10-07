package de.mbs.gallery.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.mbs.gallery.client.GreetingService;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
    GreetingService {

  public String greetServer(String input) throws IllegalArgumentException {
    return "Hello, GWT User!";
  }
}
