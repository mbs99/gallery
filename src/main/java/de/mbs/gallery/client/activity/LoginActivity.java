package de.mbs.gallery.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.Callback;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.query.client.GQ;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.mbs.gallery.client.ClientFactory;
import de.mbs.gallery.client.GalleryResources;
import de.mbs.gallery.client.event.ChangeNavbarEvent;
import de.mbs.gallery.client.event.ENavbarType;
import de.mbs.gallery.client.model.Authorization;
import de.mbs.gallery.client.place.AbstractGalleryPlace;
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
				
				clientFactory.placeController().goTo(place.getRedirect());
			}
			
			@Override
			public void onFailure(String reason) {
				view.onLoginFailure(reason);
			}
		});
	}
}
