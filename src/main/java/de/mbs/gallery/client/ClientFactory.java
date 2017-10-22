package de.mbs.gallery.client;

import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.query.client.GQ;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

import de.mbs.gallery.client.activity.AdminActivity;
import de.mbs.gallery.client.activity.GalleryActivity;
import de.mbs.gallery.client.activity.ImageActivity;
import de.mbs.gallery.client.activity.LoginActivity;
import de.mbs.gallery.client.model.Authorization;
import de.mbs.gallery.client.presenter.AppPanelPresenter;
import de.mbs.gallery.client.view.AdminView;
import de.mbs.gallery.client.view.AppPanel;
import de.mbs.gallery.client.view.GalleryView;
import de.mbs.gallery.client.view.ImageView;
import de.mbs.gallery.client.view.LoginView;

public class ClientFactory {
	
	private final SimpleEventBus eventBus = new SimpleEventBus();
	private final PlaceController placeController = new PlaceController(eventBus);
	private final Authorization authorization = GQ.create(Authorization.class);
	
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

	public LoginView getLoginView(LoginActivity loginActivity) {
		
		return new LoginView(loginActivity);
	}

	public AdminView getAdminView(AdminActivity adminActivity) {
		return new AdminView(adminActivity);
	}
	
	public Authorization getAuthorization() {
		return authorization;
	}

	public ImageView getImageView(ImageActivity imageActivity) {
		
		return new ImageView(imageActivity);
	}
}
