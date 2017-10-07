package de.mbs.gallery.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.EventBus;

import de.mbs.gallery.client.activity.GalleryActivityMapper;
import de.mbs.gallery.client.place.GalleryPlace;
import de.mbs.gallery.client.place.GalleryPlaceHistoryMapper;
import de.mbs.gallery.client.view.AppPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Gallery implements EntryPoint {

	private final Messages messages = GWT.create(Messages.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		EventBus eventBus = clientFactory.eventBus();
		PlaceController placeController = clientFactory.placeController();
		AppPanel view = clientFactory.appPanel();

		// Start ActivityManager for the main widget with our ActivityMapper
		ActivityMapper activityMapper = new GalleryActivityMapper(clientFactory);
		ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
		activityManager.setDisplay(view.getContentPanel());

		// Start PlaceHistoryHandler with our PlaceHistoryMapper
		GalleryPlaceHistoryMapper historyMapper = GWT.create(GalleryPlaceHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(placeController, eventBus, new GalleryPlace(""));

		RootPanel.get().add(view);
		// Goes to the place represented on URL else default place
		historyHandler.handleCurrentHistory();
	}
}
