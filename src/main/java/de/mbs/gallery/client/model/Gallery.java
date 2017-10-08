package de.mbs.gallery.client.model;

import java.util.List;

import com.google.gwt.query.client.builders.JsonBuilder;

public interface Gallery extends JsonBuilder {

	String getName();
	
	GalleryImage[] getImages();
}
