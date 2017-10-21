package de.mbs.gallery.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.EventBus;

import de.mbs.gallery.client.activity.GalleryActivityMapper;
import de.mbs.gallery.client.place.GalleryPlaceHistoryMapper;
import de.mbs.gallery.client.place.LoginPlace;
import de.mbs.gallery.client.presenter.AppPanelPresenter;
import de.mbs.gallery.client.view.AppPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GalleryEntryPoint implements EntryPoint {

	private final Messages messages = GWT.create(Messages.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		String galleryId = Window.Location.getParameter("id");
		
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		EventBus eventBus = clientFactory.eventBus();
		PlaceController placeController = clientFactory.placeController();
		AppPanelPresenter appPanelPresenter = new AppPanelPresenter(clientFactory);
		AppPanel view = clientFactory.appPanel(appPanelPresenter);

		// Start ActivityManager for the main widget with our ActivityMapper
		ActivityMapper activityMapper = new GalleryActivityMapper(clientFactory);
		ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
		activityManager.setDisplay(view.getContentPanel());

		// Start PlaceHistoryHandler with our PlaceHistoryMapper
		GalleryPlaceHistoryMapper historyMapper = GWT.create(GalleryPlaceHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(placeController, eventBus, new LoginPlace(""));

		RootPanel.get().add(view);
		// Goes to the place represented on URL else default place
		historyHandler.handleCurrentHistory();
	}
}
