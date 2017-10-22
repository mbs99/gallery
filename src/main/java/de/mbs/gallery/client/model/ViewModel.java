package de.mbs.gallery.client.model;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.gwt.query.client.Function;

public class ViewModel {
	
	private Map<String,Gallery> galleriesByName = new LinkedHashMap<>();
	private Authorization authorization;

	public Gallery getGallery(String name, Function callback) {
		
		return galleriesByName.get(name);
	}

	public void setGallery(Gallery gallery) {
		galleriesByName.put(gallery.getName(), gallery);
	}

	public Authorization getAuthorization() {
		return authorization;
	}

	public void setAuthorization(Authorization authorization) {
		this.authorization = authorization;
	}
}
