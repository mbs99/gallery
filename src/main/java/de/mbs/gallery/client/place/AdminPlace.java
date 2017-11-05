package de.mbs.gallery.client.place;

import com.google.gwt.place.shared.PlaceTokenizer;

public class AdminPlace extends AbstractGalleryPlace {
	
	private static final String id = "admin";
    
    public AdminPlace() {
    	super(id);
	}

	public String getId() {
    	return id;
    }

    public static class Tokenizer implements PlaceTokenizer<AdminPlace> {
        @Override
        public String getToken(AdminPlace place) {
            return AdminPlace.id;
        }

        @Override
        public AdminPlace getPlace(String token) {
        	
        	return new AdminPlace();
        }
    }
}
