package de.mbs.gallery.client.model;

import com.google.gwt.query.client.builders.JsonBuilder;

public interface UserAccount extends JsonBuilder {
	
	String getUsername();
	void setUsername(String username);
	
	String getPassword();
	void setPassword(String password);

}
