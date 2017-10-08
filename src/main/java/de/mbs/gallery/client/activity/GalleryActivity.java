package de.mbs.gallery.client.activity;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.core.client.Callback;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

import de.mbs.gallery.client.ClientFactory;
import de.mbs.gallery.client.GalleryResources;
import de.mbs.gallery.client.model.Gallery;
import de.mbs.gallery.client.place.GalleryPlace;
import de.mbs.gallery.client.view.GalleryView;

public class GalleryActivity extends AbstractActivity {

	GalleryPlace place;
	ClientFactory clientFactory;
	GalleryView view;

	public GalleryActivity(GalleryPlace place, ClientFactory clientFactory) {
		super();

		this.place = place;
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget parent, EventBus eventBus) {

		this.view = clientFactory.galleryView(this);
		GalleryResources res = clientFactory.galleryResources();

		res.getGallery(place.getId(), new Callback<Gallery, String>() {

			@Override
			public void onSuccess(Gallery result) {
				view.setGallery(result);
				parent.setWidget(view.asWidget());
			}

			@Override
			public void onFailure(String reason) {
				Window.alert("reason");

			}
		});

	}
}
