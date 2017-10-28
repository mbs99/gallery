package de.mbs.gallery.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class LoginPlace extends Place {
	
	private Place redirect;
	
	public LoginPlace(String redirect) {
	    }

    public LoginPlace(Place redirect) {
       this.redirect = redirect;
    }

	public Place getRedirect() {
    	return redirect;
    }

    public static class Tokenizer implements PlaceTokenizer<LoginPlace> {
        @Override
        public String getToken(LoginPlace place) {
            return "";
        }

        @Override
        public LoginPlace getPlace(String token) {
        	return new LoginPlace(token);
        }
    }
}
