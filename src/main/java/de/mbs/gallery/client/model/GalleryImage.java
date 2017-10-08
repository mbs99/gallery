package de.mbs.gallery.client.model;

import com.google.gwt.query.client.builders.JsonBuilder;

public interface GalleryImage extends JsonBuilder {
	
	String getId();
	
	String getFilename();
	
	int getVote();
	
	void setVote(int vote);

}
