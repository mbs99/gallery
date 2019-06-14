package de.mbs.gallery.client.activity;

import java.util.Arrays;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.Callback;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.query.client.GQ;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.mbs.gallery.client.ClientFactory;
import de.mbs.gallery.client.GalleryResources;
import de.mbs.gallery.client.event.ChangeNavbarEvent;
import de.mbs.gallery.client.event.ENavbarType;
import de.mbs.gallery.client.model.Authorization;
import de.mbs.gallery.client.place.AbstractGalleryPlace;
import de.mbs.gallery.client.place.AdminPlace;
import de.mbs.gallery.client.place.DownloadPlace;
import de.mbs.gallery.client.place.GalleryPlace;
import de.mbs.gallery.client.place.LoginPlace;
import de.mbs.gallery.client.view.LoginView;

public class LoginActivity extends AbstractActivity {

	LoginPlace place;
	ClientFactory clientFactory;
	LoginView view;

	public LoginActivity(LoginPlace place, ClientFactory clientFactory) {
		super();

		this.place = place;
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget parent, EventBus eventBus) {
		
		clientFactory.eventBus().fireEvent(new ChangeNavbarEvent(ENavbarType.DEFAULT));

		this.view = clientFactory.getLoginView(this);

		parent.setWidget(view.asWidget());

	}
	
	@Override
	public void onStop() {
	}

	public void authorize(String user, String pwd) {
		
		GalleryResources res = clientFactory.galleryResources();
		
		Authorization auth = GQ.create(Authorization.class);
		auth.setUser(user);
		auth.setPassword(pwd);
		if(place.getRedirect() instanceof AbstractGalleryPlace) {
			AbstractGalleryPlace redirectPlace = (AbstractGalleryPlace)place.getRedirect();
			auth.setRoles(new String[]{redirectPlace.getRole()});
		}
		
		res.login(auth, new Callback<Authorization, String>() {
			
			@Override
			public void onSuccess(Authorization result) {
				
				Authorization auth = clientFactory.getAuthorization();
				auth.setUser(result.getUser());
				auth.setRoles(result.getRoles());
				
				if(null != place.getRedirect()) {
					clientFactory.placeController().goTo(place.getRedirect());
				}
				else {
					if(Arrays.asList(auth.getRoles()).contains(AdminPlace.id)) {
						clientFactory.placeController().goTo(new AdminPlace());
					} else {
						view.onLoginSuccess(auth.getRoles());
					}
				}
			}
			
			@Override
			public void onFailure(String reason) {
				view.onLoginFailure(reason);
			}
		});
	}

	public void showGallery(String id) {
		
		Place place;
		
		if(null != id && id.toLowerCase().endsWith("download")) {
			place = new DownloadPlace(id);
		}
		else {
			place = new GalleryPlace(id);
		}
		
		clientFactory.placeController().goTo(place);
	}
}
