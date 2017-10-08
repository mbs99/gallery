package de.mbs.gallery.client.presenter;

import com.google.gwt.place.shared.Place;

import de.mbs.gallery.client.ClientFactory;
import de.mbs.gallery.client.place.GalleryPlace;

public class AppPanelPresenter {
	
	ClientFactory factory;

	public AppPanelPresenter(ClientFactory clientFactory) {
		this.factory = clientFactory;
	}

	public void setFilter(String filter) {
		
		Place currentPlace = factory.placeController().getWhere();
		if(currentPlace instanceof GalleryPlace) {
			GalleryPlace place = new GalleryPlace(((GalleryPlace) currentPlace).getId(), filter);
			
			factory.placeController().goTo(place);
		}
		
	}

}
