package de.mbs.gallery.client.presenter;

import com.google.gwt.core.client.Callback;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;

import de.mbs.gallery.client.ClientFactory;
import de.mbs.gallery.client.event.ChangeFilterEvent;
import de.mbs.gallery.client.event.ChangeNavbarEvent;
import de.mbs.gallery.client.event.EMenuItem;
import de.mbs.gallery.client.event.MenuItemEvent;
import de.mbs.gallery.client.model.Authorization;
import de.mbs.gallery.client.place.GalleryPlace;
import de.mbs.gallery.client.place.LogoutPlace;
import de.mbs.gallery.client.view.AppPanel;

public class AppPanelPresenter {

	ClientFactory factory;
	private AppPanel view;

	public AppPanelPresenter(ClientFactory clientFactory) {
		this.factory = clientFactory;

		clientFactory.eventBus().addHandler(ChangeNavbarEvent.TYPE,
				new ChangeNavbarEvent.ChangeNavbarEventEventHandler() {

					@Override
					public void changeNavbar(ChangeNavbarEvent event) {
						handleChangeNavbar(event);
					}
				});
		
		clientFactory.eventBus().addHandler(ChangeFilterEvent.TYPE,
				new ChangeFilterEvent.ChangeFilterEventEventHandler() {
					
					@Override
					public void changeFilter(ChangeFilterEvent event) {
						handleChangeFilter(event);
						
					}
				});

	}

	public void setFilter(String filter) {
		
		Place currentPlace = factory.placeController().getWhere();
		if (currentPlace instanceof GalleryPlace) {
			GalleryPlace place = new GalleryPlace(((GalleryPlace) currentPlace).getId(), filter);

			factory.placeController().goTo(place);
		}

	}

	public void showGallery() {
		factory.eventBus().fireEvent(new MenuItemEvent(EMenuItem.SHOW_GALLERY));
	}

	public void previousImage() {
		factory.eventBus().fireEvent(new MenuItemEvent(EMenuItem.SHOW_PREVIOUS_IMAGE));
	}

	public void nextImage() {
		factory.eventBus().fireEvent(new MenuItemEvent(EMenuItem.SHOW_NEXT_IMAGE));
	}

	public void setView(AppPanel view) {
		this.view = view;
		
	}

	private void handleChangeNavbar(ChangeNavbarEvent event) {
		switch (event.getNavbarType()) {
		case GALLERY_VIEW: {
			view.setNavbar(factory.getGalleryViewNavbar(this));
		}
		break;
		case IMAGE_VIEW: {
			view.setNavbar(factory.getImageViewNavbar(this));
		}
		break;
		
		case ADMIN_VIEW: {
			view.setNavbar(factory.getAdminViewNavbar(this));
		}
		break;
		
		case ORDER_VIEW: {
			view.setNavbar(factory.getOrderViewNavbar(this));
		}
		break;
		
		default:
			view.setNavbar(factory.getDefaultNavbar(this));
		break;
		}
	}
	
	private void handleChangeFilter(ChangeFilterEvent event) {
		view.setFilter(event.getFilter());
	}

	public void logout() {
		factory.galleryResources().logout(new Callback<Void, String>() {
			
			@Override
			public void onSuccess(Void result) {
				Authorization auth = factory.getAuthorization();
				auth.setUser(null);
				auth.setPassword(null);
				
				factory.placeController().goTo(new LogoutPlace());
			}
			
			@Override
			public void onFailure(String reason) {
				Window.alert(reason);
				
				factory.placeController().goTo(new LogoutPlace());
			}
		});
		
	}

	public void goToOrderPlace() {
		factory.eventBus().fireEvent(new MenuItemEvent(EMenuItem.SHOW_ORDER));
	}

	public void submitOrder() {
		factory.eventBus().fireEvent(new MenuItemEvent(EMenuItem.SUBMIT_ORDER));
	}
}
