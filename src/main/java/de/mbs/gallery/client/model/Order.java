package de.mbs.gallery.client.model;

import java.util.List;

import com.google.gwt.query.client.builders.JsonBuilder;

public interface Order extends JsonBuilder {
	
	List<GalleryImage> getImages();
	void setImages(List<GalleryImage> images);

	String getComment();
	void getComment(String comment);
	
	String getGalleryName();
	void setGalleryName(String galleryName);
}
