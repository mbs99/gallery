package de.mbs.gallery.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.mbs.gallery.client.ClientFactory;
import de.mbs.gallery.client.GalleryResources;
import de.mbs.gallery.client.event.ChangeNavbarEvent;
import de.mbs.gallery.client.event.ChangeNavbarEvent.NAVBAR_TYPE;
import de.mbs.gallery.client.model.Authorization;
import de.mbs.gallery.client.place.AdminPlace;
import de.mbs.gallery.client.place.LoginPlace;
import de.mbs.gallery.client.view.AdminView;

public class AdminActivity extends AbstractActivity {

	AdminPlace place;
	ClientFactory clientFactory;
	AdminView view;

	public AdminActivity(AdminPlace place, ClientFactory clientFactory) {
		super();

		this.place = place;
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget parent, EventBus eventBus) {
		
		Authorization auth = clientFactory.getAuthorization();
		if(auth.getUser() != null) {
		
			clientFactory.eventBus().fireEvent(new ChangeNavbarEvent(NAVBAR_TYPE.ADMIN_VIEW));
	
			this.view = clientFactory.getAdminView(this);
	
			parent.setWidget(view.asWidget());
		}
		else {
			clientFactory.placeController().goTo(new LoginPlace(place));
		}

	}
	
	@Override
	public void onStop() {
	}
}
