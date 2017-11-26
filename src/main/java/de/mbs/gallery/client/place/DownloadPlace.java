package de.mbs.gallery.client.place;

import com.google.gwt.place.shared.PlaceTokenizer;

public class DownloadPlace extends AbstractGalleryPlace {

    public DownloadPlace(String id) {
       super(id);
    }

	public String getId() {
    	return getRole();
    }

    public static class Tokenizer implements PlaceTokenizer<DownloadPlace> {
        @Override
        public String getToken(DownloadPlace place) {
        	
            return place.getRole();
        }

        @Override
        public DownloadPlace getPlace(String token) {
        	
        	return new DownloadPlace(token);
        }
    }
}
