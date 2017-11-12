package de.mbs.gallery.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.Callback;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Composite;

import de.mbs.gallery.client.ClientFactory;
import de.mbs.gallery.client.Constants;
import de.mbs.gallery.client.GalleryResources;
import de.mbs.gallery.client.model.Gallery;
import de.mbs.gallery.client.model.ViewModel;
import de.mbs.gallery.client.place.LoginPlace;

public abstract class AbstractGalleryActivity<T extends Place, V extends Composite> extends AbstractActivity {
	
	protected T place;
	protected ClientFactory clientFactory;
	protected GalleryResources galleryResources;
	protected V view;
	protected ViewModel model;
	
	protected AbstractGalleryActivity(T place, ClientFactory factory) {
		this.place = place;
		this.clientFactory = factory;
		this.galleryResources = clientFactory.galleryResources();
		this.model = clientFactory.getViewModel();
	}
	
	protected Boolean isAuthorized() {
		//Authorization auth = clientFactory.getAuthorization();
		
		return null != Cookies.getCookie(Constants.SESSION_COOKIE_NAME);
		
		//return Boolean.valueOf(auth.getUser() != null && ! auth.getUser().isEmpty());
	}
	
	protected void redirectToLogin() {
		clientFactory.placeController().goTo(new LoginPlace(place));
	}
	
	protected V getView() {
		return view;
	}
	
	protected void asyncLoadGallery(String galleryName, Callback<Gallery, String> callback) {
		if(null == model.getGallery(galleryName)) {
			
			galleryResources.getGallery(galleryName, new Callback<Gallery, String>() {
	
				@Override
				public void onSuccess(Gallery gallery) {
					
					model.setGallery(gallery);
					
					callback.onSuccess(gallery);
				}
	
				@Override
				public void onFailure(String reason) {
					callback.onFailure(reason);
	
				}
			});
		}
		else {
			callback.onSuccess(model.getGallery(galleryName));
		}
	}
	
	protected void saveGallery(String galleryName, Callback<Void, String> callback) {
		GalleryResources res = clientFactory.galleryResources();
		Gallery gallery = model.getGallery(galleryName);
		res.saveGallery(gallery, callback);
	}
}
