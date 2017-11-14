package de.mbs.gallery.client.model;

import com.google.gwt.query.client.builders.JsonBuilder;

public interface Gallery extends JsonBuilder {

	String getName();
	void setName(String name);
	
	GalleryImage[] getImages();
	void setImages(GalleryImage[] images);
	
	String getOrderState();
	void setOrderState(String orderState);
}
