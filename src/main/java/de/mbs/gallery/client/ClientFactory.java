package de.mbs.gallery.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

import de.mbs.gallery.client.activity.GalleryActivity;
import de.mbs.gallery.client.presenter.AppPanelPresenter;
import de.mbs.gallery.client.view.AppPanel;
import de.mbs.gallery.client.view.GalleryView;

public class ClientFactory {
	
	private final SimpleEventBus eventBus = new SimpleEventBus();
	private final PlaceController placeController = new PlaceController(eventBus);
	
	public GalleryResources galleryResources() {
		return new GalleryResources();
	}

	public GalleryView galleryView(GalleryActivity galleryActivity) {
		return new GalleryView(galleryActivity);
	}

	public EventBus eventBus() {
		
		return eventBus;
	}

	public PlaceController placeController() {
		
		return placeController;
	}

	public AppPanel appPanel(AppPanelPresenter presenter) {
		return new AppPanel(presenter);
	}
}
