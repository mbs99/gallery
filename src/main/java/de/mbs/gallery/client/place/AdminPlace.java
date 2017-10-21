package de.mbs.gallery.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class AdminPlace extends Place {
	
	private String id;
    
    public AdminPlace(String id) {
    	this.id = id;
	}

	public String getId() {
    	return id;
    }

    public static class Tokenizer implements PlaceTokenizer<AdminPlace> {
        @Override
        public String getToken(AdminPlace place) {
            return place.id;
        }

        @Override
        public AdminPlace getPlace(String token) {
        	
        	return new AdminPlace(token);
        }
    }
}
