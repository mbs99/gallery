package de.mbs.gallery.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.Callback;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.query.client.GQ;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.mbs.gallery.client.ClientFactory;
import de.mbs.gallery.client.GalleryResources;
import de.mbs.gallery.client.model.Authorization;
import de.mbs.gallery.client.place.AdminPlace;
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
		
		res.login(auth, new Callback<Authorization, String>() {
			
			@Override
			public void onSuccess(Authorization result) {
				
				Authorization auth = clientFactory.getAuthorization();
				auth.setPassword(result.getPassword());
				auth.setUser(result.getUser());
				
				Window.alert(place.getRedirect());
				
				clientFactory.placeController().goTo(new AdminPlace(""));
			}
			
			@Override
			public void onFailure(String reason) {
				Window.alert(reason);
			}
		});
	}
}
