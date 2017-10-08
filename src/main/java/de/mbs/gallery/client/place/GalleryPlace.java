package de.mbs.gallery.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class GalleryPlace extends Place {
	
	private String id;
	private String filter;

    public GalleryPlace(String id) {
       this(id,"");
    }
    
    public GalleryPlace(String id, String filter) {
    	this.id = id;
    	this.filter = filter;
	}

	public String getId() {
    	return id;
    }
	
	public String getFilter() {
    	return filter;
    }

    public static class Tokenizer implements PlaceTokenizer<GalleryPlace> {
        @Override
        public String getToken(GalleryPlace place) {
            return place.id + "::" + place.filter;
        }

        @Override
        public GalleryPlace getPlace(String token) {
        	String[] tokens = token.split("::");
        	
        	return new GalleryPlace(token);
        }
    }
}
