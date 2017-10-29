package de.mbs.gallery.client.place;

import com.google.gwt.place.shared.PlaceTokenizer;

public class GalleryPlace extends AbstractGalleryPlace {
	
	private String id;
	private String filter;

    public GalleryPlace(String id) {
       this(id,"");
    }
    
    public GalleryPlace(String id, String filter) {
    	super(id);
    	
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
        	if(0 == tokens.length) {
        		return new GalleryPlace("");
        	}
        	else if(1 == tokens.length) {
        		return new GalleryPlace(tokens[0]);
        	}
        	else {
        		return new GalleryPlace(tokens[0]);
        	}
        }
    }
}
