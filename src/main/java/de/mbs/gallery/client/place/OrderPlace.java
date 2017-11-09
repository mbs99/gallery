package de.mbs.gallery.client.place;

import com.google.gwt.place.shared.PlaceTokenizer;

public class OrderPlace extends AbstractGalleryPlace {

    public OrderPlace(String role) {
       super(role);
    }

    public static class Tokenizer implements PlaceTokenizer<OrderPlace> {
        @Override
        public String getToken(OrderPlace place) {
            return place.getRole();
        }

        @Override
        public OrderPlace getPlace(String token) {
        	return new OrderPlace(token);
        }
    }
}
