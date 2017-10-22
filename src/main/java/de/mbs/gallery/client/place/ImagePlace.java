package de.mbs.gallery.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ImagePlace extends Place {
	
	private String galleryName;
	private String imageId;

    public ImagePlace(String galleryName, String imageId) {
       this.galleryName = galleryName;
       this.imageId = imageId;
    }

	public String getGalleryName() {
    	return galleryName;
    }
	
	public String getImageId() {
    	return imageId;
    }

    public static class Tokenizer implements PlaceTokenizer<ImagePlace> {
        @Override
        public String getToken(ImagePlace place) {
            return place.galleryName + "::" + place.getImageId();
        }

        @Override
        public ImagePlace getPlace(String token) {
        	String[] tokens = token.split("::");
        	ImagePlace place;
        	if(tokens.length == 2) {
        		place = new ImagePlace(tokens[0], tokens[1]);
        	}
        	else {
        		place = new ImagePlace("","");
        	}
        	return place;
        }
    }
}
