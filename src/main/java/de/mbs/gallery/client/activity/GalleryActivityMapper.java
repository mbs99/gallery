package de.mbs.gallery.client.activity;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;

import de.mbs.gallery.client.ClientFactory;
import de.mbs.gallery.client.place.GalleryPlace;

public class GalleryActivityMapper implements ActivityMapper {
	private ClientFactory clientFactory;

	public GalleryActivityMapper(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}

	@Override
	public Activity getActivity(Place place) {
		if (place instanceof GalleryPlace) {
			return new GalleryActivity((GalleryPlace) place, clientFactory);
		}
		else {
			Window.alert("Unknown place: " + place);
		}
		return null;
	}
}