package de.mbs.gallery.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class GalleryPlace extends Place {
	
	private String id;

    public GalleryPlace(String id) {
       this.id = id;
    }

    public static class Tokenizer implements PlaceTokenizer<GalleryPlace> {
        @Override
        public String getToken(GalleryPlace place) {
            return place.id;
        }

        @Override
        public GalleryPlace getPlace(String token) {
        	return new GalleryPlace(token);
        }
    }
}
