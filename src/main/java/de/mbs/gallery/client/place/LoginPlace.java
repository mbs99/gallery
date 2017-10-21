package de.mbs.gallery.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class LoginPlace extends Place {
	
	private String id;

    public LoginPlace(String id) {
       this.id = id;
    }

	public String getId() {
    	return id;
    }

    public static class Tokenizer implements PlaceTokenizer<LoginPlace> {
        @Override
        public String getToken(LoginPlace place) {
            return place.id;
        }

        @Override
        public LoginPlace getPlace(String token) {
        	return new LoginPlace(token);
        }
    }
}
