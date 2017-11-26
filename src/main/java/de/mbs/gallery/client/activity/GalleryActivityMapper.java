package de.mbs.gallery.client.activity;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.place.shared.Place;

import de.mbs.gallery.client.ClientFactory;
import de.mbs.gallery.client.place.AdminPlace;
import de.mbs.gallery.client.place.CheckoutPlace;
import de.mbs.gallery.client.place.DownloadPlace;
import de.mbs.gallery.client.place.GalleryPlace;
import de.mbs.gallery.client.place.ImagePlace;
import de.mbs.gallery.client.place.LoginPlace;
import de.mbs.gallery.client.place.LogoutPlace;
import de.mbs.gallery.client.place.OrderPlace;

public class GalleryActivityMapper implements ActivityMapper {
	private ClientFactory clientFactory;

	public GalleryActivityMapper(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public Activity getActivity(Place place) {
		
		Activity activity = createActivityFromPlace(place);
		
		return activity;
	}
	
	private Activity createActivityFromPlace(Place place) {
		if (place instanceof GalleryPlace) {	 	
			return new GalleryActivity((GalleryPlace) place, clientFactory);
		}
		else if (place instanceof DownloadPlace) {	 	
			return new DownloadActivity((DownloadPlace) place, clientFactory);
		}
		else if(place instanceof LoginPlace) {
			return new LoginActivity((LoginPlace)place, clientFactory);
		}
		else if(place instanceof AdminPlace) {
			return new AdminActivity((AdminPlace)place, clientFactory);
		}
		else if(place instanceof ImagePlace) {
			return new ImageActivity((ImagePlace)place, clientFactory);
		}
		else if(place instanceof LogoutPlace) {
			return new LogoutActivity((LogoutPlace)place, clientFactory);
		}
		else if(place instanceof OrderPlace) {
			return new OrderActivity((OrderPlace)place, clientFactory);
		}
		else if(place instanceof CheckoutPlace) {
			return new CheckoutActivity((CheckoutPlace)place, clientFactory);
		}
		else {
			return new LoginActivity((LoginPlace)place, clientFactory);
		}
	}
}