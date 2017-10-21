package de.mbs.gallery.client.model;

import com.google.gwt.query.client.builders.JsonBuilder;

public interface Authorization extends JsonBuilder{

	String getUser();
	void setUser(String user);
	
	String getPassword();
	void setPassword(String pwd);
	
	String[] getRoles();
	void setRoles(String[] roles);
	
}
