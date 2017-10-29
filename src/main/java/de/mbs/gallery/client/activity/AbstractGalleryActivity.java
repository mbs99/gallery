package de.mbs.gallery.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.place.shared.Place;

import de.mbs.gallery.client.ClientFactory;
import de.mbs.gallery.client.model.Authorization;
import de.mbs.gallery.client.place.LoginPlace;

public abstract class AbstractGalleryActivity<T extends Place> extends AbstractActivity {
	
	protected T place;
	protected ClientFactory clientFactory;
	
	protected AbstractGalleryActivity(T place, ClientFactory factory) {
		this.place = place;
		this.clientFactory = factory;
	}
	
	protected Boolean isAuthorized() {
		Authorization auth = clientFactory.getAuthorization();
		
		return Boolean.valueOf(auth.getUser() != null && ! auth.getUser().isEmpty());
	}
	
	protected void redirectToLogin() {
		clientFactory.placeController().goTo(new LoginPlace(place));
	}
}
