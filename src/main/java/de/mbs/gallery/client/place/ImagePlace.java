package de.mbs.gallery.client.place;

import com.google.gwt.place.shared.PlaceTokenizer;

public class ImagePlace extends AbstractGalleryPlace {

	private String galleryName;
	private String imageId;
	private String filter;

	public ImagePlace(String galleryName, String imageId, String filter) {
		super(galleryName);
		
		this.galleryName = galleryName;
		this.imageId = imageId;
		this.filter = filter;
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
			if (tokens.length == 2) {
				place = new ImagePlace(tokens[0], tokens[1], "");
			} else if (tokens.length == 3) {
				place = new ImagePlace(tokens[0], tokens[1], tokens[2]);
			} else {
				place = new ImagePlace("", "", "");
			}
			return place;
		}
	}

	public String getFilter() {
		return filter;
	}
}
