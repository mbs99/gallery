package de.mbs.gallery.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class LoginPlace extends Place {
	
	private String redirect;

    public LoginPlace(String redirect) {
       this.redirect = redirect;
    }

	public String getRedirect() {
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
