package de.mbs.gallery.client.model;

import com.google.gwt.query.client.builders.JsonBuilder;

public interface UsernameList extends JsonBuilder {
	
	String[] getUsers();
	void setUsers(String[] users);

}
