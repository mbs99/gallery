package de.mbs.gallery.client.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class ViewModel {
	
	private Map<String,Gallery> galleriesByName = new LinkedHashMap<>();
	private Authorization authorization;
	private int slideshowPos = -1;

	public Gallery getGallery(String name) {
		
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
	
	public void setSlideshowPos(int pos) {
		this.slideshowPos = pos;
	}
	
	public int getSlideshowPos() {
		return this.slideshowPos;
	}
}
