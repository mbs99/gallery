package de.mbs.gallery.client.place;

import com.google.gwt.place.shared.Place;

public abstract class AbstractGalleryPlace extends Place {
	
	private String role;
	
	protected AbstractGalleryPlace(String role) {
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
}
