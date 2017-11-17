package de.mbs.gallery.client.place;

import com.google.gwt.place.shared.PlaceTokenizer;

public class CheckoutPlace extends AbstractGalleryPlace {

    public CheckoutPlace(String role) {
       super(role);
    }

    public static class Tokenizer implements PlaceTokenizer<CheckoutPlace> {
        @Override
        public String getToken(CheckoutPlace place) {
            return place.getRole();
        }

        @Override
        public CheckoutPlace getPlace(String token) {
        	return new CheckoutPlace(token);
        }
    }
}
