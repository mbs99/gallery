package de.mbs.gallery.client.model;

import com.google.gwt.query.client.builders.JsonBuilder;

public interface GalleryList extends JsonBuilder {

	String[] getGalleries();
	void setGalleries(String[] galleries);
}
